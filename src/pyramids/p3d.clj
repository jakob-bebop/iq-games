(ns pyramids.p3d
  (:require [quil.core :as q]))

(def layer-height (atom 8))


(defn transform-point [p]
    (mapv * p 
      (repeat @layer-height )
      [1 1 (Math/sqrt 2)]))

(defn vertices [& points]
;; (println points)
  (doseq [p points]
;;     (println c)
    (apply q/vertex (transform-point p))))

(defn- sq-corners [x y z]
  (let [c1 [(dec x) (dec y) z]
        c2 [(inc x) (dec y) z]
        c3 [(inc x) (inc y) z]
        c4 [(dec x) (inc y) z]]
      [c1 c2 c3 c4]))

(defn draw-pyramid [c1 c2 c3 c4 t]
(q/stroke 0)
      (q/begin-shape)
      (vertices c1 c2 c3 c4)
      (q/end-shape :close)
;;     (makesphere (transform-point t) 2)
;;     (q/with-fill [200 50 0 50]
      (q/begin-shape :triangle-fan)
        (vertices t c1 c4 c3 c2 c1)
      (q/end-shape))


(defn draw-pyramid-up [x y z]
  (apply draw-pyramid (conj (sq-corners x y z) [x y (inc z)])))

(defn draw-pyramid-down [x y z]
  (apply draw-pyramid (conj (sq-corners x y (inc z)) [x y z])))

(defn draw-tetra [x y z]
  (let [i (mod (+ x z) 2)
        j (- 1 i)
        a [x (dec y) (+ z j)]
        b [x (inc y) (+ z j)]
        c [(dec x) y (+ z i)]
        d [(inc x) y (+ z i)]]
    (q/stroke 0)
    (q/begin-shape :triangle-strip)
    (vertices b a d c b a)
    (q/end-shape)))


  
(defn draw [p]
  (let [set-of-points (:points p)
  xys (set (map (fn [[x y z]] [x y (int (Math/floor (/ z 2)))]) set-of-points))]
  (q/with-fill (:color p)
        (doseq [[x y z] xys]
          ((if (contains? set-of-points [x y (* 2 z)])
            (if (contains? set-of-points [x y (inc (* 2 z))])
              draw-tetra
              draw-pyramid-up)
            draw-pyramid-down) x y z)))))
