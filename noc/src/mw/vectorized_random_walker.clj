(ns mw.vectorized-random-walker
  (:gen-class)
  (:require [quil.core :as q]
           [quil.middleware :as mid]
           [clojure.core.matrix :as m]))


(defn make-vector [x y] 
   (m/array [x y]))

(defn rand-step [] 
  (q/random -1 1))

(defn setup []
  (q/background 255)
  (q/frame-rate 120)
  (q/stroke 0)
  (m/set-current-implementation :vectorz)
  (make-vector (/ (q/width) 2)
          (/ (q/height) 2)))

(defn draw-state [v]
  (q/point (first v) (second v)) )

(defn update-state [rand-fn walker]
  (m/add walker (make-vector (rand-fn) (rand-fn))))

(defn screen 
  ([& {:keys [title update-state-fn draw-state-fn setup-fn]
       :or {title "Sketch"
            update-state-fn (partial update-state rand-step)
            setup-fn setup
            draw-state-fn draw-state}}]
   (q/sketch
    :title title 
    :size [483 300]
    :setup setup-fn
    :update update-state-fn
    :draw draw-state-fn
    :features [:keep-on-top]
    :middleware [mid/fun-mode])))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (screen))



