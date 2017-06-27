package ice.cream

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.*
import grails.gorm.transactions.Transactional

//<1>
@Secured(['ROLE_USER'])
class IceCreamController extends RestfulController {

    IceCreamService iceCreamService

    static responseFormats = ['json']
    IceCreamController() {
        super(IceCream)
    }

    def index(Integer max, String username) {
        params.max = Math.min(max ?: 10, 100) //<2>
        User _user = getUser(username)
        if(_user) {
            List<IceCream> iceCreams = UserIceCream.where { user == _user }
                    .list()
                    .collect { it.iceCream }
            respond iceCreams //<3>
        }
        else
            render status: 404 //<4>
    }

    @Transactional
    def save(String username, String flavor) {
        User user = getUser(username)
        if(user) {
            def id = iceCreamService.addIceCreamToUser(user, flavor).id
            render id ?: [status: 500]
        } else
            render status: 404
    }

    def delete(String username, Long id) {
        User user = getUser(username)
        if(user) {
            respond iceCreamService.removeIceCreamFromUser(user, id) ?: [status: 500]
        } else
            render status: 404
    }

    private User getUser(String username) {
        return User.findByUsername(username)
    }
}