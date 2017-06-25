package ice.cream

class UrlMappings {

    static mappings = {

        delete "/ice-cream/$username/$id"(controller: 'iceCream', action: 'delete')
        get    "/ice-cream/$username/"(controller: 'iceCream', action: 'index')
        post   "/ice-cream/$username"(controller: 'iceCream', action: 'save')
        put    "/ice-cream/$id"(controller: 'iceCream', action: 'update')
        post   "/signup"(controller: 'user', action: 'signup')
        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')

    }
}
