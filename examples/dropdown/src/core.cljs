(ns examples.dropdown.core
  (:require [om.core :as om :include-macros true]
            [om-semantic.dropdown :as dd]
            [clojure.string :as str]
            [om.dom :as dom :include-macros true]))

(enable-console-print!)

(.clear js/console)

(def menu
  (mapv #(hash-map
          :value (str/lower-case %)
          :label %)
        ["Viktor"
         "Sebastian"
         "Pelle"
         "Rikard"
         "Supertramp"]))

(defonce app-state (atom {:menu     menu
                          :selected nil}))

(defn button
  [data owner]
  (om/component
    (dom/div #js {:className "ui button"
                  :onClick   #(om/update!
                               data :selected
                               (:value (last menu)))}
             (:label (last menu)))))

(om/root
  (fn [data owner]
    (reify
      om/IRender
      (render [_]
              (dom/div nil
                (dom/h3 nil "Dropdown example")
                (om/build button data)
                (om/build dd/dropdown
                          data
                          {:init-state
                           {:default-text "Pick Character"
                            :menu [:menu]
                            :idkey :value
                            :lkey :label
                            :disabled false
                            :selected [:selected]}})
                (dom/span nil " You picked: "
                         (get (dd/find-key
                                (:menu data)
                                :value
                                (:selected data)) :label))))))
  app-state
  {:target (. js/document (getElementById "app"))})
