(ns mw.a-gaussian-distribution
  (:gen-class)
  (:require [quil.core :as q]
           [quil.middleware :as m]))

(defn walker [x y]
  {:x x :y y })

(defn rand-step [sz] 
   
(+ (* (q/random-gaussian) 60) (/ sz 2) ))

(defn setup []
  (q/background 255)
  (q/frame-rate 30)
  (q/no-stroke)
  (q/fill 0 10)
  (walker (/ (q/width) 2)
          (/ (q/height) 2)))

(defn draw-state [{:keys [x y]}]
  (q/ellipse x y 16 16))

(defn update-state [rand-fn {:keys [x y]}]
  ;; let's do x and y for a bit more fun
  (walker (rand-fn (q/width)) (rand-fn (q/height))))

(defn screen 
  ([& {:keys [title update-state-fn draw-state-fn setup-fn]
       :or {title "Sketch"
            update-state-fn (partial update-state rand-step)
            setup-fn setup
            draw-state-fn draw-state}}]
   (q/sketch
    :title title 
    :size [600 300]
    :setup setup-fn
    :update update-state-fn
    :draw draw-state-fn
    :features [:keep-on-top]
    :middleware [m/fun-mode])))

(defn -main
  [& args]
  (screen))



