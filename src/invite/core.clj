(ns invite.core)

;; ## Graph functions

(defn make-edge [graph tail head]
  (if (contains? graph head)
    (assoc-in graph [tail :out head] ::invalid)
    (-> graph
        (assoc-in [tail :out head] 0)
        (assoc-in [head :in] tail))))

(defn calculate-points-edge [graph tail head]
  (assoc-in graph [tail :out head]
            (let [{:keys [points out] :or {points 0}} (get graph head)]
              (if (empty? out)
                0
                (+ 1 (/ points 2))))))

(defn calculate-points-node [graph node]
  (assoc-in graph [node :points]
            (->> (get-in graph [node :out])
                 ;; Maps are represented as a seq of tuples [key value].
                 ;; So the next line will extract all the points of the edges.
                 (map second)
                 (remove #(= ::invalid %))
                 (reduce + 0))))

(defn calculate-points-parents [graph node]
  (loop [child node g graph]
    (if-let [parent (get-in g [child :in])]
      (recur parent
             (-> g
                 (calculate-points-edge parent child)
                 (calculate-points-node parent)))
      g)))

;; ## Public api

;; We represent the graph by a map, keys are nodes and values are a map with the following data
;; {:out {node-1 points-from-1
;;        node-2 points-from-2}   <-- This map represents the outgoing edges,
;;                                    keys are the heads of the edges,
;;                                    values are the points earned via the edges
;;                                    (i.e. O or 1 + (points-head / 2)).
;;                                    If the edge is invalid, then the points of the edge = ::invalid.
;;  :in node                      <-- The tail of the incoming edge or nil
;;                                    (there can only be one valid invitation).
;;  :points 0}                    <-- The total points of this node.
;;                                    This equals the sum of the points of the :out edges.
;;
;; Points are stored as BigDecimal.

(def default-graph
  {})

(defn add-edge [graph [tail head]]
  "Add an edge to the graph."
  (-> graph
      (make-edge tail head)
      (calculate-points-parents tail)))

(defn add-edges [graph edges]
  "Add a seq of edges to the graph."
  ;; Add the edges one by one using add-edge
  (reduce add-edge graph edges))

(defn get-points [graph node]
  "Get the points for the given graph and node."
  (get-in graph [node :points] 0))

(defn read-edges [filename]
  "Read the given file into clojure seq.
  Each line should contain the tail and head node of an edge, separated with a space."
  (with-open [rdr (clojure.java.io/reader filename)]
    (->> (line-seq rdr)
         ;; line-seq is lazy, so make sure to evaluate it before continuing
         ;; because otherwise we might read when the reader has been closed
         (doall)
         (map #(->> (clojure.string/split % #"\s")
                    (map (fn [a] (Integer/parseInt a))))))))


(defn read-graph [filename graph]
  "Read the given file of edges into the default graph."
  (->> (read-edges filename)
       (add-edges graph)))

(defn rank [graph]
  "Returns the list of nodes sorted by points"
     (sort-by :points > ( for [key (keys graph)]
      (apply hash-map [:id key :points (get-points graph key)]))))
