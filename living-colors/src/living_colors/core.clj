(ns living-colors.core)

(defn health-check [request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body  "OK"})
