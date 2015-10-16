(ns test.runner
  (:require [cljs.test]
            [doo.runner :refer-macros [doo-tests]]
            [om-semantic.tests.dropdown]
            [om-semantic.tests.rating]
            [cljsjs.jquery]
            [semantic-ui.core]))

(enable-console-print!)

(doo-tests 'om-semantic.tests.dropdown
           'om-semantic.tests.rating)
