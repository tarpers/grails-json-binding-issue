package json.binding.issue

import org.codehaus.groovy.grails.web.metaclass.BindDynamicMethod

class TestService {

    def create(params) {
        def a = new A(params)
        a.save()
        a
    }

    def createUsingBindDynamic(params) {
        def a = new A()
        def args = [a, params]
        def bind = new BindDynamicMethod()
        bind.invoke(a, 'bind', args as Object[])
        a.save()
        a
    }
}
