package ice.cream

import org.springframework.context.ApplicationListener
import grails.plugin.springsecurity.rest.RestTokenCreationEvent

class TokenCreationEventListener implements ApplicationListener<RestTokenCreationEvent> {

    void onApplicationEvent(RestTokenCreationEvent event) {

        User.withNewSession {
            User user = User.where { username == event.principal.username }.first()
            user.lastLogin = new Date()
            user.save(flush: true)
        }
    }
}