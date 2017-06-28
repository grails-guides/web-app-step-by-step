package ice.cream

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@Transactional
class UserIceCreamService {

    UserIceCream create(User user, IceCream iceCream, boolean flush = false) { //<1>
        UserIceCream instance = new UserIceCream(user: user, iceCream: iceCream)
        if ( !instance.save(flush: flush) ) { //<2>
            instance.errors.allErrors.each {  error ->
                println error
            }
        }
        instance
    }

    @ReadOnly //<2>
    List<IceCream> findAllIceCreamsByUser(User loggedUser) { //<3>
        UserIceCream.where {
            user == loggedUser
        }.list()*.iceCream //<4>
    }
}