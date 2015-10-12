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
                          :clearable true
                          :rating 3
                          :max-rating 5}))

(defn downrating-button
  "Decreases the rating by one"
  [data owner]
  (om/component
   (dom/div #js {:className "ui button"}
            "Decrease Rating")))

(defn uprating-button
  "Increases the rating by one"
  [data owner]
  (om/component
   (dom/div #js {:className "ui button"}
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
   (dom/div #js {:className "ui button"}
            "Clearability: "
            (if (:clearable data) "True" "False"))))

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
