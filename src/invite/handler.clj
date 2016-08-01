(ns invite.handler
  (:use cooljure.core)
  (:require 
    [clojure.string         :as str])
  (:require 
    [compojure.core :refer [defroutes routes]]
    [ring.middleware.resource :refer [wrap-resource]]
    [ring.middleware.file-info :refer [wrap-file-info]]
    [hiccup.middleware :refer [wrap-base-url]]
    [compojure.handler :as handler]
    [compojure.route :as route]
    [invite.routes.home :refer [home-routes]]))
  
(defn nubank []
  (do
  (println " _   _       _                 _    ")
  (println "| \\ | |     | |               | |   ")
  (println "|  \\| |_   _| |__   __ _ _ __ | | __")
  (println "| . ` | | | | '_ \\ / _` | '_ \\| |/ /")
  (println "| |\\  | |_| | |_) | (_| | | | |   < ")
  (println "|_| \\_|\\__,_|_.__/ \\__,_|_| |_|_|\\_\\")
  (println "                                     ")))

(defn init []
  (println "The application is starting. Wait a moment please")
  (nubank)
  (invite.routes.home/load))

(defn destroy []
  (println "The application is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (routes home-routes app-routes)
      (handler/site)
      (wrap-base-url)))

