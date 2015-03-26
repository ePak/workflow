(ns user
  (:require [clojure.tools.namespace.repl :refer [refresh refresh-all]]
            [ring.adapter.jetty :refer [run-jetty]]
            [workflow.server :refer [http-handler]]
            [ring.middleware.reload :refer [wrap-reload]]))

(def system nil)

(defn init 
  []
  (alter-var-root #'system 
                  (constantly (run-jetty 
                                (wrap-reload #'http-handler) 
                                {:port 3000 :join? false}))))


(defn start [] (.start system))

(defn stop [] (.stop system))

(defn go
  []
  (require '[clojure.repl :refer :all])
  (init)
  (start))


(defn reset
  []
  (stop)
  (refresh :after 'user/go))

(defn hello [] "(user/hello)")
