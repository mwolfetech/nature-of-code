(ns mw.threed-perlin-noise
  (:gen-class)
  (:require [quil.core :as q]
            [quil.middleware :as m]))


(defn setup []
  (q/background 255)
  (q/stroke 1)
  (q/no-loop)
  (let [scale 20]
    {:scale scale :rows (/ (q/height) scale) :cols (/ (q/width) scale)}))

  (defn draw-state [{:keys [scale rows cols]}]
    (q/background 255)
    ;;(q/translate 0 50)
    ;;(q/rotate-x (/ Math/PI  3));
    (q/begin-shape :triangle-strip)
    (->> (for [y (range 0 cols) 
               x (range 0 rows)]
           [ x
             y 
            (q/map-range (q/noise (* x 0.1)) 0 1 0 50)])
         
          (map (fn [[x y z]] 
                       (q/vertex (* x scale) (* y scale)) 
                       (q/vertex (* x scale) (* (inc y) scale))))
          (doall))
    (q/end-shape)
    {:scale scale :rows rows :cols cols})


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
      :renderer :p3d
      :update update-state-fn
      :draw draw-state-fn
      :features [:keep-on-top]
      :middleware [m/fun-mode])))

  (defn -main
    "I don't do a whole lot ... yet."
    [& args]
    (screen))


