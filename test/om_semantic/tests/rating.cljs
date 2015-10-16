(ns om-semantic.tests.rating
  (:require [cljs.test :refer-macros [is testing deftest]]
            [cljs-react-test.simulate :as sim]
            [cljs-react-test.utils :as tu]
            [om.core :as om :include-macros true]
            [om-semantic.rating :as r]))

(enable-console-print!)

(defn make-wrapper
  []
  (fn [data owner]
    (om/component
     (om/build r/rating data))))

(deftest rating
  (testing "Rating displays initial state"
    (let [c (tu/new-container!)
          app-state (atom {:rating 3 :max-rating 5 :interactive false})
          wrapper (make-wrapper)
          root (om/root wrapper app-state {:target c})
          react-node (tu/find-one-by-class root "rating")
          display-node (om/get-node react-node)]
      (comment is (= "5" (.-maxRating (.-dataset display-node))))
      (comment is (= "3" (.-rating (.-dataset display-node)))))))
