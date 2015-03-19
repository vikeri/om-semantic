(ns ^:figwheel-always examples.dropdown.core
  (:require-macros [cljs.core.async.macros :refer [go-loop]])
  (:require [om.core :as om :include-macros true]
            [cljs.core.async :refer [chan <!]]
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

(defonce app-state (atom {:menu menu}))

(defn button
  [data owner]
  (om/component
    (dom/div {:class   "ui button"
              :onClick #(om/update! data :selected (last menu))}
             (:label (last menu)))))

(om/root
  (fn [data owner]
    (reify
      om/IInitState
      (init-state [_]
        {:chan (chan)})
      om/IWillMount
      (will-mount [_]
        (go-loop []
          (let [selected (<! (om/get-state owner :chan))]
            (om/update! data :selected selected))
          (recur)))
      om/IRenderState
      (render-state [_ state]
        (dom/div {:class "ui two column centered grid"}
                 (dom/div {:class "ui segment column"}
                          (dom/h1 "Dropdown example")
                          (om/build button data)
                          (om/build dd/dropdown
                                    data
                                    {:init-state
                                     {:default-text "Pick Character"
                                      :chan         (:chan state)}})
                          (dom/div {:class "ui divider"})
                          (dom/div "You picked: "
                                   (get-in data [:selected :label])))))))
  app-state
  {:target (. js/document (getElementById "app"))})