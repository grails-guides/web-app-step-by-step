package ice.cream

class UrlMappings {

    static mappings = {

        delete "/$controller/$username/$id"(controller: 'iceCream', action: 'delete')
        get    "/$controller/$username/"(controller: 'iceCream', action: 'index')
        post   "/$controller/$username"(controller: 'iceCream', action: 'save')
        put    "/$controller/$id"(controller: 'iceCream', action: 'update')
        post   "/signup"(controller: 'user', action: 'signup')
        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')

    }
}
