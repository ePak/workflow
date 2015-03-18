(ns workflow.dev
  (:require [figwheel.client :as figwheel]
            [weasel.repl :as repl]
            [workflow.app :as app]))

(enable-console-print!)

(println "workflow.dev")

(figwheel/start {:websocket-url "ws://localhost:3449/figwheel-ws"
                 :on-jsload (fn [] (print "figwheel reloaded"))})

(when-not (repl/alive?)
  (repl/connect "ws://localhost:9001"))

(app/main)
