(ns invite.routes.home
  (:use cooljure.core)
  (:require [compojure.core     :refer :all]
            [invite.core         :as core]
            [clojure.data.json :as json]
            [ring.util.response :as resp]
            [cooljure.parse     :as coolp]))
             
(def g (atom core/default-graph))
 
(defn load []
  "Load the content from input.text to the graph"
  (reset! g (core/read-graph "resources/input.txt" @g ))
  (let [results {:status 200 :headers {"Content-Type" "application/json"} :body 
                (str "{\"message\": \"The graph has been populated.\"}")}] results))


(defn numeric? [s]
  (if-let [s (seq s)]
    (let [s (if (= (first s) \-) (next s) s)
          s (drop-while #(Character/isDigit %) s)
          s (if (= (first s) \.) (next s) s)
          s (drop-while #(Character/isDigit %) s)]
      (empty? s))))

(defn handle-nom-numeric []
  (do
    (let [results {:status 500 :headers {"Content-Type" "application/json"} :body 
                 (str "{\"message\": \"Oops, this method only accept numbers.\"}")}] results)))

(defn add-edge
  [id-1 id-2]
  "Add a new egde to the graph"
  (if (and (numeric? id-1 ) (numeric? id-2))
  (do
    (reset! g (core/add-edge @g [ (. Integer parseInt id-1) (. Integer parseInt id-2)]))
    (let [results {:status 200 :headers {"Content-Type" "application/json"} :body 
                (str "{\"message\": \"An invite from " id-1 " to " id-2 " has been sent\"}")}] results))
    (handle-nom-numeric)))
  

(defn show-ranking []
  "Returns a JSON with a list of edges sorted by points"
 (let [results {:status 200 :headers {"Content-Type" "application/json"} :body 
                (json/write-str (core/rank  @g))}] results))


(defn reset
  "Set the graph as empty"
  []
  (reset! g core/default-graph)
   (let [results {:status 200 :headers {"Content-Type" "application/json"} :body 
                (str "{\"message\": \"The graph has been reseted\"}")}] results))
  

(defroutes home-routes
  (GET     "/"               []  (resp/resource-response "index.html" {:root "public"}))
  (POST    "/graph/:n1/:n2"  [n1 n2]     (add-edge n1 n2))
  (POST     "/graph/load"    []          (load))
  (GET     "/graph"          []          (show-ranking))
  (DELETE  "/graph"          []          (reset)))

