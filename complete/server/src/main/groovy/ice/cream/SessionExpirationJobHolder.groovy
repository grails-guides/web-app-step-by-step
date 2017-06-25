package ice.cream

import groovy.time.TimeCategory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.Scheduled

class SessionExpirationJobHolder {

    @Autowired
    SimpMessagingTemplate brokerMessagingTemplate //This class is provided by the spring-websocket plugin and allows us to push an event over a websocket channel

    @Value('${timeout.minutes}') //Loads our timeout.minutes property from application.yml
    Integer timeout

    @Scheduled(cron = "0 * * * * *") //Run every minute
    void findExpiredSessions() {
        Date timeoutDate
        use( TimeCategory ) { //Use Groovy's DSL for time operations
            timeoutDate = new Date() - timeout.minutes
        }

        User.withNewSession {
            List<User> expiredUsers = User.where { //Query for loggedIn users with a lastLogin date after the timeout limit
                lastLogin != null
                lastLogin < timeoutDate
            }.list()

            //Iterate over the expired users
            expiredUsers.each { user ->
                user.lastLogin = null //Reset lastLogin date
                user.save(flush: true)

                //Send a websocket message to a user-specific "channel" for each expired user - we're using their username as the unique key for each channel
                brokerMessagingTemplate.convertAndSend "/topic/${user.username}".toString(), "logout"
            }
        }
    }
}