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
          app-state (atom {:rating 3 :max-rating 5 :interactive true})
          wrapper (make-wrapper)
          root (om/root wrapper app-state {:target c})
          react-node (tu/find-one-by-class root "rating")
          react-stars (tu/find-by-class react-node "icon")
          display-node (om/get-node react-node)
          display-stars (mapv om/get-node react-stars)]
      (is (= "5" (.-maxRating (.-dataset display-node))))
      (is (= "3" (.-rating (.-dataset display-node))))
      (testing "and changing state affects rating widget"
        (om/update! (om/root-cursor app-state) :rating 4)
        (om/render-all)
        (is (= "4" (.-rating (.-dataset display-node))))
        (is (= 4 (count (tu/find-by-class root "active")))))
      (testing "Clicking rating changes app state"
        (sim/click (nth react-stars 1) nil)
        (om/render-all)
        (is (= "2" (.-rating (.-dataset display-node))))
        (is (= 2 (count (tu/find-by-class root "active")))))
      (tu/unmount! c))))

(deftest rating-noninteractive
  (testing "but cannot be clicked when non-interactive"
    (let [c (tu/new-container!)
          app-state (atom {:rating 3 :max-rating 5 :interactive false})
          wrapper (make-wrapper)
          root (om/root wrapper app-state {:target c})
          react-node (tu/find-one-by-class root "rating")
          react-stars (tu/find-by-class react-node "icon")
          display-node (om/get-node react-node)
          display-stars (mapv om/get-node react-stars)]
      (is (= "3" (.-rating (.-dataset display-node))))
      (sim/click (nth react-stars 1) nil)
      (om/render-all)
      (is (= "3" (.-rating (.-dataset display-node))))
      (is (= 3 (count (tu/find-by-class root "active"))))
      (tu/unmount! c))))
