(ns mw.bouncing-without-vectors
  (:gen-class)
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(set! *warn-on-reflection* true)

(defn setup []
  (q/background 255)
  (q/frame-rate 60)
  (q/stroke 0)
  (q/fill (q/color 127 0 0))
  {:x 100 :y 100 :xspeed 2.5 :yspeed 2})

(defn draw-state [{:keys [x y]}]
  (q/background 255)
  (q/ellipse x y 48 48))

(defn update-state [{:keys [x y xspeed yspeed]}]
  (let [newx (+ x xspeed)
        newy (+ y yspeed)]
  {:x newx
   :y newy
   :xspeed (if (or (> newx (q/width)) (< newx 0)) (* (- 1)  xspeed) xspeed)
   :yspeed (if (or (> newy (q/height)) (< newy 0)) (* (- 1) yspeed) yspeed)}))

(defn screen 
  ([& {:keys [title update-state-fn draw-state-fn setup-fn]
       :or {title "Bouncing without vectors"
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
  "I don't do a whole lot ... yet."
  [& args]
  (screen))
