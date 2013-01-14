package json.binding.issue

import grails.plugin.spock.IntegrationSpec
import org.codehaus.groovy.grails.web.json.JSONObject

class TestServiceSpec extends IntegrationSpec {

    def testService

    def b1, b2, b3, c1, c2, cc1, cc2, cc3

    def 'Create an A object with multiple Bs using JSON'() {

        given: 'some test data'
            buildTestData()

        and: 'a JSON object to bind'
            def json = new JSONObject("""
                {
                    "name": "A1",
                    "bs[0].id": "$b1.id",
                    "bs[1].id": "$b2.id"
                }
            """)

        when:
            def a = testService.create(json)

        then:
            a.name == 'A1'
            a.bs.find { it.id == b1.id }
            a.bs.find { it.id == b2.id }
    }

    def 'Create an A object with multiple Cs using JSON'() {

        given: 'some test data'
            buildTestData()

        and: 'a JSON object to bind'
            def json = new JSONObject("""
                {
                    "name": "A1",
                    "cs[0].id": "$c1.id",
                    "cs[1].id": "$c2.id"
                }
            """)

        when:
            def a = testService.create(json)

        then:
            a.name == 'A1'
            a.cs.find { it.id == c1.id }
            a.cs.find { it.id == c2.id }
    }

    def 'Create an A object with multiple CCs using JSON'() {

        given: 'some test data'
            buildTestData()

        and: 'a JSON object to bind'
            def json = new JSONObject("""
                {
                    "name": "A1",
                    "ccs[0].id": "$cc1.id",
                    "ccs[1].id": "$cc2.id"
                }
            """)

        when:
            def a = testService.create(json)

        then:
            a.name == 'A1'
            a.ccs.find { it.id == cc1.id }
            a.ccs.find { it.id == cc2.id }
    }

    def 'Create an A object with multiple CCs using BindDynamicMethod'() {

        given: 'some test data'
            buildTestData()

        and: 'a JSON object to bind'
            def json = new JSONObject("""
                {
                    "name": "A1",
                    "ccs[0].id": "$cc1.id",
                    "ccs[1].id": "$cc2.id"
                }
            """)

        when:
            def a = testService.createUsingBindDynamic(json)

        then:
            a.name == 'A1'
            a.ccs.find { it.id == cc1.id }
            a.ccs.find { it.id == cc2.id }
    }

    def 'Create an A object with with more Bs than CCs using JSON'() {

        given: 'some test data'
            buildTestData()

        and: 'a JSON object to bind'
            def json = new JSONObject("""
                {
                    "name": "A1",
                    "bs[0].id": "$b1.id",
                    "bs[1].id": "$b2.id",
                    "bs[2].id": "$b3.id",
                    "ccs[0].id": "$cc1.id",
                    "ccs[1].id": "$cc2.id"
                }
            """)

        when:
            def a = testService.create(json)

        then:
            a.name == 'A1'

            a.bs.find { it.id == b1.id }
            a.bs.find { it.id == b2.id }
            a.bs.find { it.id == b3.id }

            a.ccs.find { it.id == cc1.id }
            a.ccs.find { it.id == cc2.id }
    }

    def 'Create an A object with with more CCs than Bs using JSON'() {

        given: 'some test data'
            buildTestData()

        and: 'a JSON object to bind'
            def json = new JSONObject("""
                {
                    "name": "A1",
                    "bs[0].id": "$b1.id",
                    "bs[1].id": "$b2.id",
                    "ccs[0].id": "$cc1.id",
                    "ccs[1].id": "$cc2.id",
                    "ccs[2].id": "$cc3.id"
                }
            """)

        when:
            def a = testService.create(json)

        then:
            a.name == 'A1'

            a.bs.find { it.id == b1.id }
            a.bs.find { it.id == b2.id }

            a.ccs.find { it.id == cc1.id }
            a.ccs.find { it.id == cc2.id }
            a.ccs.find { it.id == cc3.id }
    }

    def 'Create an A object with with more CCs than Bs using BindDynamicMethod'() {

        given: 'some test data'
            buildTestData()

        and: 'a JSON object to bind'
            def json = new JSONObject("""
                {
                    "name": "A1",
                    "bs[0].id": "$b1.id",
                    "bs[1].id": "$b2.id",
                    "ccs[0].id": "$cc1.id",
                    "ccs[1].id": "$cc2.id",
                    "ccs[2].id": "$cc3.id"
                }
            """)

        when:
            def a = testService.createUsingBindDynamic(json)

        then:
            a.name == 'A1'

            a.bs.find { it.id == b1.id }
            a.bs.find { it.id == b2.id }

            a.ccs.find { it.id == cc1.id }
            a.ccs.find { it.id == cc2.id }
            a.ccs.find { it.id == cc3.id }
    }

    def buildTestData() {
        b1 = new B(name: 'B1').save(failOnError: true)
        b2 = new B(name: 'B2').save(failOnError: true)
        b3 = new B(name: 'B3').save(failOnError: true)

        c1 = new B(name: 'C1').save(failOnError: true)
        c2 = new B(name: 'C2').save(failOnError: true)

        cc1 = new CC(name: 'CC1', altName: 'Cee Cee One').save(failOnError: true)
        cc2 = new CC(name: 'CC2', altName: 'Cee Cee Two').save(failOnError: true)
        cc3 = new CC(name: 'CC3', altName: 'Cee Cee Three').save(failOnError: true)
    }
}
