(ns pyramids.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [pyramids.dynamic :as d]))

(defn settings []
  (q/smooth))


(q/defsketch pyramids
  :title "You spin my circle right round"
  :size [500 500]
  :setup d/setup
  :settings settings
  :draw d/draw
  :update d/update-state
;;   :features [:keep-on-top]
  :middleware [m/fun-mode]
  :navigation-3d {
    :position [40 -40 40],
    :straight [-1 1 -1],
    :up [0 0 1]
    :step-size 2
  }
  :key-pressed d/handle-key
  :renderer :opengl)
