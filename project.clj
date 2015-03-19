(defproject om-semantic "0.1.0-SNAPSHOT"
  :description "A collection of om-components with the css from Semantic UI"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-3126"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [figwheel "0.2.5-SNAPSHOT"]
                 [prismatic/om-tools "0.3.11"]
                 [org.omcljs/om "0.8.8"]]

  :plugins [[lein-cljsbuild "1.0.4"]
            [lein-figwheel "0.2.5-SNAPSHOT"]]

  :source-paths ["src"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled"]
  
  :cljsbuild {
    :builds [{:id "dropdown"
              :source-paths ["src" "examples/dropdown" "dev_src"]
              :compiler {:output-to "resources/public/js/compiled/om_semantic.js"
                         :output-dir "resources/public/js/compiled/out/dropdown"
                         :optimizations :none
                         :main examples.dropdown.core
                         :asset-path "js/compiled/out/dropdown"
                         :source-map true
                         :source-map-timestamp true
                         :cache-analysis true }}
             {:id "dev"
              :source-paths ["src" "dev_src"]
              :compiler {:output-to "resources/public/js/compiled/om_semantic.js"
                         :output-dir "resources/public/js/compiled/out"
                         :optimizations :none
                         :main om-semantic.dev
                         :asset-path "js/compiled/out"
                         :source-map true
                         :source-map-timestamp true
                         :cache-analysis true }}
             {:id "min"
              :source-paths ["src"]
              :compiler {:output-to "resources/public/js/compiled/om_semantic.js"
                         :main om-semantic.core                         
                         :optimizations :advanced
                         :pretty-print false}}]}

  :figwheel {
             :http-server-root "public" ;; default and assumes "resources"
             :server-port 3449 ;; default
             :css-dirs ["resources/public/css"] ;; watch and update CSS
             })
