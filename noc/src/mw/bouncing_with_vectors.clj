(ns mw.bouncing-with-vectors
  (:gen-class)
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [clojure.core.matrix :as mx]))

(defn ball [position speed]
  {:position position :speed speed})

(defn make-vector [ x y ]
  (mx/array [x y]))

(defn setup []
  (q/background 255)
  (q/frame-rate 60)
  (q/stroke 0)
  (q/fill (q/color 127 0 0))
  (let [position (make-vector 100 100)
        speed (make-vector 2.5 2)]
  (ball position speed)))

(defn draw-state [{:keys [position]}]
  (q/background 255)
  (q/ellipse (first position) (second position) 48 48))

(defn update-state [{:keys [position speed]}]
  (let  [new-position (mx/add position speed)
         newx (first new-position)
         newy (second new-position)
         xspeed (first speed)
         yspeed (second speed)] 
  (ball new-position (make-vector 
   (if (or (> newx (q/width)) (< newx 0)) (* (- 1)  xspeed) xspeed)
   (if (or (> newy (q/height)) (< newy 0)) (* (- 1) yspeed) yspeed)))))

(defn screen 
  ([& {:keys [title update-state-fn draw-state-fn setup-fn]
       :or {title "Bouncing with vectors"
            update-state-fn update-state
            setup-fn setup
            draw-state-fn draw-state}}]
   (q/sketch
    :title title 
    :size [640 240]
    :setup setup-fn
    :update update-state-fn
    :draw draw-state-fn
    :features [:keep-on-top]
    :middleware [m/fun-mode])))

(defn -main
  [& args]
  (screen))
