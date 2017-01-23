(ns pyramids.pieces)

(def B {
  :color [50 150 50 255]
  :points #{ [0 0 0] [0 1 0] [0 1 1] [1 1 1] [1 1 2] [0 1 2] [0 1 3]}})

(def A {
  :color [200 50 0 200] 
  :points #{ [2 0 0] [1 0 0] [1 0 1]} })

(def C { 
  :color [25 47 0 255] 
  :points #{ [2 0 0] [1 0 0] [1 0 1] [0 0 0] } })
  
(def E {
  :color [255 255 0 255]
  :points #{[1 1 1] [1 0 0] [1 0 1 ] [1 1 2] [1 0 2] [1 0 3]}
})

(def G {
  :color [255 0 0 255]
  :points #{[0 0 0] [1 0 0] [0 1 0] [0 1 1] [1 0 1] [1 1 1] [1 1 2]}
})

  
(defn- add-coordinates [set-of-points x y z] (set (map
  (fn [p] (mapv + p [x y z])) set-of-points)))

(defn shift-piece' [set-of-points x y z]
  (let [odd (mod z 2)
        deltafn (fn [x] (+ odd (* 2 x)))]
        (add-coordinates set-of-points (deltafn x) (deltafn y) (* 2 z))))

(defn shift [p x y z] (update p :points shift-piece' x y z))

(defn turn [p]
  (update p :points
  #(set (map (fn [[x y z]] [(- y) x z]) %))))

(defn flip [p]
  (update p :points
  #(set (map (fn [[x y z]] [x (- y) (- 3 z)]) %))))

