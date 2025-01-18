(ns vector.vector 
  (:gen-class)
  (:require [core.matrix :as m])


(defprotocol IVector
  (add [this v])
  (angle-between [this v])
  (array [this])
  (clamp-to-zero [this])
  (cross [this v])
  (dist [this v]))

