(ns om-semantic.rating
  (:require [clojure.string :as string]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

;; Utility functions

(defn generate-stars
  "Generate star widgets based on current props"
  [data]
  (let [rating (:rating data)
        max-rating (:max-rating data)
        nums (range 1 (inc max-rating))]
    (map #(dom/i #js {:className (string/join " " ["icon" (if (<= % rating)
                                                            "active")])
                      :onClick (if (:interactive data)
                                 (fn [e] (om/update! data :rating %)) nil)})
         nums)))

;; Rating component

(defn rating
  "A simple rating component for Om using Semantic UI CSS"
  [data owner]
  (reify
    om/IDisplayName
    (display-name [_] "Rating")
    om/IRender
    (render [_]
      (let [className (if (:interactive data)
                        "ui rating"
                        "ui rating disabled")
            rating (:rating data)
            max-rating (:max-rating data)
            stars (generate-stars data)]
        (apply dom/div
               #js {:className className
                    :data-rating (:rating data)
                    :data-max-rating (:max-rating data)}
               stars)))))
