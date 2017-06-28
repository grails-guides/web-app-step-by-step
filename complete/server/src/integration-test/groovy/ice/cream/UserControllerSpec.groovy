package ice.cream

import grails.plugins.rest.client.RestBuilder
import grails.testing.mixin.integration.Integration
import groovy.json.JsonOutput
import spock.lang.Specification

@Integration
class UserControllerSpec extends Specification {

    def "user are able to signup"() {

        given:
        RestBuilder rest = new RestBuilder()

        when:
        String jsonPayload = JsonOutput.toJson([username: 'sdelamo', password: 'secret'])
        def resp = rest.post("http://localhost:${serverPort}/signup") {
            accept('application/json')
            contentType('application/json')
            json jsonPayload
        }

        then:
        resp.status == 200

        and: 'access token is returned'
        resp.json.access_token
    }
}
