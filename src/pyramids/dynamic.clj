(ns pyramids.dynamic
  (:require [quil.core :as q]
            [pyramids.pieces :as pieces]
            [pyramids.p3d :as p3d]))

(defn setup []
  ; Set frame rate to 30 frames per second.
  (q/frame-rate 8)
  ; Set color mode to HSB (HSV) instead of default RGB.
;;   (q/color-mode :hsv)
  ; setup function returns initial state. It contains
  ; circle color and position.
  ; Set circle color.
  (q/no-clip)

;;   (q/camera 0 -15 1 0 0 0 0 0 1)
  { :A pieces/A :B pieces/B :C pieces/C :D pieces/D
    :position [60 -60 60],
    :straight [-1 1 -1],
    :up [0 0 1]
    :turns 0
    :shift [0, 0, 0]
    :flip true
    :spin true
    })
            

(defn turn-cam [state]
 (let [ [x y z] (:position state)
        t -5
        sint (Math/sin (Math/toRadians t))
        cost (Math/cos (Math/toRadians t))
        x' (- (* x cost) (* y sint)) 
        y' (+ (* x sint) (* y cost))
        newpos (mapv #(* 1.00 %) [x' y' 40])]
     (assoc state :position newpos :straight [(- x') (- y') (- z)] :up [0 0 -1])))   

(defn update-state [state]
  (if (:spin state) (turn-cam state) state))

(defn handle-key [state event]

 (case (:key event)
   :c (update state :turns #(mod (inc %) 4))
   :e (update state :flip not)
   :p (update state :spin not)
   :a (update-in state [:shift 0] dec)
   :d (update-in state [:shift 0] inc)
   :w (update-in state [:shift 1] dec)
   :s (update-in state [:shift 1] inc)
   :z (update-in state [:shift 2] dec)
   :q (update-in state [:shift 2] inc)
   state))


(defn turnn [p n]
  (nth (iterate pieces/turn p) n))
(defn sshift [p [x y z]] (pieces/shift p x y z))
(defn flipp [p flag] (if flag (pieces/flip p) p))
(defn draw [state]
  ; Clear the sketch by filling it with light-grey color.
  ; Calculate x and y coordinates of the circle.
  (let [[x y z] (:position state)
        [upx upy upz] (:up state)] (q/camera x y z 0 0 0 upx upy upz)) 
  (q/lights)
  (q/background 100)
;;   (q/no-stroke)
;;   (q/fill 200 50 0 255)
;;   (q/fill 200 120 0 255)
;;   (q/box 50)
  (p3d/draw (:B state))  
;;   (println state)
  (-> (:D state)  (p3d/draw))
  (p3d/draw (:C state))  
  (-> (:A state) 
    (assoc :color [200 100 50 255])
    (flipp (:flip state))
    (pieces/shift 0 1 -1)
    (turnn (:turns state))
    (sshift (:shift state))  
    (p3d/draw))
  )

