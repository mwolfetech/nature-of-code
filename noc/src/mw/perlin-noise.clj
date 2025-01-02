(ns mw.perlin-noise
  (:gen-class)
  (:require [quil.core :as q]
           [quil.middleware :as m]))

(defn walker [x y tx ty]
  {:x x :y y :tx tx :ty ty})

(defn setup [rand-fn]
  (q/background 255)
  (q/frame-rate 30)
  (q/stroke 0)
  (walker (map-step (rand-fn 0) (q/width))
          (map-step (rand-fn 10000) (q/height))
          0 
          10000))

(defn map-step 
  ([r sz]
   (map-step r 0 sz))
  ([r s1 s2 ]
   (q/map-range r 0.0 1.0 s1 s2)))

(defn draw-state [{:keys [x y]}]
  (q/ellipse x y 10 10))

(defn update-state [rand-fn {:keys [x y tx ty]}]
  (walker  (map-step (rand-fn tx) (q/width))  
           (map-step (rand-fn ty) (q/height))
          (+ tx 0.01)
          (+ ty 0.01)))

(defn screen 
  ([& {:keys [title update-state-fn draw-state-fn setup-fn]
       :or {title "Sketch"
            update-state-fn (partial update-state q/noise)
            setup-fn (partial setup q/noise)
            draw-state-fn draw-state}}]
   (q/sketch
    :title title 
    :size [483 300]
    :setup setup-fn
    :update update-state-fn
    :draw draw-state-fn
    :features [:keep-on-top]
    :middleware [m/fun-mode])))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (screen))



(comment 
   ;; Exercise 0.7 Perlin Steps
    (screen :title "0.7 Perlin Steps" :update-state (fn [_ {:keys [x y tx ty]}]
                           (walker (+ x (map-step (q/noise tx) -1 1))
                                   (+ y (map-step (q/noise ty) -1 1))
                                   (+ tx 0.01)
                                   (+ ty 0.01))))
                                  
