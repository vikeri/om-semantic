(defproject om-semantic "0.1.4-SNAPSHOT"
  :description "A collection of om-components with the css from Semantic UI"
  :url "https://github.com/vikeri/om-semantic"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "0.0-3308"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [cljs-react-test "0.1.3-SNAPSHOT"]
                 [org.omcljs/om "0.9.0" :exclusions [cljsjs/react]]]

  :plugins [[lein-cljsbuild "1.1.0"]
            [lein-doo "0.1.2-SNAPSHOT"]]

  :source-paths ["src"]

  :clean-targets ^{:protect false} ["target"
                                    "examples/out"
                                    "examples/dropdown/out"
                                    "examples/rating/out"]

  :cljsbuild {:builds [{:id "test"
                        :source-paths ["src" "test"]
                        :compiler {:output-to "target/testable.js"
                                   :optimizations :whitespace
                                   :cache-analysis false
                                   :pretty-print true}}
                       {:id "examples"
                        :source-paths ["src" "examples/src"]
                        :compiler {:output-to "examples/out/main.js"
                                   :output-dir "examples/out"
                                   :main examples.core
                                   :asset-path "out"
                                   :optimizations :advanced
                                   :cache-analysis false
                                   :pretty-print true}}
                       {:id "dropdown"
                        :source-paths ["src" "examples/dropdown/src"]
                        :compiler {:output-to "examples/dropdown/out/main.js"
                                   :output-dir "examples/dropdown/out"
                                   :main examples.dropdown.core
                                   :asset-path "out"
                                   :optimizations :none
                                   :source-map true}}
                       {:id "rating"
                        :source-paths ["src" "examples/rating/src"]
                        :compiler {:output-to "examples/rating/out/main.js"
                                   :output-dir "examples/rating/out"
                                   :main examples.rating.core
                                   :asset-path "out"
                                   :optimizations :none
                                   :source-map true}}]})
