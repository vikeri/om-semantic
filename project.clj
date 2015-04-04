(defproject om-semantic "0.1.1-SNAPSHOT"
  :description "A collection of om-components with the css from Semantic UI"
  :url "https://github.com/vikeri/om-semantic"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-3126"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [cljs-react-test "0.1.0-SNAPSHOT"]
                 [org.omcljs/om "0.8.8" :exclusions [cljsjs/react]]]

  :plugins [[lein-cljsbuild "1.0.5"]]

  :source-paths ["src"]

  :clean-targets ^{:protect false} ["target" "examples/dropdown/out"]
  
  :cljsbuild {:builds [{:id "tests"
                        :source-paths ["src" "test"]
                        :notify-command ["phantomjs"
                                         "vendor/phantom/unit-test.js"
                                         "vendor/phantom/unit-test.html"]
                        :compiler {:output-to "target/testable.js"
                                   :optimizations :whitespace
                                   :cache-analysis false
                                   :pretty-print true}}
                       {:id "dropdown"
                        :source-paths ["examples/dropdown/src"]
                        :compiler {:output-to "examples/dropdown/out/main.js"
                                   :output-dir "examples/dropdown/out"
                                   :main examples.dropdown.core
                                   :asset-path "out"
                                   :optimizations :none
                                   :source-map true}}]})
