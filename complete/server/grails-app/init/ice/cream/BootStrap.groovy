package ice.cream

import groovy.util.logging.Slf4j

@Slf4j
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

        if(!Role.list()) {
            new Role(authority: 'ROLE_USER').save(flush: true)
            log.info "Inserted role..."

        }
    }
    def destroy = {
    }
}
