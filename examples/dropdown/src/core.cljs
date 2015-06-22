(ns examples.dropdown.core
  (:require [clojure.string :as str]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [om-semantic.dropdown :as dd]))

(enable-console-print!)

(def menu
  "The options in the dropdown menu, with a :value and a :label"
  (mapv #(hash-map :value (str/lower-case %) :label %)
        ["Viktor" "Sebastian" "Pelle" "Rikard" "Supertramp"]))


;; The app-state, with a option menu and the current selected option
(defonce app-state (atom {:menu menu :selected nil}))

(defn button
  "Restores the selected option to the last value in the menu"
  [data owner]
  (om/component
    (dom/div #js {:className "ui button"
                  :onClick #(om/update! data :selected (:value (last menu)))}
      (:label (last menu)))))

(defn main-component
  "You can pick a character, reset your selection, and view it."
  [data owner]
  (om/component
    (dom/div nil
      (dom/h3 nil "Dropdown example")
      (om/build button data)
      (om/build dd/dropdown data
        {:init-state {:default-text "Pick Character"
                      :menu [:menu]         ;; Which key leads to the menu in data
                      :selected [:selected] ;; Where should the selection be placed
                      :idkey :value         ;; For each item, which key returns the id
                      :lkey :label          ;; For each item, which key returns the label
                      :disabled false}})    ;; default is false
      (dom/span nil " You picked: "
        (get (dd/find-key (:menu data) :value (:selected data)) :label)))))

(om/root main-component app-state
  {:target (. js/document (getElementById "app"))})
