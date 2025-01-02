(ns mw.a-random-number-distribution
  (:gen-class)
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def total-counts 20)

(defn setup []
  (q/background 255)
  (q/frame-rate 60)
  (q/stroke 0)
  (q/fill 127)
  (let [counts (vec (take total-counts (repeat 0)))
        width (/ (q/width) total-counts)]
    {:counts counts 
     :width width}))

(defn make-rect [i x w h ]
  (q/rect (* i w)
          (- h x)
          (dec w)
          x))

(defn draw-state [{:keys [counts width]}]
  (doall (map-indexed (fn [i x] (make-rect i x (/ (q/width) total-counts) (q/height))) counts)))


(defn update-state [rand-fn {:keys [counts width] :as state} ]
  (let [index (rand-fn total-counts)]
    {:counts (update-in counts [index] inc) 
     :width width}))

(defn screen 
  ([& {:keys [title update-state-fn draw-state-fn setup-fn]
       :or {title "Sketch"
            update-state-fn (partial update-state rand-int)
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

(defn -main [& args]
  (screen))



(comment
   ;; Example 0.5 An Accept-Reject Distribution
   (screen :title "An Accept-Reject Distribution"
           :update-state-fn (partial update-state 
                                  (fn [total-counts] 
                                    (loop []
                                      (let [r1 (rand-int total-counts) 
                                            r2 (rand-int total-counts)]
                                      (if (> r1 r2)
                                        r1
                                        (recur)))))))
)
         

  
