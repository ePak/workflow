(ns user
  (:require [clojure.repl :refer :all]
            [clojure.tools.namespace.repl :refer [refresh refresh-all]]
            [ring.adapter.jetty :refer [run-jetty]]
            [workflow.server :refer [http-handler]]
            [ring.middleware.reload :refer [wrap-reload]]
            [figwheel-sidecar.auto-builder :as fig-auto]
            [figwheel-sidecar.core :as fig]
            [clojurescript-build.auto :as auto]
            [net.cgrand.enlive-html :refer [html append prepend template]]
            [clojure.core.incubator :refer [dissoc-in]]
            [weasel.repl.websocket :as weasel]))

(def system nil)
(def fig-state nil)
(def fig-builder nil)


(defn inject-dev-javascripts [response-body]
  (apply str ((template response-body [] [:body]
             (comp (prepend (html [:script {:type "text/javascript" :src "/js/out/goog/base.js"}]))
                   (append  (html [:script {:type "text/javascript"} "goog.require('workflow.dev')"])))))))

(defn inject-dev-stuff [handler]
  (fn [request]
    (let [result (handler request)
          content-type (clojure.string/lower-case (get-in result [:headers "Content-Type"] ""))]
      (if (= "text/html" content-type)
        (let [new-result (update-in result [:body] inject-dev-javascripts)]
          (dissoc-in new-result [:headers "Content-Length"]))
        result))))

(defn init
  []
  (alter-var-root #'system
                  (constantly (run-jetty
                                (wrap-reload (-> http-handler
                                                 (inject-dev-stuff)))
                                {:port 3000 :join? false})))
  (alter-var-root #'fig-state
                  (constantly {:builds [{:id "dev"
                                         :source-paths ["src/cljs"]
                                         :compiler {:output-to "resources/public/js/workflow.js"
                                                    :output-dir "resources/public/js/out"
                                                    :source-map "resources/public/js/out.js.map"
                                                    :source-map-timestamp true}}]}))
  (alter-var-root #'fig-builder (constantly nil)))

(defn start []
  (.start system)
  (when-not (:figwheel-server #'fig-state)
    (alter-var-root #'fig-state
                    #(assoc % :figwheel-server (fig/start-server {:css-dirs ["resources/public/css"]})))
    (alter-var-root #'fig-builder
                    (constantly (fig-auto/autobuild* @#'fig-state)))))

(defn stop []
  (auto/stop-autobuild! @#'fig-builder)
  (fig/stop-server (:figwheel-server fig-state))
  (.stop system))

(defn go []
  (init)
  (start))


(defn reset []
  (stop)
  (refresh :after 'user/go))

(defn brepl-env []
  (weasel/repl-env :ip "0.0.0.0" :port 9001))
