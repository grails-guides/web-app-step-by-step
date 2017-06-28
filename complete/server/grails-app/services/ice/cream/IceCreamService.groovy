package ice.cream

import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.validation.ObjectError

@Slf4j
@CompileStatic
@Transactional
class IceCreamService {

    UserIceCreamService userIceCreamService

    /**
     * @return null if an error occurs while saving the ice cream or the association between icream and user
     */
    @GrailsCompileStatic
    IceCream addIceCreamToUser(User user, String iceCreamFlavor, boolean flush = false) {
        IceCream iceCream = IceCream.where { flavor == iceCreamFlavor }.get() ?: new IceCream(flavor: iceCreamFlavor)

        if(!iceCream.save(flush: flush)) {
            iceCream.errors.allErrors.each { ObjectError error ->
                log.error(error.toString())
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