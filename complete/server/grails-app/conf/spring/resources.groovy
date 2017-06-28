import ice.cream.CustomWebSocketConfig
import ice.cream.SessionExpirationJobHolder
import ice.cream.TokenCreationEventListener
import ice.cream.UserPasswordEncoderListener

// Place your Spring DSL code here
beans = {
    userPasswordEncoderListener(UserPasswordEncoderListener) //<1>
    webSocketConfig(CustomWebSocketConfig)                 //<2>
    tokenCreationEventListener(TokenCreationEventListener) //<3>
    sessionExpirationJobHolder(SessionExpirationJobHolder) //<4>

}
