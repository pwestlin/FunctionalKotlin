package nu.westlin.functionalkotlin

import arrow.core.Either
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class PresenterTest {

    private val service = mockk<UserService>(relaxed = true)
    private val logger = mockk<Logger>(relaxed = true)
    private val presenter = Presenter(service, logger)

    @Test
    fun `add two users and print them`() {
        val users = listOf(user1, user2)
        every { service.add(users) } returns Either.Right(Unit)

        presenter.addUsersAndPrintThem(users)

        verify {
            service.add(users)
            logger.info("All users added")
        }
    }

    @Test
    fun `add two users and print them when a user already exist`() {
        val users = listOf(user1)
        val errorMessage = "User $user1 already exist"
        every { service.add(users) } returns Either.Right(Unit)

        presenter.addUsersAndPrintThem(users)
        verify {
            service.add(users)
            logger.info("All users added")
        }
        verify(exactly = 0) { logger.error(any()) }

        clearMocks(logger, service)
        every { service.add(users) } returns Either.Left(UserError.UserAlreadyExistError(errorMessage))
        presenter.addUsersAndPrintThem(users)
        verify {
            service.add(users)
            logger.error(errorMessage)
        }
        verify(exactly = 0) { logger.info(any()) }
    }

    @Test
    fun `print all users`() {
        val users = listOf(user1, user2)
        every { service.getAllUsers() } returns { Either.Right(users) }

        presenter.printAllUsers()

        verify {
            service.getAllUsers()
            logger.info("All users:")
            logger.info("\t$user1")
            logger.info("\t$user2")
        }
    }

}