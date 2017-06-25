package ice.cream


import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(LoginInterceptor)
class LoginInterceptorSpec extends Specification {

    def setup() {
    }

    def cleanup() {

    }

    void "Test login interceptor matching"() {
        when:"A request matches the interceptor"
            withRequest(controller:"login")

        then:"The interceptor does match"
            interceptor.doesMatch()
    }
}
