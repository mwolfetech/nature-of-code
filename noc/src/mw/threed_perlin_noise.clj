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
  (q/translate 0 50)
  (q/rotate-x (/ Math/PI  3));
  (doall (->> (loop [y 0
                     x 0
                     res []
                     sub []]
                (if (and (> y rows) (> x cols)) 
                  (vec res)
                  (if (> x cols) 
                    (recur (inc y) 0 (vec (concat res [sub])) [])
                    (recur y (inc x) res (vec (concat sub [[[x y (q/map-range (q/noise (* x 0.2)  (* y 0.2)) 0 1 -50 50)] 
                                                            [x (inc y) (q/map-range (q/noise (* x 0.2) (* (inc y) 0.2)) 0 1 -50 50)]]]))))))
              (map (fn [strip]
                     (q/begin-shape :triangle-strip)
                     (doall (map (fn [[[x y z][x2 y2 z2]]] 
                       (q/vertex (* x scale) (* y scale) z)
                       (q/vertex (* x2 scale) (* y2 scale) z2)) strip))
                   (q/end-shape)))))



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


