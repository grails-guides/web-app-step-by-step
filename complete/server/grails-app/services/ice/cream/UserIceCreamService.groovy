package ice.cream

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional
import org.springframework.validation.ObjectError

@Transactional
class UserIceCreamService {

    UserIceCream create(User user, IceCream iceCream, boolean flush = false) { //<1>
        UserIceCream instance = new UserIceCream(user: user, iceCream: iceCream)
        if ( !instance.save(flush: flush) ) { //<2>
            instance.errors.allErrors.each { ObjectError error ->
                println(error.toString())
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