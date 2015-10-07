(ns test.runner
  (:require [cljs.test]
            [doo.runner :refer-macros [doo-tests]]
            [om-semantic.tests.dropdown]
            [om-semantic.tests.rating]))

(enable-console-print!)

(doo-tests 'om-semantic.tests.dropdown
           'om-semantic.tests.rating)
