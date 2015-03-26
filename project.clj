(defproject workflow "0.1.0-SNAPSHOT"
  :description "Setting up workflow step-by-setp"
  ;:url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :main ^:skip-aot workflow.server
  :target-path "target/%s"
  :source-paths ["src/clj" "src/cljs"]

  :dependencies [[org.clojure/clojure "1.7.0-alpha5"]
                 [compojure "1.3.2"]
                 [ring "1.3.2"]
                 [org.clojure/clojurescript "0.0-2913"]]

  :plugins [[lein-cljsbuild "1.0.5"]]

  :cljsbuild {:builds {:main {:source-paths ["src/cljs"]
                              :compiler {:output-to "resources/public/js/workflow.js"
                                         :output-dir "out"
                                         :optimizations :whitespace
                                         :pretty-printing true }}}}

  :profiles {:dev {:source-paths ["dev"]
                   :dependencies [[org.clojure/tools.namespace "0.2.10"]
                                  [figwheel "0.2.5"]
                                  [figwheel-sidecar "0.2.5"]
                                  [enlive "1.1.5"]
                                  [org.clojure/core.incubator "0.1.3"]]
                   :plugins [[lein-figwheel "0.2.5-SNAPSHOT"]]
                   :cljsbuild {:builds {:main {:output-dir "resources/public/js/out"
                                              :source-map "resources/public/js/out.js.map"
                                              :optimizations :none}}}
                   :figwheel {:css-dirs ["resources/public/css"]}}
             :uberjar {:aot :all}})
