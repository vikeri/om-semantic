(ns om-semantic.dropdown
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

;; Utilities - should be moved to separate file when more components are added

(defn find-key
  "Matches a key in a map inside a vector against a value and returns map"
  [-vector -key value]
  (first (filter #(= (get % -key) value) -vector)))

;; Dropdown event functions

(defn dropdown-select
  "Select dropdown item"
  [owner value data e]
  (.stopPropagation e)
  (.preventDefault e)
  (om/set-state! owner :open false)
  (let [selected (om/get-state owner :selected)
        cursor   (if (< 1 (count selected))
                   (get-in data (pop selected))
                   data)]
    (om/update! cursor (last selected) value)))

(defn dropdown-click
  "Dropdown is clicked"
  [owner open]
  (om/set-state! owner :open (not open)))

(defn -itemdiv
  "Generates item div"
  [item owner selected idkey lkey data]
  (let [value (get item idkey)
        label (get item lkey)]
    (dom/div #js {:className (str "item"
                                       (when (= value selected)
                                         " active selected"))
                  :key        value
                  :onClick    (fn [e] (dropdown-select owner value data e))
                  :data-value value
                  :data-text  label}
             label)))

;; Dropdown component

(defn dropdown
  "A simple dropdown component for Om using Semantic UI css
  init-state:
   selected: kork to the selected item id
   menu: kork to the menu items
   idkey: which key in the menu items that should be used as value
   tabidx: tabIndex
   default-text: What shows if nothing is selected
   lkey: which key in the menu items that should be used as label (text)
   name: the name of the input field"
  [data owner]
  (reify
    om/IDisplayName
    (display-name [_]
      "Dropdown")
    om/IInitState
    (init-state [_]
      {:default-text "-"
       :name "dropdown"
       :tabidx 0
       :open false})
    om/IRenderState
    (render-state [_ state]
      (let [def-text (:default-text state)
            lkey (:lkey state)
            idkey (:idkey state)
            items (get-in data (:menu state))
            open (:open state)
            selected (get-in data (:selected state))
            tclass (str "text" (when-not selected " default"))
            mclass (str "menu" (when open " transition visible"))
            text (if selected (get (find-key items idkey selected) lkey)
                              def-text)
            itemdiv #(-itemdiv % owner selected idkey lkey data)]
        (dom/div
          #js {:className   "ui selection dropdown"
               :onBlur #(om/set-state! owner :open false)
               :tabIndex (:tabidx state)
               :onClick #(dropdown-click owner open)}
          (dom/input #js {:type    "hidden"
                          :key     "input"
                          :name    name})
          (dom/i #js {:className "dropdown icon"})
          (dom/div #js {:className tclass} text)
          (apply dom/div #js {:className mclass
                        :key "dropdown-menu"}
                   (map itemdiv items)))))))