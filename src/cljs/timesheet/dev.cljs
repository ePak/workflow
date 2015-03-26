(ns timesheet.dev
  (:require [figwheel.client :as figwheel]
            [timesheet.app :as app]))

(enable-console-print!)

(println "timesheet.dev")

(figwheel/start {:websocket-url "ws://localhost:3449/figwheel-ws"
                 :on-jsload (fn [] (print "figwheel reloaded"))})


(app/main)
