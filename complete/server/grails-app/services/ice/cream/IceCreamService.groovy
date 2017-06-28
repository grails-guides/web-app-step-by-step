package ice.cream

import grails.gorm.transactions.Transactional

@Transactional
class IceCreamService {

    UserIceCreamService userIceCreamService

    /**
     * @return null if an error occurs while saving the ice cream or the association between ice cream and user
     */
    IceCream addIceCreamToUser(User user, String iceCreamFlavor, boolean flush = false) {
        IceCream iceCream = IceCream.find { flavor == iceCreamFlavor } //<1>

        if(!iceCream) {
            iceCream = new IceCream(flavor: iceCreamFlavor)

            if(!iceCream.save(flush: flush)) {
                iceCream.errors.allErrors.each { error -> println error } //<2>
                return null
            }
        }

        UserIceCream userIceCream = userIceCreamService.create(user, iceCream, flush) //<3>

        if ( userIceCream.hasErrors() ) {
            return null
        }

        iceCream
    }

    Boolean removeIceCreamFromUser(User userEntity, Long id, boolean flush = false) {
        IceCream iceCreamEntity = IceCream.load(id) //<4>
        UserIceCream.find { user == userEntity && iceCream == iceCreamEntity }?.delete(flush: flush)
        true
    }
}