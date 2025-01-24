(ns vector.vector 
  (:gen-class)
  (:require [clojure.core.matrix :as m]))


(defprotocol IVectorsa
  (add ^Vector [^Vector this ^Vector that])
  ;; (set [x])
  ;; (set [x y])
  ;; (set [x y z])
  ;; (rem [x])
  ;; (rem [x y])
  ;; (rem [x y z]) 
  ;; (sub [x])
  ;; (sub [x y])
  ;; (sub [x y z])
  ;; (mult [x])
  ;; (mult [x y])
  ;; (mult [x y z])
  ;; (div [x])
  ;; (div [x y])
  ;; (div [x y z])
  ;; (mag [this]) 
  ;; (mag-sq [this])
  ;; (dot [this v])
  ;; (cross [this v])
  ;; (dist [this v])
  ;; (normalize [this v])
  ;; (limit [this, limit])
  ;; (set-mag [this, mag])
  ;; (heading [this])
  ;; (set-heading [this, angle])
  ;; (rotate [this, amt])
  ;; (angle-between [this v])
  ;; (lerp [this v amt])
  ;; (slerp [this v amt])
  ;; (refect [this amt])
  (get-array [this])
  ;; (equals [this x])
  ;; (equals [this x y])
  ;; (equals [this x y z])
  ;; (from-angle [a])
  ;; (from-angles [a b])
  ;; (random-2d)
  ;; (random-3d)
  ;; (clamp-to-zero [this])
)
 
(defn vector-add [a b]
  (m/add a b)) 

(deftype Vector [^:volatile-mutable va]
  IVectorsa
  (add [this that] (set! va (vector-add va (.-va ^Vector that))) this)
  (get-array [this] (m/clone va)))

(defn ^Vector make-vector [x y z]
  (Vector. (m/array [x y z]))) 
