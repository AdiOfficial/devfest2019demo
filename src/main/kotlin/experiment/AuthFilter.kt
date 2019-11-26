// package experiment

// import io.micronaut.http.HttpRequest
// import io.micronaut.http.HttpResponse
// import io.micronaut.http.MutableHttpResponse
// import io.micronaut.http.annotation.Filter
// import io.micronaut.http.filter.HttpServerFilter
// import io.micronaut.http.filter.ServerFilterChain
// import io.reactivex.Flowable
// import org.reactivestreams.Publisher

// @Filter("/**")
// class AuthFilter: HttpServerFilter {
//    override fun doFilter(request: HttpRequest<*>, chain: ServerFilterChain): Publisher<MutableHttpResponse<*>> {
//        val username = request.headers.getFirst("username")
//        if (!username.isPresent) {
//            return Flowable.fromArray(HttpResponse.unauthorized<Any>())
//        }

//        request.attributes.put("userId", username.get())
//        return chain.proceed(request)
//    }
// }