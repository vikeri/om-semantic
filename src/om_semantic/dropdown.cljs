(ns om-semantic.dropdown
  (:require [om.core :as om :include-macros true]
            [cljs.core.async :refer [put!]]
            [om-tools.dom :as dom :include-macros true]))


(defn dropdown-select
  "Select dropdown item"
  [owner item e]
  (.stopPropagation e)
  (.preventDefault e)
  (doto owner
    (om/set-state! :open false)
    (om/set-state! :selected item))
  (when-let [chan (om/get-state owner :chan)]
    (put! chan item)
    nil))

(defn dropdown-click
  "Dropdown is clicked"
  [owner open]
  (om/set-state! owner :open (not open)))

(defn -itemdiv
  "Generates item div"
  [{:keys [value label] :as item} owner selected]
  (dom/div {:class      (str "item"
                             (when (= value (:value selected))
                               " active selected"))
            :key        value
            :onClick    (fn [e] (dropdown-select owner item e))
            :data-value value
            :data-text  label}
           label))

(defn dropdown
  "A simple dropdown"
  [data owner]
  (reify
    om/IDisplayName
    (display-name [_]
      "Dropdown")
    om/IInitState
    (init-state [_]
      {:selected (:selected data)
       :default-text "-"
       :tabidx 0
       :open false})
    om/IWillReceiveProps
    (will-receive-props [_ next-props]
      (let [next-selected (:selected next-props)]
        (when-not (= (om/get-state owner :selected) next-selected)
          (om/set-state! owner :selected next-selected))))
    om/IRenderState
    (render-state [_ state]
      (let [def-text (:default-text state)
            items (:menu data)
            open (:open state)
            selected (:selected state)
            tclass (str "text" (when-not selected " default"))
            mclass (str "menu" (when open " transition visible"))
            text (if selected (:label selected) def-text)
            itemdiv #(-itemdiv % owner selected)]
        (dom/div
          {:class   "ui selection dropdown"
           :onBlur #(om/set-state! owner :open false)
           :tabIndex (:tabidx state)
           :onClick #(dropdown-click owner open)}
          (dom/input {:type    "hidden"
                      :key     "input"
                      :name    "gender"})
          (dom/i {:class "dropdown icon"})
          (dom/div {:class tclass} text)
          (dom/div {:class mclass
                    :key "dropdown-menu"}
                   (map itemdiv items)))))))