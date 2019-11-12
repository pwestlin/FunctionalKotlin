package nu.westlin.functionalkotlin

// TODO: Add error message
sealed class UserError() {
    object UserAlreadyExistError : UserError()
    object UserDoesNotExistError : UserError()
}
