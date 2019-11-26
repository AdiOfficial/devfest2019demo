package experiment

import com.googlecode.objectify.ObjectifyService
import com.googlecode.objectify.ObjectifyService.ofy
import com.googlecode.objectify.annotation.Entity
import com.googlecode.objectify.annotation.Id
import com.googlecode.objectify.annotation.Index
import com.googlecode.objectify.annotation.OnSave
import com.googlecode.objectify.impl.translate.opt.joda.JodaTimeTranslators
import io.micronaut.core.version.annotation.Version
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.RequestAttribute
import java.net.URI
import org.joda.time.DateTime
import java.util.UUID

@Controller("/games")
class GamesController {
    init {
        initialiseDatabase()
    }

    @Get("/")
    fun index(@RequestAttribute userId: String): HttpResponse<List<Game>> {
        return HttpResponse.ok(getOwnersGames(userId))
    }

    @Post("/")
    fun addItem(name: String, year: Int, @RequestAttribute userId: String): HttpResponse<String> {
        val game = Game(name, year, userId)
        saveGame(game)
        return HttpResponse.created<String>(URI.create("/${game.id}"))
    }

    @Delete("/{id}")
    fun deleteItem(id: String, @RequestAttribute userId: String): HttpResponse<String> {
        val game = getGame(id, userId)
        ofy().delete().entity(game).now()
        return HttpResponse.noContent()
    }

    @Get("/{id}")
    fun getItem(id: String, @RequestAttribute userId: String): HttpResponse<Game> {
        val game = getGame(id, userId)
        return HttpResponse.ok(game)
    }

    private fun getGame(id: String, userId: String): Game? {
        val game = ofy().load().type(Game::class.java).id(id).now() ?: null
        if (game?.owner != userId) return null
        return game
    }

    private fun getOwnersGames(userId: String): List<Game> {
        return ofy().load().type(Game::class.java).filter(Game::owner.name, userId).list()
    }

    private fun saveGame(game: Game) {
        ofy().save().entity(game).now()
    }

    private fun initialiseDatabase() {
        ObjectifyService.init()
        JodaTimeTranslators.add(ObjectifyService.factory())
        ObjectifyService.register(Game::class.java)
        ObjectifyService.begin()
    }
}


@Entity
data class Game(val name: String, val year: Int, @Index val owner: String): DatastoreEntity()

open class DatastoreEntity {
    @OnSave
    fun onsave() {
        this.modified = DateTime.now()
    }

    @Id
    var id: String = UUID.randomUUID().toString()
    var created: DateTime = DateTime.now()
    var modified: DateTime = DateTime.now()
}
