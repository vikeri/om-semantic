(ns om-semantic.dropdown
  (:require [om.core :as om :include-macros true]
            [om-tools.dom :as dom :include-macros true]))

;; Utilities - should be moved to separate file when more components are added

(defn select-cursor-key
  "As select-key but works for cursors"
  [coll k]
  (apply dissoc coll (keys (dissoc coll k))))

(defn jswarn
  "Throws a javascript warning"
  [warn]
  (.warn js/console warn))

;; Dropdown event functions

(defn dropdown-select
  "Select dropdown item"
  [owner item e]
  (.stopPropagation e)
  (.preventDefault e)
  (doto owner
    (om/set-state! :open false)
    (om/set-state! :selected item))
  (om/update! (om/get-state owner :selected-cursor)
              (om/get-state owner :skey)
              item))

(defn dropdown-click
  "Dropdown is clicked"
  [owner open]
  (om/set-state! owner :open (not open)))

(defn -itemdiv
  "Generates item div"
  [item owner selected idkey lkey]
  (let [value (get item idkey)
        label (get item lkey)]
    (dom/div {:class      (str "item"
                               (when (= value (get selected idkey))
                                 " active selected"))
              :key        value
              :onClick    (fn [e] (dropdown-select owner item e))
              :data-value value
              :data-text  label}
             label)))

;; Dropdown component

(defn dropdown
  "A simple dropdown component for Om using Semantic UI css
  opts:
   skey: the key to the cursor that holds the selected item
   mkey: the key to the cursor that holds the menu items
   idkey: which key in the menu items that should be used as value
   lkey: which key in the menu items that should be used as label (text)"
  [data owner {:keys [skey mkey idkey lkey] :as opts}]
  (reify
    om/IDisplayName
    (display-name [_]
      "Dropdown")
    om/IInitState
    (init-state [_]
      (let [missing (apply disj #{:skey :mkey :idkey :lkey} (keys opts))]
        (if-not (empty? missing) (jswarn (str "No " (first missing) " set for dropdown"))))
      {:selected-cursor (select-cursor-key data skey)
       :selected (get data skey)
       :default-text "-"
       :skey skey
       :tabidx 0
       :open false})
    om/IWillReceiveProps
    (will-receive-props [_ next-props]
      (println next-props)
      (let [next-selected (get next-props skey)]
        (when-not (= (om/get-state owner :selected) next-selected)
          (om/set-state! owner :selected next-selected))))
    om/IRenderState
    (render-state [_ state]
      (let [def-text (:default-text state)
            items (get data mkey)
            open (:open state)
            selected (:selected state)
            tclass (str "text" (when-not selected " default"))
            mclass (str "menu" (when open " transition visible"))
            text (if selected (get selected lkey) def-text)
            itemdiv #(-itemdiv % owner selected idkey lkey)]
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