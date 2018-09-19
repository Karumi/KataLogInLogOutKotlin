package com.karumi.kataloginlogoutkotlin

import arrow.core.left
import arrow.core.right
import com.nhaarman.mockito_kotlin.whenever
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.assertFalse
import org.joda.time.DateTime
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LogInLogOutKataTest {

    companion object {
        private const val ANY_INVALID_PASSWORD = "password"
        private const val ANY_VALID_PASSWORD = "admin"
        private const val ANY_INVALID_USERNAME = "username"
        private const val ANY_VALID_USERNAME = "admin"
    }

    @Mock
    private lateinit var clock: Clock

    private lateinit var logInLogOutKata: LogInLogOutKata

    @Before
    fun setUp() {
        logInLogOutKata = LogInLogOutKata(clock)
    }

    @Test
    fun `should return an invalid username error if the username contains the first invalid char char chars`() {
        val logInResult = logInLogOutKata.logIn("ad,min", ANY_INVALID_PASSWORD)

        assertEquals(InvalidUsername.left(), logInResult)
    }

    @Test
    fun `should return an invalid username error if the username contains the second invalid char char chars`() {
        val logInResult = logInLogOutKata.logIn("ad.min", ANY_INVALID_PASSWORD)

        assertEquals(InvalidUsername.left(), logInResult)
    }

    @Test
    fun `should return an invalid username error if the username contains the third invalid char char chars`() {
        val logInResult = logInLogOutKata.logIn("ad;min", ANY_INVALID_PASSWORD)

        assertEquals(InvalidUsername.left(), logInResult)
    }

    @Test
    fun `should return an invalid credentials error if the username is not correct`() {
        val logInResult = logInLogOutKata.logIn(ANY_INVALID_USERNAME, ANY_VALID_PASSWORD)

        assertEquals(InvalidCredentials.left(), logInResult)
    }

    @Test
    fun `should return an invalid credentials error if the password is not correct`() {
        val logInResult = logInLogOutKata.logIn(ANY_VALID_USERNAME, ANY_INVALID_PASSWORD)

        assertEquals(InvalidCredentials.left(), logInResult)
    }

    @Test
    fun `should return the user username and a success result if the user and password are corrects`() {
        val logInResult = logInLogOutKata.logIn(ANY_VALID_USERNAME, ANY_VALID_PASSWORD)

        assertEquals(ANY_VALID_USERNAME.right(), logInResult)
    }

    @Test
    fun `should return an error if the second when the log out is performed is odd`() {
        givenNowIs(DateTime(3))

        val logOutResult = logInLogOutKata.logOut()

        assertFalse(logOutResult)
    }

    @Test
    fun `should return success if the second when the log out is performed is even`() {
        givenNowIs(DateTime(2))

        val logOutResult = logInLogOutKata.logOut()

        assertTrue(logOutResult)
    }

    private fun givenNowIs(dateTime: DateTime) {
        whenever(clock.now).thenReturn(dateTime)
    }
}
