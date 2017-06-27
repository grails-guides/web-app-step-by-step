package ice.cream

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import grails.plugin.springsecurity.rest.token.AccessToken
import grails.plugin.springsecurity.rest.token.generation.TokenGenerator
import grails.plugin.springsecurity.rest.token.rendering.AccessTokenJsonRenderer
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails

//<1>
@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
class UserController {
    static responseFormats = ['json', 'xml']

    UserService userService
    TokenGenerator tokenGenerator
    AccessTokenJsonRenderer accessTokenJsonRenderer
    SpringSecurityService springSecurityService

    def signup() {
        def json = request.JSON
        def username = json.username
        def password = json.password

        //<2>
        if (User.findByUsername(username)) {
            render status: HttpStatus.UNPROCESSABLE_ENTITY.value(), "duplicate key"
        } else {
            User user = userService.createUser(username, password)

            //<3>
            springSecurityService.reauthenticate(user.username)
            AccessToken token = tokenGenerator.generateAccessToken(springSecurityService.principal as UserDetails)
            render status: HttpStatus.OK.value(), accessTokenJsonRenderer.generateJson(token)

        }
    }
}
