package com.karumi.kataloginlogoutkotlin

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import kotlinx.coroutines.coroutineScope

class LogInLogOutKata(private val clock: Clock) {
    companion object {
        private const val ALLOWED_CREDENTIALS = "admin"
    }

    suspend fun logIn(username: String, pass: String): Either<LogInError, String> = coroutineScope {
        when {
            username == ALLOWED_CREDENTIALS && pass == ALLOWED_CREDENTIALS -> username.right()
            containsInvalidChars(username) -> InvalidUsername.left()
            else -> InvalidCredentials.left()
        }
    }

    suspend fun logOut(): Boolean = coroutineScope {
        (clock.now.toDate().time % 2).toInt() == 0
    }

    private fun containsInvalidChars(username: String): Boolean =
        username.contains(".") || username.contains(",") || username.contains(";")
}

sealed class LogInError
object InvalidUsername : LogInError()
object InvalidCredentials : LogInError()
