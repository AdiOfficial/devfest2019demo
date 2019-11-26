package experiment

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.RequestAttribute
import java.net.URI
import java.util.UUID

@Controller("/games")
class GamesController {
    private val database = mutableListOf<Game>()

    @Get("/")
    fun index(@RequestAttribute userId: String): HttpResponse<List<Game>> {
        return HttpResponse.ok(database.filter { it.owner == userId })
    }

    @Post("/")
    fun addItem(name: String, year: Int, @RequestAttribute userId: String): HttpResponse<String> {
        val game = Game(name, year, userId)
        database.add(game)
        return HttpResponse.created<String>(URI.create("/${game.id}"))
    }

    @Delete("/{id}")
    fun deleteItem(id: String, @RequestAttribute userId: String): HttpResponse<String> {
        val game = database.singleOrNull { it.id == id && it.owner == userId } ?: return HttpResponse.notFound()
        database.remove(game)
        return HttpResponse.noContent()
    }

    @Get("/{id}")
    fun getItem(id: String, @RequestAttribute userId: String): HttpResponse<Game> {
        val game = database.singleOrNull { it.id == id && it.owner == userId } ?: return HttpResponse.notFound()
        return HttpResponse.ok(game)
    }
}

data class Game(val name: String, val year: Int, val owner: String) {
    val id = UUID.randomUUID().toString()
}