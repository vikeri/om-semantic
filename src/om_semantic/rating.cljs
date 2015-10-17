(ns om-semantic.rating
  (:require [clojure.string :as string]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(enable-console-print!)


;; Rating component
(defn generate-stars
  [data rating max-rating]
  (let [nums (range 1 (inc max-rating))]
    (map #(dom/i #js {:className (string/join " " ["icon" (if (<= % rating)
                                                            "active")])
                      :onClick (if (:interactive data)
                                 (fn [e] (om/update! data :rating %)) nil)})
         nums)))

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
            stars (generate-stars data rating max-rating)]
        (apply dom/div
               #js {:className className
                    :data-rating (:rating data)
                    :data-max-rating (:max-rating data)}
               stars)))))
