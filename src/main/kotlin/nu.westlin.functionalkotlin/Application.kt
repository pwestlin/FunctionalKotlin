package nu.westlin.functionalkotlin

data class User(val name: String) {
    init {
        // TODO: Side effect
        require(name.length in 6..16) { "Name has to be 6-16 characters long" }
    }

    companion object
}

class UserRepository {
    private val users = ArrayList<User>()

    fun all(): List<User> = users.toList()

    // TODO: Value or null
    fun get(name: String): User? = users.firstOrNull { it.name == name }

    fun add(user: User) {
        // TODO: Side effect
        require(!users.contains(user)) { "User $user already exist." }
        // TODO: Side effect
        users.add(user)
    }

    fun remove(user: User) {
        // TODO: Side effect
        require(users.contains(user)) { "User $user does not exist." }
        // TODO: Side effect
        users.remove(user)
    }
}

class UserService(private val repository: UserRepository) {

    // TODO: Value or null
    fun getUser(name: String): User? = repository.get(name)
    fun add(user: User) = add(listOf(user))
    fun add(users: List<User>) {
        users.forEach(repository::add)
    }
}

class Presenter(private val service: UserService) {
    fun addUsersAndPrintThem(users: List<User>) {
        service.add(users)

        // TODO: Side effect
        users.forEach(::println)
    }
}

fun main() {
    Presenter(UserService(UserRepository())).apply {
        addUsersAndPrintThem(listOf(User("foobar"), User("raboof")))
    }
}