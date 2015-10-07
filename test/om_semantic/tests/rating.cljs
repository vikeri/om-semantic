(ns om-semantic.tests.rating
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.test :refer-macros [is testing deftest]]
            [cljs.core.async :as async :refer [chan put!]]
            [cljs-react-test.simulate :as sim]
            [cljs-react-test.utils :as tu]
            [om.core :as om :include-macros true]
            [om-semantic.rating :as r]))

(enable-console-print!)

(defn make-wrapper
 ([init-state] (make-wrapper init-state {}))
 ([init-state opts]
  (fn [data owner]
    (om/component
     (om/build r/rating data {:init-state init-state
                              :opts opts})))))

(deftest rating
  (testing "Rating displays initial state"
    (let [c (tu/new-container!)
          ch (chan)
          app-state (atom {:rating 3 :max-rating 5})
          wrapper (make-wrapper init-state {:ch ch})
          root (om/root wrapper app-state {:target c})
          react-node (tu/find-one-by-class root "rating")
          display-node (om/get-node react-node)]
      (is (= "5" (.-maxRating (.-dataset display-node))))
      (is (= "3" (.-rating (.-dataset display-node)))))))
