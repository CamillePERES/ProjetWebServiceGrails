package projetapigrails

class UrlMappings {

    static mappings = {
        delete "/api/$controller/$id(.$format)?"(action:"delete")
        get "/api/$controller(.$format)?"(action:"index")
        get "/api/$controller/$id(.$format)?"(action:"show")
        post "/api/$controller(.$format)?"(action:"save")
        put "/api/$controller/$id(.$format)?"(action:"update")
        patch "/api/$controller/$id(.$format)?"(action:"patch")

        //URL custom
        put "/api/user/updateUserPassword" (controller:'User',action:"updateUserPassword")
        get "/api/user/showUserLogged" (controller:'User',action:"showUserLogged")

        //URL error
        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
