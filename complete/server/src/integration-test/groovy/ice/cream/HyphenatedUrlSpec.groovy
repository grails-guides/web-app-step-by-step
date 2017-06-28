package ice.cream

import grails.gorm.transactions.Rollback
import grails.plugins.rest.client.RestBuilder
import grails.testing.mixin.integration.Integration
import groovy.json.JsonOutput
import spock.lang.Specification

@Integration
class HyphenatedUrlSpec extends Specification {

    @Rollback
    def "test the default camel case convention is replaced by a hyphenated implementation"() {
        given:
        RestBuilder rest = new RestBuilder()

        expect:
        User.findByUsername('sherlock')

        when:
        String jsonPayload = JsonOutput.toJson([username: 'sherlock', password: 'secret'])
        def resp = rest.post("http://localhost:${serverPort}/api/login") {
            accept('application/json')
            contentType('application/json')
            json jsonPayload
        }

        then:
        resp.status == 200
        def accessToken = resp.json.access_token

        when:
        resp = rest.get("http://localhost:${serverPort}/ice-cream/index") {
            header('Authorization', "Bearer ${accessToken}")
        }

        then:
        resp.status == 200

        when:
        resp = rest.get("http://localhost:${serverPort}/iceCream/index") {
            header('Authorization', "Bearer ${accessToken}")
        }

        then:
        resp.status == 200
    }
}
