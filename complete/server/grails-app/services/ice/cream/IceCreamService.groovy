package ice.cream

import grails.gorm.transactions.Transactional
import org.springframework.validation.ObjectError

@Transactional
class IceCreamService {

    UserIceCreamService userIceCreamService

    /**
     * @return null if an error occurs while saving the ice cream or the association between icream and user
     */
    IceCream addIceCreamToUser(User user, String iceCreamFlavor, boolean flush = false) {
        IceCream iceCream = IceCream.where { flavor == iceCreamFlavor }.get() ?: new IceCream(flavor: iceCreamFlavor)

        if(!iceCream.save(flush: flush)) {
            iceCream.errors.allErrors.each { ObjectError error ->
                println(error.toString())
            }
            return null
        }
        UserIceCream userIceCream = userIceCreamService.create(user, iceCream, flush)
        if ( userIceCream.hasErrors() ) {
            return null
        }

        iceCream
    }

    Boolean removeIceCreamFromUser(User userEntity, Long id, boolean flush = false) {
        IceCream iceCreamEntity = IceCream.load(id)
        UserIceCream.where { user == userEntity && iceCream == iceCreamEntity }.get()?.delete(flush: flush)
        true
    }
}