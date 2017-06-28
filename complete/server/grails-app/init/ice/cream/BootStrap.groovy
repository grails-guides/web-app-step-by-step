package ice.cream

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

@Slf4j
@CompileStatic
class BootStrap {

    def init = { servletContext ->
        log.info "Loading database..."

        if (!IceCream.count()) {
            def iceCreams = ['vanilla', 'chocolate', 'strawberry'].collect { flavor ->
                new IceCream(flavor: flavor).save(flush: true)
            }

            def ids = iceCreams*.id
            log.info "Inserted records with ids ${ids.join(',')}"
        }

        if (!Role.count()) {
            def role = new Role(authority: 'ROLE_USER').save(flush: true)
            log.info "Inserted role..."

            User user = new User(username: 'sherlock', password: 'secret').save(flush: true)
            log.info "Inserted user..."

            UserRole.create(user, role, true)
            log.info "Associated user with role..."
        }
    }
    def destroy = {
    }
}
