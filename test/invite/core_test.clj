(ns invite.core-test
  (:require [clojure.test :refer :all]
            [invite.core :refer :all]))


(def g default-graph)

(defn get-points* [graph node]
  (->> (get-points graph node)
       (float)
       (format "%.3f")))

(deftest example-test
  (testing "Given example"
    (let [g* (add-edges g [[1 2]
                           [1 3]
                           [3 4]
                           [2 4]
                           [4 5]
                           [4 6]])]
      (is (= 5/2 (get-points g* 1)))
      (is (= 0   (get-points g* 2)))
      (is (= 1   (get-points g* 3)))
      (is (= 0   (get-points g* 4)))
      (is (= 0   (get-points g* 5)))
      (is (= 0   (get-points g* 6))))))
	  
(deftest path-test
  (testing "Path"
    (let [g* (add-edges g [[1 2]
                           [2 3]
                           [3 4]
                           [4 5]
                           [5 6]])]
      (is (= 15/8 (get-points g* 1)))
      (is (= 7/4  (get-points g* 2)))
      (is (= 3/2  (get-points g* 3)))
      (is (= 1    (get-points g* 4)))
      (is (= 0    (get-points g* 5)))
      (is (= 0    (get-points g* 6))))))

(deftest path-with-two-ends-test
  (testing "Path with 2 ends"
    (let [g* (add-edges g [[1 2]
                           [2 3]
                           [3 4]
                           [3 5]
                           [4 6]
                           [5 7]])]
      (is (= 2 (get-points g* 1)))
      (is (= 2 (get-points g* 2)))
      (is (= 2 (get-points g* 3)))
      (is (= 0 (get-points g* 4)))
      (is (= 0 (get-points g* 5)))
      (is (= 0 (get-points g* 6)))
      (is (= 0 (get-points g* 7))))))

(deftest perfectly-balanced-binary-tree-test
  (testing "Perfectly balanced binary tree"
    (let [g* (add-edges g [[1 2]
                           [1 3]
                           [2 4]
                           [2 5]
                           [3 6]
                           [3 7]
                           [4 8]
                           [4 9]
                           [5 10]
                           [5 11]
                           [6 12]
                           [6 13]
                           [7 14]
                           [7 15]])]
      (is (= 4 (get-points g* 1)))
      (is (= 2 (get-points g* 2)))
      (is (= 2 (get-points g* 3)))
      (is (= 0 (get-points g* 4)))
      (is (= 0 (get-points g* 5)))
      (is (= 0 (get-points g* 6)))
      (is (= 0 (get-points g* 7)))
      (is (= 0 (get-points g* 8)))
      (is (= 0 (get-points g* 9)))
      (is (= 0 (get-points g* 10)))
      (is (= 0 (get-points g* 11)))
      (is (= 0 (get-points g* 12)))
      (is (= 0 (get-points g* 13)))
      (is (= 0 (get-points g* 14)))
      (is (= 0 (get-points g* 15))))))

(deftest cycle-test
  (testing "Cycle"
    (let [g* (add-edges g [[1 2]
                           [2 3]
                           [3 4]
                           [4 5]
                           [5 1]])]
      (is (= 15/8 (get-points g* 1)))
      (is (= 7/4  (get-points g* 2)))
      (is (= 3/2  (get-points g* 3)))
      (is (= 1    (get-points g* 4)))
      (is (= 0    (get-points g* 5))))))

#_(deftest input-test
  (testing "Input file"
    (let [g* (read-graph "resources/input.txt")]
      (is (= "2.000" (get-points* g* 1)))   ;; 1 + (points 2) / 2
      (is (= "2.000" (get-points* g* 2)))   ;; 1 + (points 3) / 2
      (is (= "2.000" (get-points* g* 3)))   ;; 1 + (points 4) / 2
      (is (= "2.000" (get-points* g* 4)))   ;; 1 + (points 5) / 2
      (is (= "2.000" (get-points* g* 5)))   ;; 1 + (points 6) / 2
      (is (= "2.000" (get-points* g* 6)))   ;; 1 + (points 7) / 2
      (is (= "2.000" (get-points* g* 7)))   ;; 1 + (points 8) / 2
      (is (= "2.000" (get-points* g* 8)))   ;; 1 + (points 9) / 2
      (is (= "2.000" (get-points* g* 9)))   ;; 1 + (points 10) / 2
      (is (= "2.000" (get-points* g* 10)))  ;; 1 + (points 11) / 2
      (is (= "2.000" (get-points* g* 11)))  ;; 1 + (points 12) / 2
      (is (= "2.000" (get-points* g* 12)))  ;; 1 + (points 13) / 2
      (is (= "2.000" (get-points* g* 13)))  ;; 1 + (points 14) / 2
      (is (= "2.000" (get-points* g* 14)))  ;; 1 + (points 15) / 2
      (is (= "2.000" (get-points* g* 15)))  ;; 1 + (points 16) / 2
      (is (= "2.000" (get-points* g* 16)))  ;; 1 + (points 17) / 2
      (is (= "2.000" (get-points* g* 17)))  ;; 1 + (points 18) / 2
      (is (= "2.000" (get-points* g* 18)))  ;; 1 + (points 19) / 2
      (is (= "2.000" (get-points* g* 19)))  ;; 1 + (points 20) / 2
      (is (= "2.000" (get-points* g* 20)))  ;; 1 + (points 21) / 2
      (is (= "2.000" (get-points* g* 21)))  ;; 1 + (points 22) / 2
      (is (= "2.000" (get-points* g* 22)))  ;; 1 + (points 23) / 2
      (is (= "2.000" (get-points* g* 23)))  ;; 1 + (points 24) / 2
      (is (= "2.000" (get-points* g* 24)))  ;; 1 + (points 25) / 2
      (is (= "2.000" (get-points* g* 25)))  ;; 1 + (points 26) / 2
      (is (= "2.000" (get-points* g* 26)))  ;; 1 + (points 27) / 2
      (is (= "2.000" (get-points* g* 27)))  ;; 1 + (points 28) / 2
      (is (= "2.000" (get-points* g* 28)))  ;; 1 + (points 29) / 2
      (is (= "2.000" (get-points* g* 29)))  ;; 1 + (points 30) / 2
      (is (= "2.000" (get-points* g* 30)))  ;; 1 + (points 31) / 2
      (is (= "2.000" (get-points* g* 31)))  ;; 1 + (points 32) / 2
      (is (= "2.000" (get-points* g* 32)))  ;; 1 + (points 33) / 2
      (is (= "2.000" (get-points* g* 33)))  ;; 1 + (points 34) / 2
      (is (= "2.001" (get-points* g* 34)))  ;; 1 + (points 35) / 2
      (is (= "2.001" (get-points* g* 35)))  ;; 1 + (points 36) / 2
      (is (= "2.003" (get-points* g* 36)))  ;; 1 + (points 37) / 2
      (is (= "2.006" (get-points* g* 37)))  ;; 1 + (points 38) / 2
      (is (= "2.012" (get-points* g* 38)))  ;; 1 + (points 39) / 2
      (is (= "2.023" (get-points* g* 39)))  ;; 1 + (points 40) / 2
      (is (= "2.047" (get-points* g* 40)))  ;; 1 + (points 41) / 2
      (is (= "2.094" (get-points* g* 41)))  ;; 1 + (points 42) / 2
      (is (= "2.188" (get-points* g* 42)))  ;; 1 + (points 43) / 2
      (is (= "2.375" (get-points* g* 43)))  ;; 1 + (points 44) / 2
      (is (= "2.750" (get-points* g* 44)))  ;; 1 + (points 45) / 2
      (is (= "3.500" (get-points* g* 45)))  ;; 1 + (points 46) / 2
      (is (= "5.000" (get-points* g* 46)))  ;; 1 + (points 47) / 2
      (is (= "8.000" (get-points* g* 47)))  ;; 1 + (points 48) / 2
      (is (= "14.000" (get-points* g* 48))) ;; 1 + (points 49) / 2
      (is (= "26.000" (get-points* g* 49))) ;; 1 + (points 50) / 2
      (is (= "50.000" (get-points* g* 50))) ;; 51, ..., 100
      (is (= "0.000" (get-points* g* 51)))
      (is (= "0.000" (get-points* g* 52)))
      (is (= "0.000" (get-points* g* 53)))
      (is (= "0.000" (get-points* g* 54)))
      (is (= "0.000" (get-points* g* 55)))
      (is (= "0.000" (get-points* g* 56)))
      (is (= "0.000" (get-points* g* 57)))
      (is (= "0.000" (get-points* g* 58)))
      (is (= "0.000" (get-points* g* 59)))
      (is (= "0.000" (get-points* g* 60)))
      (is (= "0.000" (get-points* g* 61)))
      (is (= "0.000" (get-points* g* 62)))
      (is (= "0.000" (get-points* g* 63)))
      (is (= "0.000" (get-points* g* 64)))
      (is (= "0.000" (get-points* g* 65)))
      (is (= "0.000" (get-points* g* 66)))
      (is (= "0.000" (get-points* g* 67)))
      (is (= "0.000" (get-points* g* 68)))
      (is (= "0.000" (get-points* g* 69)))
      (is (= "0.000" (get-points* g* 70)))
      (is (= "0.000" (get-points* g* 71)))
      (is (= "0.000" (get-points* g* 72)))
      (is (= "0.000" (get-points* g* 73)))
      (is (= "0.000" (get-points* g* 74)))
      (is (= "0.000" (get-points* g* 75)))
      (is (= "0.000" (get-points* g* 76)))
      (is (= "0.000" (get-points* g* 77)))
      (is (= "0.000" (get-points* g* 78)))
      (is (= "0.000" (get-points* g* 79)))
      (is (= "0.000" (get-points* g* 80)))
      (is (= "0.000" (get-points* g* 81)))
      (is (= "0.000" (get-points* g* 82)))
      (is (= "0.000" (get-points* g* 83)))
      (is (= "0.000" (get-points* g* 84)))
      (is (= "0.000" (get-points* g* 85)))
      (is (= "0.000" (get-points* g* 86)))
      (is (= "0.000" (get-points* g* 87)))
      (is (= "0.000" (get-points* g* 88)))
      (is (= "0.000" (get-points* g* 89)))
      (is (= "0.000" (get-points* g* 90)))
      (is (= "0.000" (get-points* g* 91)))
      (is (= "0.000" (get-points* g* 92)))
      (is (= "0.000" (get-points* g* 93)))
      (is (= "0.000" (get-points* g* 94)))
      (is (= "0.000" (get-points* g* 95)))
      (is (= "0.000" (get-points* g* 96)))
      (is (= "0.000" (get-points* g* 97)))
      (is (= "0.000" (get-points* g* 98)))
      (is (= "0.000" (get-points* g* 99)))
      (is (= "0.000" (get-points* g* 100)))
      (is (= "0.000" (get-points* g* 101)))
      (is (= "0.000" (get-points* g* 102)))
      (is (= "0.000" (get-points* g* 103)))
      (is (= "0.000" (get-points* g* 104)))
      (is (= "0.000" (get-points* g* 105)))
      (is (= "0.000" (get-points* g* 106)))
      (is (= "0.000" (get-points* g* 107)))
      (is (= "0.000" (get-points* g* 108)))
      (is (= "0.000" (get-points* g* 109)))
      (is (= "0.000" (get-points* g* 110)))
      (is (= "0.000" (get-points* g* 111)))
      (is (= "0.000" (get-points* g* 112)))
      (is (= "0.000" (get-points* g* 113)))
      (is (= "0.000" (get-points* g* 114)))
      (is (= "0.000" (get-points* g* 115)))
      (is (= "0.000" (get-points* g* 116)))
      (is (= "0.000" (get-points* g* 117)))
      (is (= "0.000" (get-points* g* 118)))
      (is (= "0.000" (get-points* g* 119)))
      (is (= "0.000" (get-points* g* 120)))
      (is (= "0.000" (get-points* g* 121)))
      (is (= "0.000" (get-points* g* 122)))
      (is (= "0.000" (get-points* g* 123)))
      (is (= "0.000" (get-points* g* 124)))
      (is (= "0.000" (get-points* g* 125)))
      (is (= "0.000" (get-points* g* 126)))
      (is (= "0.000" (get-points* g* 127)))
      (is (= "0.000" (get-points* g* 128)))
      (is (= "0.000" (get-points* g* 129)))
      (is (= "0.000" (get-points* g* 130)))
      (is (= "0.000" (get-points* g* 131)))
      (is (= "0.000" (get-points* g* 132)))
      (is (= "0.000" (get-points* g* 133)))
      (is (= "0.000" (get-points* g* 134)))
      (is (= "0.000" (get-points* g* 135)))
      (is (= "0.000" (get-points* g* 136)))
      (is (= "0.000" (get-points* g* 137)))
      (is (= "0.000" (get-points* g* 138)))
      (is (= "0.000" (get-points* g* 139)))
      (is (= "0.000" (get-points* g* 140)))
      (is (= "0.000" (get-points* g* 141)))
      (is (= "0.000" (get-points* g* 142)))
      (is (= "0.000" (get-points* g* 143)))
      (is (= "0.000" (get-points* g* 144)))
      (is (= "0.000" (get-points* g* 145)))
      (is (= "0.000" (get-points* g* 146)))
      (is (= "0.000" (get-points* g* 147)))
      (is (= "0.000" (get-points* g* 148)))
      (is (= "0.000" (get-points* g* 149)))
      (is (= "0.000" (get-points* g* 150)))
      (is (= "0.000" (get-points* g* 160)))
      (is (= "0.000" (get-points* g* 180)))
      (is (= "0.000" (get-points* g* 190))))))