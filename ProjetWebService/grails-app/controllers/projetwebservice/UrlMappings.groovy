package projetwebservice

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }
//http://localhost:8080/advert/show/50/json
        //controller:advert
        //action:show
        //id: 50 adverts
        //format:json

        "/"(view: "/index")
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
