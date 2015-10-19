(ns examples.core
  (:require [clojure.string :as str]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [examples.dropdown :as dropdown]
            [examples.rating :as rating]
            [om-semantic.dropdown :as dd]))

(enable-console-print!)

(defonce app-state (atom (merge @rating/app-state @dropdown/app-state)))

(defn main-component
  [data owner]
  (om/component
    (dom/div nil
             (om/build dropdown/main-component data)
             (dom/div #js{:className "ui hidden divider"})
             (om/build rating/main-component data))))

(om/root main-component app-state
  {:target (. js/document (getElementById "app"))})
