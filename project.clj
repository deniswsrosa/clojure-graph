(defproject demo "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [
    [org.clojure/clojure            "1.8.0"]
    [cooljure                       "0.1.29"]
    [prismatic/schema               "1.1.3"]
    [prismatic/hiphip               "0.2.0"]
    [org.clojure/tools.namespace    "0.2.11"]
    [compojure                      "1.5.1"]
    [hiccup                         "1.0.5"]
    [ring-server                    "0.4.0"]
    [org.clojure/data.json          "0.2.6"]
    [metosin/ring-swagger-ui "2.1.1-M1"]
  ]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler   invite.handler/app
         :init      invite.handler/init
         :destroy   invite.handler/destroy}
  :profiles
  { :uberjar        { :aot :all }
    :production     { :ring {:open-browser? false, :stacktraces? false, :auto-reload? false} }
    :dev            { :dependencies [ [ring-mock "0.1.5"]
                                      [ring/ring-devel "1.5.0"] ]
                    }
  }
  :main ^:skip-aot invite.core
  :target-path "target/%s"
  :jvm-opts ^:replace []
)
