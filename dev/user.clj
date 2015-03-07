(ns user
  (:require [clojure.tools.namespace.repl :refer [refresh refresh-all]]
            [ring.adapter.jetty :refer [run-jetty]]
            [workflow.server :refer [website]]))

(def system nil)

(defn init 
  []
  (alter-var-root #'system 
                  (constantly (run-jetty website {:port 3000 :join? false}))))


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
