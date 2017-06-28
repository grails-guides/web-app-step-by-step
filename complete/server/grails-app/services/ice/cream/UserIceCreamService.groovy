package ice.cream

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.validation.ObjectError

@Slf4j
@Transactional
@CompileStatic
class UserIceCreamService {

    UserIceCream create(User user, IceCream iceCream, boolean flush = false) { //<1>
        UserIceCream instance = new UserIceCream(user: user, iceCream: iceCream)
        if ( !instance.save(flush: flush) ) { //<2>
            instance.errors.allErrors.each { ObjectError error ->
                log.error(error.toString())
            }
        }
        instance
    }

    @ReadOnly
    List<IceCream> findAllIceCreamsByUser(User loggedUser) {
        UserIceCream.where {
            user == loggedUser
        }.list()*.iceCream as List<IceCream>
    }
}