import ice.cream.CustomWebSocketConfig
import ice.cream.SessionExpirationJobHolder
import ice.cream.TokenCreationEventListener
import ice.cream.UserPasswordEncoderListener

// Place your Spring DSL code here
beans = {
    userPasswordEncoderListener(UserPasswordEncoderListener, ref('hibernateDatastore')) //Added by Spring Security Core plugin
    webSocketConfig(CustomWebSocketConfig)                 //Custom settings for websockets
    tokenCreationEventListener(TokenCreationEventListener) //Listens for new access tokens and sets the loginDate
    sessionExpirationJobHolder(SessionExpirationJobHolder) //Checks for expired sessions

}
