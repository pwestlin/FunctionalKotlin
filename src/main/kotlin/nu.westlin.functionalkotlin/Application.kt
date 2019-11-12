package nu.westlin.functionalkotlin

data class User(val name: String) {
    init {
        require(name.length in 6..16) { "Name has to be 6-16 characters long" }
    }

    companion object
}

class UserRepository {
    private val users = ArrayList<User>()

    fun all(): List<User> = users.toList()

    fun get(name: String): User? = users.firstOrNull { it.name == name }

    fun add(user: User) {
        require(!users.contains(user)) { "User $user already exist." }
        users.add(user)
    }

    fun remove(user: User) {
        require(users.contains(user)) { "User $user does not exist." }
        users.remove(user)
    }
}

// TODO: Add class UserDetail and UserDetailRepository so the UserService has some "right to exist"?

class UserService(private val repository: UserRepository) {

    fun getUser(name: String): User? = repository.get(name)
    fun add(user: User) = add(listOf(user))
    fun add(users: List<User>) {
        users.forEach(repository::add)
    }
}

class Presenter(private val service: UserService) {
    fun addUsersAndPrintThem(users: List<User>) {
        service.add(users)

        users.forEach(::println)
    }

}

fun main() {
    Presenter(UserService(UserRepository())).apply {
        addUsersAndPrintThem(listOf(User("foobar"), User("raboof")))
    }
}