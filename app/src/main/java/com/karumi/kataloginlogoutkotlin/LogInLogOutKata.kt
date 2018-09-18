package com.karumi.kataloginlogoutkotlin

import arrow.core.Either
import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.left
import arrow.core.right

class LogInLogOutKata(private val clock: Clock) {
  companion object {
    private const val ALLOWED_CREDENTIALS = "admin"
  }

  fun logIn(username: String, pass: String): Either<LogInError, String> = when {
    username == ALLOWED_CREDENTIALS && pass == ALLOWED_CREDENTIALS -> username.right()
    containsInvalidChars(username) -> InvalidUsername.left()
    else -> InvalidCredentials.left()
  }

  fun logOut(): Option<Unit> = when {
    ((clock.now.toDate().time % 2).toInt() == 0) -> Some(Unit)
    else -> None
  }


  private fun containsInvalidChars(username: String): Boolean =
      username.contains(".") || username.contains(",") || username.contains(";")
}

sealed class LogInError
object InvalidUsername : LogInError()
object InvalidCredentials : LogInError()