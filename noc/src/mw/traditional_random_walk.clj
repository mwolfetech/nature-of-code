(ns mw.traditional-random-walk
  (:gen-class)
  (:require [quil.core :as q]
           [quil.middleware :as m]))

(defn walker [x y]
  {:x x :y y })

(defn rand-step [] 
  (q/random -1 1))

(defn setup []
  (q/background 255)
  (q/frame-rate 120)
  (q/stroke 0)
  (walker (/ (q/width) 2)
          (/ (q/height) 2)))

(defn draw-state [{:keys [x y]}]
  (q/point x y))

(defn update-state [rand-fn {:keys [x y]}]
  (walker (+ x (rand-fn)) (+ y (rand-fn))))

(defn screen 
  ([& {:keys [title update-state-fn draw-state-fn setup-fn]
       :or {title "Sketch"
            update-state-fn (partial update-state rand-step)
            setup-fn setup
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
  [& args]
  (screen))

(comment
  ;; Exercise 0.1
    (screen :title "Exercise 0.1 Down and to the right"
            :update-state-fn (partial update-state  
                                      (fn [] (condp <= (q/random 1)  
                                               0.55 (q/random -1 0)
                                               0 (q/random 1)))))


  ;; Exercise 0.3 
  (let [step (fn [cur mouse] (condp <= (q/random 1)  
                      0.50 (if (> cur mouse) (q/random -1 0) (q/random 1))
                      0 (if (> cur mouse) (q/random 1) (q/random -1 0))))]

    (screen :title "Exercise 0.3 Towards Mouse"
            :update-state-fn (fn [{:keys [x y]}]
                               (let [xstep (step x (q/mouse-x))
                                     ystep (step y (q/mouse-y))]
                                 (walker (+ x xstep) (+ y ystep))))))


  ;; Exercise 0.6
   (screen :title "Exercise 0.6 Some Big Steps"
           :update-state-fn (partial update-state 
                                  (fn [] 
                                    (loop []
                                      (let [r1 (q/random -1 1) 
                                            r2 (q/random 0 1)]
                                      (if (> (* r1 r1) r2)
                                        (* r1  5)
                                        (recur)))))))


)


