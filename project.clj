(defproject om-semantic "0.1.4-SNAPSHOT"
  :description "A collection of om-components with the css from Semantic UI"
  :url "https://github.com/vikeri/om-semantic"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "0.0-3308"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [cljs-react-test "0.1.3-SNAPSHOT"]
                 [org.omcljs/om "0.9.0" :exclusions [cljsjs/react]]
                 [cljsjs/jquery "2.1.4-0"]
                 [jayq "2.5.4"]]

  :plugins [[lein-cljsbuild "1.0.5"]
            [lein-doo "0.1.2-SNAPSHOT"]]

  :source-paths ["src"]

  :clean-targets ^{:protect false} ["target"
                                    "examples/dropdown/out"
                                    "examples/rating/out"]

  :cljsbuild {:builds [{:id "test"
                        :source-paths ["src" "test"]
                        :compiler {:output-to "target/testable.js"
                                   :optimizations :whitespace
                                   :cache-analysis false
                                   :pretty-print true
                                   :foreign-libs [{:file "https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.1.4/semantic.js"
                                                   :file-min "https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.1.4/semantic.min.js"
                                                   :provides ["semantic-ui.core"]}]}}
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
