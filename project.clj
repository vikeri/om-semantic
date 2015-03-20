(defproject om-semantic "0.1.0-SNAPSHOT"
  :description "A collection of om-components with the css from Semantic UI"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-3126"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [org.omcljs/om "0.8.8"]]

  :plugins [[lein-cljsbuild "1.0.5"]]

  :source-paths ["src"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                    "examples/dropdown/out"]
  
  :cljsbuild {
    :builds [{:id "dropdown"
              :source-paths ["src" "examples/dropdown/src"]
              :compiler {:output-to "examples/dropdown/out/main.js"
                         :output-dir "examples/dropdown/out"
                         :optimizations :none
                         :source-map true}}]}
)
