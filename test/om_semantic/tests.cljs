(ns om-semantic.tests
 (:require [cljs.test :refer-macros [run-tests]]
           [cljs-react-test.utils :as tu]))

(deftest basic-test-handling 
  (testing "Simple test"
    (tu/new-container!)
    (is (= 1 1))))


