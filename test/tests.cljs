(ns om-semantic.tests
  (:require [cljs.test :refer-macros [is testing deftest]]
            [cljs-react-test.utils :as tu]))

(enable-console-print!)

(deftest basic-test-handling 
  (testing "Simple test"
    (tu/new-container!)
    (is (= 1 1))))


