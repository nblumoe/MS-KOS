(ns living-colors.core
  (:gen-class)
  (:require [ring.adapter.jetty :refer :all]))

(defn health-check [request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body  "OK"})

(defn -main [& args]
  (run-jetty health-check {:port 3000}))
