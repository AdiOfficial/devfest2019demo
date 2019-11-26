private val database = mutableListOf<Game>()


@Get("/")
fun index(): HttpResponse<List<Game>> {
    return HttpResponse.ok(database)
}