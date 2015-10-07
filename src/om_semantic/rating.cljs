(ns om-semantic.rating
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(enable-console-print!)

;; Rating component

(defn rating
  "A simple rating component for Om using Semantic UI CSS"
  [data owner]
  (reify
    om/IDisplayName
    (display-name [_] "Rating")
    om/IRender
    (render [_]
      (dom/div #js {:className "ui rating"
                    :data-rating (:rating data)
                    :data-max-rating (:max-rating data)}))))
