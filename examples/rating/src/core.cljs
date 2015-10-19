(ns examples.rating.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [om-semantic.rating :as r]))

(enable-console-print!)

;; The app-state, with:
;; * a toggle for rating interaction
;; * a toggle for rating clearability
;; * a max rating value
;; * a current rating value
(defonce app-state (atom {:interactive true
                          :rating 3
                          :max-rating 5}))

(defn downrating-button
  "Decreases the rating by one"
  [data owner]
  (om/component
   (dom/div #js {:className "ui button"
                 :onClick (fn []
                            (om/transact! data
                                          :rating
                                          #(max 0 (dec %))))}
            "Decrease Rating")))

(defn uprating-button
  "Increases the rating by one"
  [data owner]
  (om/component
   (dom/div #js {:className "ui button"
                 :onClick (fn []
                            (om/transact! data
                                          :rating
                                          #(min (:max-rating data) (inc %))))}
            "Increase Rating")))

(defn interactive-button
  "Toggle rating's interactivity"
  [data owner]
  (om/component
   (dom/div #js {:className "ui button"
                 :onClick #(om/transact! data :interactive not)}
            "Interactivity: "
            (if (:interactive data) "True" "False"))))

(defn clearable-button
  "Toggle rating's clearability"
  [data owner]
  (om/component
   (dom/div #js {:className "ui button"
                 :onClick #(om/update! data :rating 0)}
            "Clear Rating")))

(defn main-component
  "You can change ratings, toggle interaction and clearability"
  [data owner]
  (om/component
   (dom/div nil
            (dom/h3 nil "Rating example")
            (om/build downrating-button data)
            (om/build r/rating data)
            (om/build uprating-button data)
            (om/build interactive-button data)
            (om/build clearable-button data))))

(om/root main-component app-state
         {:target (. js/document (getElementById "app"))})
