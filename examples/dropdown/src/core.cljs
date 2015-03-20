(ns examples.dropdown.core
  (:require [om.core :as om :include-macros true]
            [om-semantic.dropdown :as dd]
            [om-tools.dom :as dom :include-macros true]))

(enable-console-print!)

(.clear js/console)

(def menu
  (mapv #(hash-map
          :value (clojure.string/lower-case %)
          :label %)
        ["Viktor"
         "Sebastian"
         "Pelle"
         "Rikard"
         "Supertramp"]))

(defonce app-state (atom {:menu menu
                          :selected nil}))

(defn button
  [data owner]
  (om/component
    (dom/div {:class   "ui button"
              :onClick #(om/update! data :selected (last menu))}
             (:label (last menu)))))

(om/root
  (fn [data owner]
    (reify
      om/IRender
      (render [_]
              (dom/div
                (dom/h3 "Dropdown example")
                (om/build button data)
                (om/build dd/dropdown
                          data
                          {:init-state
                           {:default-text "Pick Character"}
                           :opts
                           {:skey  :selected
                            :mkey  :menu
                            :idkey :value
                            :lkey  :label}})
                ;(dom/br)
                (dom/span " You picked: "
                         (get-in data [:selected :label]))))))
  app-state
  {:target (. js/document (getElementById "app"))})