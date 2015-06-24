(ns test.test-runner
  (:require [cljs.test :refer [successful?] :refer-macros [run-tests]]
            [doo.runner :refer-macros [doo-tests]]
            [om-semantic.tests]))

(enable-console-print!)

(doo-tests 'om-semantic.tests)
