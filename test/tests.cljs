(ns om-semantic.tests
  (:require [cljs.test :refer-macros [is testing deftest]]
            [cljs-react-test.simulate :as sim]
            [cljs-react-test.utils :as tu]
            [om.core :as om :include-macros true]
            [om-semantic.dropdown :as d]))

(enable-console-print!)

(deftest dropdown 
  (testing "Dropdown displays initial state"
    (let [c (tu/new-container!)
          xs [{:id 1 :val "Joffrey"}
              {:id 2 :val "Cersei"}
              {:id 5 :val "Tywin"}]
          app-state (atom {:xs xs :selected 1})
          init-state {:menu [:xs]
                      :idkey :id
                      :lkey :val
                      :disabled false
                      :selected [:selected]}
          wrapper (fn [data owner]
                    (om/component
                     (om/build d/dropdown data {:init-state init-state})))
          rt (om/root wrapper app-state {:target c})
          react-nodes (tu/find-by-tag rt "div")
          [main-react display-react] react-nodes 
          [main-node display-node] (mapv om/get-node react-nodes)]
      (is (= "Joffrey" (.-innerHTML display-node)))
      (testing "and opens on click, showing the options"
        (sim/click main-node nil)
        (om/render-all)
        (let [ns (drop 1 (tu/find-by-tag (tu/find-one-by-class rt "menu") "div"))
              ns (map om/get-node ns)]
          (is (= (map :val xs) (map #(.-innerHTML %) ns)))
          (testing "which when clicked change the state"
            (sim/click (second ns) nil)
            (om/render-all)
            (is (= (:selected @app-state) (:id (second xs))))
            (is (= (:val (second xs)) (.-innerHTML display-node)))))))))


