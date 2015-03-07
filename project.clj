(defproject workflow "0.1.0-SNAPSHOT"
  :description "A workflow application for JIRA"
  ;:url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0-alpha5"]
                 [compojure "1.3.2"]
                 [ring "1.3.2"]
                 ]
  :main ^:skip-aot workflow.server
  :target-path "target/%s"
  :source-paths ["src/clj" "src/cljs"]
  :profiles {:dev {:source-paths ["dev"]
                   :dependencies [[org.clojure/tools.namespace "0.2.10"]
                                  ]} 
             :uberjar {:aot :all}})
