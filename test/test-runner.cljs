(ns test.test-runner
  (:require
   [cljs.test :as test :refer-macros [run-tests] :refer [report]]
   [om-semantic.tests]))

(enable-console-print!)

(defn runner []
  (if (cljs.test/successful? (run-tests 'om-semantic.tests))
    0
    1))
