package ice.cream

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import grails.plugin.springsecurity.rest.token.AccessToken
import grails.plugin.springsecurity.rest.token.generation.TokenGenerator
import grails.plugin.springsecurity.rest.token.rendering.AccessTokenJsonRenderer
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails

//The @Secured annotation specifies the access controls for this controller - anonymous access is permitted
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

        //Check for duplicate username
        if (User.findByUsername(username)) {
            render status: HttpStatus.UNPROCESSABLE_ENTITY.value(), "duplicate key"
        } else {
            User user = userService.createUser(username, password)

            //Authenticate the newly created user, and generate the authentication token
            //This step saves the React app from having to make a second login request after the /signup request
            springSecurityService.reauthenticate(user.username)
            AccessToken token = tokenGenerator.generateAccessToken(springSecurityService.principal as UserDetails)
            render status: HttpStatus.OK.value(), accessTokenJsonRenderer.generateJson(token)

        }
    }
}
