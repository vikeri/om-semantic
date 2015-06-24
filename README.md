## Om-Semantic

[Semantic UI](https://github.com/semantic-org/semantic-ui/) components built with [Om](https://github.com/omcljs/om)

Still very much in Alpha, use at own risk.

Examples: [vikeri.github.io/om-semantic](http://vikeri.github.io/om-semantic/)

## Available components

- Dropdown `dropdown.cljs`

## TODO

- Make dropdown tabbable, and to be able to select with up/down keys

## Add it as Leiningen dependency

![Clojars Project](http://clojars.org/om-semantic/latest-version.svg)

## Example usage

```clj
(ns examples.dropdown.core
  (:require [clojure.string :as str]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [om-semantic.dropdown :as dd]))

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
```

## Try it out

```sh
git clone https://github.com/vikeri/om-semantic.git
cd om-semantic
lein cljsbuild once dropdown
```
Open `index.html` in folder `examples/dropdown`

## Listen to Events

If you need to know when the dropdown was clicked or selected you can
pass a channel as an `opts` and listen and for `[:click nil]` and
`[:select selected-id]` events:

```clj
(defn dropdown-listener [data owner]
  (reify
    om/IInitState
    (init-state [_]
      {:ch (chan)})
    om/IWillMount
    (will-mount [_]
      (go-loop []
        (let [[tag d] (<! (om/get-state owner :ch))]
          (when (some? tag) ;; we check if the channel was closed
            (case tag
              :select (println "Selected: " d)
              :click (println "Clicked!"))
            (recur)))))
    om/IWillUnmount
    (will-unmount [_]
      ;; we close the channel before we leave to terminate the go-loop
      (async/close! (om/get-state owner :ch)))
    om/IRenderState
    (render-state [_ {:keys [ch]}]
      (om/build dd/dropdown data
        {:init-state {:default-text "Pick Character"
                      :menu [:menu]
                      :selected [:selected]
                      :idkey :value
                      :lkey :label
                      :disabled false}
		 :opts {:ch ch}}))))
```

## Testing

This project uses [doo](https://github.com/bensu/doo) for testing. To
use it follow its README to configure PhantomJS and run:

```sh
lein doo phantom test
```

## License

Licensed under the EPL
