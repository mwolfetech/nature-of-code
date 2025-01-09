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
  #_(q/rotate-x (/ Math/PI  3));
  (doall (->> (loop [y 0
                     x 0
                     res []
                     sub []]
                (if (and (= y rows) (= x cols)) 
                  (vec res)
                  (if (= x cols) 
                    (recur (inc y) 0 (vec (concat res [sub])) [])
                    (recur y (inc x) res (vec (concat sub [[[x y] [x (inc y)]]]))))))
              (map (fn [strip]
                     (println "STRIP:" strip)
                     (q/begin-shape :triangle-strip)
                     (doall (map (fn [[[x y][x2 y2]]] 
                       (q/vertex (* x scale) (* y scale))
                       (q/vertex (* x2 scale) (* y2 scale))) strip))
                   (q/end-shape)))
              ))
         ))



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


