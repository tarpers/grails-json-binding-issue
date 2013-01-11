package json.binding.issue

class AService {

    def create(params) {
        def a = new A(params)
        a.save()
        a
    }
}
