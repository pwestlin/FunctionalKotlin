package nu.westlin.functionalkotlin

// TODO: Impl equals and hashCode
sealed class UserError(val errorMessage: String) {
    class UserAlreadyExistError(errorMessage: String) : UserError(errorMessage)
    class UserDoesNotExistError(errorMessage: String) : UserError(errorMessage)
    class UserFetchError(errorMessage: String) : UserError(errorMessage)
}
