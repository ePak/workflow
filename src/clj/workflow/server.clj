(ns workflow.server
  (:require [compojure.core :refer [defroutes]]
            [compojure.route :as route]
            [compojure.handler :refer [site]]
            ))

(defn directoryindex [handler index]
  (fn [request]
    (let [uri (re-find #"^.*/$" (request :uri))]
      (handler
        (if uri
          (assoc request :uri (str uri index))
          request)))))

(defroutes http-routes
  (route/resources "/")
  (route/not-found "Page not found")
  )

(def website (-> (site http-routes)
                 (directoryindex "index.html")))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
