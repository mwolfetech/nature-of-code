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
    (doall
     (->> (for [x (range 0 (q/width))
                y (range 0 (q/height))] [x y (* x 0.1) (* y 0.1)])
          (map-indexed (fn [idx [x y xoff yoff]] 
                         (let [bright (q/map-range (q/noise xoff yoff) 0 1 0 255)
                               color (q/color bright bright bright 255)]
                           (aset-int px idx color)))))))
  (q/update-pixels))


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
  [& args]
  (screen))


