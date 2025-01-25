(ns vector.vector 
  (:gen-class)
  (:require [clojure.core.matrix :as m]))


(defprotocol IVector
  (add ^Vector [^Vector this ^Vector that])
  ;;(set [this x] [this x y] [this x y z])
  ;; (rem [x] [x y] [x y z]) 
  ;; (sub [x] [x y] [x y z])
  ;; (mult [x] [x y] [x y z])
  ;; (div [x] [x y] [x y z])
  ;; (mag [this]) 
  ;; (mag-sq [this]) 
  ;; (dot [this v])
  ;; (cross [this v])
  ;; (dist [this v])
  ;; (normalize [this v])
  ;; (limit [this, limit])
  ;; (set-mag [this, mag])
  ;; (heading [this])
  ;; (set-heading [this angle])
  ;; (rotate [this, amt])
  ;; (angle-between [this v])
  ;; (lerp [this v amt])
  ;; (slerp [this v amt])
  ;; (refect [this amt])
  (array [this])
  ;; (equals [this x] [this x y] [this x y z])
  ;; (from-angle [a])
  ;; (from-angles [a b])
  ;; (random-2d)
  ;; (random-3d)
  ;; (clamp-to-zero [this])
)
 
(deftype Vector [^:volatile-mutable va]
  IVector
  (add [this that] (m/add! va (.-va ^Vector that)) this)
  (array [this] (double-array (m/clone va))))

(defn ^Vector make-vector [x y z]
  (m/set-current-implementation :vectorz)
  (Vector. (m/array [x y z]))) 
