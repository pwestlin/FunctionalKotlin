package nu.westlin.functionalkotlin

data class User(val id: Int, val name: String)

class UserRepository {
    private val users = ArrayList<User>()

    fun get(id: Int): User? = users.firstOrNull { it.id == id }
}