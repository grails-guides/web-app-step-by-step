package ice.cream

import grails.gorm.transactions.Transactional

@Transactional
class IceCreamService {

    IceCream addIceCreamToUser(User user, String flavor) {
        IceCream iceCream = IceCream.findOrCreateByFlavor(flavor)

        if(iceCream.save(flush:true)) {
            UserIceCream.create(user, iceCream, true)
            return iceCream
        } else {
            iceCream.errors.allErrors.each { log.error(it) }
            return null
        }
    }

    Boolean removeIceCreamFromUser(User user, Long id) {
        IceCream iceCream = IceCream.get(id)
        if(iceCream) {
            UserIceCream.findByUserAndIceCream(user, iceCream)?.delete(flush: true)
            return true
        } else {
            return false
        }
    }
}