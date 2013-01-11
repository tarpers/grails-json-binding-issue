package json.binding.issue

class TestService {

    def create(params) {
        def a = new A(params)
        a.save()
        a
    }
}
