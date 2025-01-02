(ns mw.noise-colored-pixels
  (:gen-class)
  (:require [quil.core :as q]
           [quil.middleware :as m]))


(defn setup []
  (q/background 255)
  (q/stroke 0)
  (q/no-loop))

(defn draw-state [state]
    (q/background 255)
    (let [px (q/pixels)]
    (dotimes [nn  (- (* (q/width)(q/height)) (q/width))]
      (let [bright (rand-int 255)
            color (q/color bright bright bright 255)]
          (aset-int px nn color))))
  (q/update-pixels)
state)
    
(defn update-state [state]
  state)

(defn screen 
  ([& {:keys [title update-state-fn draw-state-fn setup-fn]
       :or {title "Sketch"
            update-state-fn update-state
            setup-fn setup
            draw-state-fn draw-state}}]
   (q/sketch
    :title title 
    :size [400 400]
    :setup setup-fn
    :renderer :p2d
    ;;:update update-state-fn
    :draw draw-state-fn
    :features [:keep-on-top]
    :middleware [m/fun-mode])))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (screen))


