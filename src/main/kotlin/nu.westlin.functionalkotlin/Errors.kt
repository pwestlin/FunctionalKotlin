package nu.westlin.functionalkotlin

// TODO: Impl equals and hashCode
sealed class UserError(val errorMessage: String) {

    override fun equals(other: Any?): Boolean {
        return other != null && other is UserError && this::class == other::class && this.errorMessage == other.errorMessage
    }

    override fun hashCode(): Int {
        return this::class.hashCode() + 32 * errorMessage.hashCode()
    }

    class UserAlreadyExistError(errorMessage: String) : UserError(errorMessage)
    class UserDoesNotExistError(errorMessage: String) : UserError(errorMessage)
    class UserFetchError(errorMessage: String) : UserError(errorMessage)
}
