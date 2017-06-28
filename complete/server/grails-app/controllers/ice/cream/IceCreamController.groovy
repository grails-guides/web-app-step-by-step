package ice.cream

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import groovy.transform.CompileStatic

@CompileStatic
@Secured(['ROLE_USER']) //<1>
class IceCreamController extends RestfulController {

    IceCreamService iceCreamService
    UserIceCreamService userIceCreamService
    SpringSecurityService springSecurityService

    static responseFormats = ['json']

    IceCreamController() {
        super(IceCream)
    }

    def index(Integer max) {
        User user = springSecurityService.loadCurrentUser() as User
        if ( !user ) {
            render status: 404
            return
        }
        params.max = Math.min(max ?: 10, 100) //<2>

        List<IceCream> iceCreams = userIceCreamService.findAllIceCreamsByUser(user)
        respond iceCreams //<3>
    }

    def save(String flavor) {
        User user = springSecurityService.loadCurrentUser() as User
        if ( !user ) {
            render status: 404
            return
        }
        def id = iceCreamService.addIceCreamToUser(user, flavor)?.id
        render id ?: [status: 500]
    }

    def delete(Long id) {
        User user = springSecurityService.loadCurrentUser() as User
        if ( !user ) {
            render status: 404
            return
        }
        respond iceCreamService.removeIceCreamFromUser(user, id) ?: [status: 500]
    }
}