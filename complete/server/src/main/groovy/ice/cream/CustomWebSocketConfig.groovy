package ice.cream

import grails.plugin.springwebsocket.DefaultWebSocketConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry

@Configuration
@EnableWebSocketMessageBroker
class CustomWebSocketConfig extends DefaultWebSocketConfig {

    @Value('${allowedOrigin}')
    String allowedOrigin //<1>

    @Override
    void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) { //<2>
        println 'registerStompEndpoints with allowedOrigin: {}', allowedOrigin
        stompEndpointRegistry.addEndpoint("/stomp").setAllowedOrigins(allowedOrigin).withSockJS()
    }
}