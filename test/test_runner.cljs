(ns test.test-runner
  (:require [cljs.test :refer [successful?] :refer-macros [run-tests]]
            [om-semantic.tests]))

(enable-console-print!)

(defn runner []
  (if (successful? (run-tests 'om-semantic.tests))
    0
    1))
