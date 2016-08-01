(ns invite.repl
  (:use invite.handler
        ring.server.standalone
        [ring.middleware file-info file]))

(defonce server (atom nil))

(defn get-handler []
  (-> #'app
    (wrap-file "resources")
    (wrap-file-info)))

(defn start-server
  "Start srver in developer mode from REPL"
  [& [port]]
  (let [port (if port (Integer/parseInt port) 8080)]
    (reset! server
            (serve (get-handler)
                   {:port port
                    :init init
                    :auto-reload? true
                    :destroy destroy
                    :join true}))
    (println (str "the application cann be accessed at http://localhost:" port))))

(defn stop-server []
  (.stop @server)
  (reset! server nil))
