(defproject living-colors "0.1.0-SNAPSHOT"
  :plugins [[lein-ring "0.9.1"]]
  :ring {:handler living-colors.core/health-check}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [ring/ring-core "1.3.2"]
                 [ring/ring-jetty-adapter "1.3.2"]])
