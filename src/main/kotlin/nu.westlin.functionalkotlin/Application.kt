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

    fun all(): () -> Either<UserError.UserFetchError, List<User>> = { Either.right(users.toList()) }

    // TODO: Defer execution
    // TODO petves: Return Option (which I havent implemented...yet :) )
    fun get(name: String): Either<UserError.UserDoesNotExistError, User> {
        return users.firstOrNull { it.name == name }?.let { Either.right(it) }
            ?: Either.left(UserError.UserDoesNotExistError("User with name $name does not exist"))
    }

    // TODO: Defer execution
    fun add(user: User): Either<UserError.UserAlreadyExistError, Unit> {
        return if (users.contains(user)) {
            Either.left(UserError.UserAlreadyExistError("User $user already exist."))
        } else {
            users.add(user)
            Either.right(Unit)
        }
    }

    // TODO: Defer execution
    fun remove(user: User): Either<UserError.UserDoesNotExistError, Unit> {
        return if (users.contains(user)) {
            users.remove(user)
            Either.right(Unit)
        } else {
            Either.left(UserError.UserDoesNotExistError("User $user does not exist."))
        }
    }
}

class UserService(private val repository: UserRepository, private val logger: Logger) {

    fun getAllUsers(): () -> Either<UserError.UserFetchError, List<User>> = repository.all()

    // TODO: Defer execution
    fun getUser(name: String): Either<UserError.UserDoesNotExistError, User> = repository.get(name)

    // TODO: Defer execution
    fun add(user: User) = add(listOf(user))

    // TODO: Defer execution
    @Suppress("UNUSED_EXPRESSION")
    fun add(users: List<User>): Either<UserError.UserAlreadyExistError, Unit> {
        users.forEach { user ->
            repository.add(user).fold(
                { error -> logger.error(error.errorMessage); return Either.Left(error) },
                { it }
            )
        }

        return Either.right(Unit)
    }
}

class Presenter(private val service: UserService, private val logger: Logger) {

    fun addUsersAndPrintThem(users: List<User>) {
        service.add(users).fold(
            { error -> logger.error(error.errorMessage) },
            { logger.info("All users added") }
        )
    }

    fun printAllUsers() {
        // TODO petves: service.getAllUsers().map (which is not implemented...yet :) )
        service.getAllUsers()().fold(
            { error -> logger.error(error.errorMessage) },
            { users ->
                if (users.isEmpty()) {
                    logger.info("No users")
                } else {
                    logger.info("All users:")
                    users.forEach { logger.info("\t$it") }
                }
            }
        )
    }
}

fun main() {
    Presenter(
        service = UserService(repository = UserRepository(), logger = Logger<UserRepository>()),
        logger = Logger<Presenter>()
    ).apply {
        printAllUsers()
        addUsersAndPrintThem(listOf(User("foobar"), User("raboof")))
        printAllUsers()
        addUsersAndPrintThem(listOf(User("komaklasse"), User("raboof")))
        printAllUsers()
    }
}