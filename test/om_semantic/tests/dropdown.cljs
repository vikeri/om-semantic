(ns om-semantic.tests
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.test :refer-macros [is testing deftest async]]
            [cljs.core.async :as async :refer [chan put!]]
            [cljs-react-test.simulate :as sim]
            [cljs-react-test.utils :as tu]
            [om.core :as om :include-macros true]
            [om-semantic.dropdown :as d]))

(enable-console-print!)

(defn make-wrapper 
  ([init-state] (make-wrapper init-state {}))
  ([init-state opts]
   (fn [data owner]
     (om/component
       (om/build d/dropdown data {:init-state init-state
                                  :opts opts})))))

(def xs [{:id 1 :val "Joffrey"}
         {:id 2 :val "Cersei"}
         {:id 5 :val "Tywin"}])

(def init-state {:menu [:xs]
                 :idkey :id
                 :lkey :val
                 :disabled false 
                 :selected [:selected]})

(deftest dropdown 
  (testing "Dropdown displays initial state"
    (async done
      (let [c (tu/new-container!) 
            ch (chan)
            app-state (atom {:xs xs :selected 1})
            wrapper (make-wrapper init-state {:ch ch}) 
            rt (om/root wrapper app-state {:target c})
            react-nodes (tu/find-by-tag rt "div")
            [main-react display-react] react-nodes 
            [main-node display-node] (mapv om/get-node react-nodes)]
        (is (= "Joffrey" (.-innerHTML display-node)))
        (testing "and opens on click, showing the options, and firing event"
          (go (let [[tag d] (<! ch)]
                (is (= tag :click))
                (is (nil? d))))
          (sim/click main-node nil)
          (om/render-all)
          (let [ns (drop 1 (-> (tu/find-one-by-class rt "menu")
                             (tu/find-by-tag "div")))
                ns (map om/get-node ns)]
            (is (= (map :val xs) (map #(.-innerHTML %) ns)))
            (testing "which when clicked change the state, and fires an event"
              (let [new-selected (:id (second xs))]
                (go (let [[tag d] (<! ch)]
                      (is (= tag :select))
                      (is (= d new-selected))
                      (tu/unmount! c)
                      (done)))
                (sim/click (second ns) nil)
                (om/render-all)
                (is (= (:selected @app-state) new-selected))
                (is (= (:val (second xs)) (.-innerHTML display-node)))))))))))
    
(deftest disabled-dropdown
  (testing "but does nothing else if disabled"
    (let [c (tu/new-container!)
          app-state (atom {:xs xs :selected 1})
          wrapper (make-wrapper (assoc init-state :disabled true)) ;; Disable
          rt (om/root wrapper app-state {:target c})
          react-nodes (tu/find-by-tag rt "div")
          [main-react display-react] react-nodes 
          [main-node display-node] (mapv om/get-node react-nodes)]
      (is (= "Joffrey" (.-innerHTML display-node)))
      (sim/click main-node nil)
      (om/render-all)
      (is (empty? (tu/find-by-class rt "transition")))
      (is (empty? (tu/find-by-class rt "menu")))
      (is (empty? (tu/find-by-class rt "visible")))
      (tu/unmount! c))))

