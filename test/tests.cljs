(ns om-semantic.tests
  (:require [cljs.test :refer-macros [is testing deftest]]
            [om-semantic.dropdown :as d]
            [om.core :as om :include-macros true]
            [dommy.core :as dommy :refer-macros [sel]]
            [cljs-react-test.utils :as tu]))

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
          root (om/root wrapper app-state {:target c})
          [main-node display-node] (sel c [:div])]
      (is (= "Joffrey" (.-innerHTML display-node)))
      (testing "and opens on click, showing the options"
        (tu/click main-node)
        (om/render-all)
        (let [ns (sel c [:div :.menu :div])]
          (is (= (map :val xs) (map #(.-innerHTML %) ns)))
          (testing "which when clicked change the state"
            (tu/click (second ns))
            (om/render-all)
            (is (= (:selected @app-state) (:id (second xs))))
            (is (= (:val (second xs)) (.-innerHTML display-node)))))))))


