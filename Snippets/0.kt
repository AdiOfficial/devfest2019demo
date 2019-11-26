package experiment

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import java.net.URI
import java.util.UUID

@Controller("/games")
class GamesController {

    @Get("/")
    fun index(): HttpResponse<Any> {
        return HttpResponse.ok()
    }
}
