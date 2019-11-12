package nu.westlin.functionalkotlin

// TODO: Add error message
sealed class UserError(val errorMessage: String) {
    class UserAlreadyExistError(errorMessage: String) : UserError(errorMessage)
    class UserDoesNotExistError(errorMessage: String) : UserError(errorMessage)
}
