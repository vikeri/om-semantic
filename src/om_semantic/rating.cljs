(ns om-semantic.rating
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [jayq.core :refer [$]]))

(enable-console-print!)


;; Rating component

(defn rating
  "A simple rating component for Om using Semantic UI CSS"
  [data owner]
  (reify
    om/IDisplayName
    (display-name [_] "Rating")
    om/IDidMount
    (did-mount [_]
      (-> owner
          om/get-node
          $
          .rating))
    om/IDidUpdate
    (did-update [_ prev-props prev-state]
      (let [old-interactive (:interactive prev-props)
            new-interactive (:interactive data)]
        (when (not= old-interactive new-interactive)
          (if new-interactive
            (-> owner
                om/get-node
                $
                (.rating "enable"))
            (-> owner
                om/get-node
                $
                (.rating "disable"))))))
    om/IRender
    (render [_]
      (dom/div #js {:className "ui rating"
                    :data-rating (:rating data)
                    :data-max-rating (:max-rating data)}))))
