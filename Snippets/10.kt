data class Game(val name: String, val year: Int) {
   val id = UUID.randomUUID().toString()
}
