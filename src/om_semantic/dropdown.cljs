(ns om-semantic.dropdown
  (:require [cljs.core.async :as async :refer [put!]]
            [om.core :as om :include-macros true]
            [goog.style :as style]
            [goog.dom :as gom]
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
    (when-let [ch (om/get-state owner :ch)]
      (put! ch [:select value]))
    (om/update! cursor (last selected) value)))

(defn dropdown-click
  "Dropdown is clicked"
  [owner]
  (when-let [ch (om/get-state owner :ch)]
    (put! ch [:click nil]))
  (om/update-state! owner :open not))

(defn -itemdiv
  "Generates item div"
  [item owner selected idkey lkey data]
  (let [value (get item idkey)
        label (get item lkey)]
    (dom/div #js {:className (str "item" (if (= value selected)
                                           " active selected"))
                  :key        value
                  :onClick    (fn [e] (dropdown-select owner value data e))
                  :data-value value
                  :data-text  label}
             label)))

(defn upward?
  "Calculates if the dropdown should show upward"
  [node item-count]
  (let [node-height (.-height (style/getSize node))
        dropdown-height (* (+ 1 item-count) node-height)
        top-offset (.-y (style/getClientPosition node))]
    (and
      (< (- dropdown-height node-height) top-offset)
      (< (.-height (gom/getViewportSize))
         (+ dropdown-height top-offset)))))


(defn set-upward
  "Checks if the dropdown is too close to the bottom
  and sets state to go upward if it would be the case"
  [owner items]
  (om/set-state! owner
                  :upward
                  (upward? (om/get-node owner)
                           (count items))))

;; Dropdown component

(defn dropdown
  "A simple dropdown component for Om using Semantic UI CSS 
  init-state:
   selected: kork to the selected item id
   menu: kork to the menu items
   idkey: which key in the menu items that should be used as value
   tabidx: tabIndex
   class: a class to add to element ui select dropdown
   disabled: if the dropdown is disabled or not
   default-text: What shows if nothing is selected
   lkey: which key in the menu items that should be used as label (text)
   name: the name of the input field"
  [data owner {:keys [ch]}]
  (reify
    om/IDisplayName
    (display-name [_]
      "Dropdown")
    om/IInitState
    (init-state [_]
      {:default-text "-"
       :name "dropdown"
       :tabidx 0
       :disabled false
       :open false
       :upward false
       :ch ch})
    om/IDidMount
    (did-mount [_]
      (set-upward owner (get-in data (:menu (om/get-state owner)))))
    om/IRenderState
    (render-state [_ {:keys [name lkey idkey open disabled
                             tabidx default-text class upward] :as state}]
      (let [items (get-in data (:menu state))
            selected (get-in data (:selected state))
            class (str "ui selection dropdown "
                       (if class class)
                       (if upward " upward")
                       (cond open " active visible"
                             disabled " disabled"))
            tclass (str "text" (if-not selected " default"))
            text (if selected (get (find-key items idkey selected) lkey)
                              default-text)
            itemdiv #(-itemdiv % owner selected idkey lkey data)
            ]
        (dom/div
          #js {:className class
               :onBlur #(om/set-state! owner :open false)
               :tabIndex tabidx
               :onMouseOver #(set-upward owner items)
               :onClick (if-not disabled #(dropdown-click owner))}
          (dom/input #js {:type "hidden"
                          :key  "input"
                          :name name})
          (dom/i #js {:className "dropdown icon"})
          (dom/div #js {:className tclass} text)
          (if open
            (apply dom/div #js {:className "menu transition visible"
                                :key       "dropdown-menu"}
                   (map itemdiv items))))))))
