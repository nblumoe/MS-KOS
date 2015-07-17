(defproject living-colors "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [ring/ring "1.4.0"]
                 [com.soundcloud/prometheus-clj "1.0.6"]
                 [compojure "1.4.0"]]
  :ring {:handler living-colors.core/app}
  :main living-colors.core
  :aot [living-colors.core])
