package com.karumi.kataloginlogoutkotlin

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PresenterTest {

    companion object {
        private const val ANY_USERNAME = "username"
        private const val ANY_PASS = "pass"
    }

    @Mock
    private lateinit var kata: LogInLogOutKata
    @Mock
    private lateinit var view: Presenter.View

    private lateinit var presenter: Presenter

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        presenter = Presenter(kata, view, Dispatchers.Unconfined)
    }

    @Test
    fun `should show invalid credentials error if the log in process returns InvalidCredentials`() {
        givenTheLogInProcessReturns(InvalidCredentials.left())

        presenter.onLogInButtonTap(ANY_USERNAME, ANY_PASS)

        verify(view).showError(R.string.log_in_error_message)
    }

    @Test
    fun `should show an invalid username error if the log in process returns InvalidUsername`() {
        givenTheLogInProcessReturns(InvalidUsername.left())

        presenter.onLogInButtonTap(ANY_USERNAME, ANY_PASS)

        verify(view).showError(R.string.invalid_username_error_message)
    }

    @Test
    fun `should show a could not perform log out error if the log out process fails`() {
        givenTheLogOutProcessReturns(false)

        presenter.onLogOutButtonTap()

        verify(view).showError(R.string.log_out_error_message)
    }

    @Test
    fun `should hide the login form & show the logout form if the login process finishes`() {
        givenTheLogInProcessReturns(ANY_USERNAME.right())

        presenter.onLogInButtonTap(ANY_USERNAME, ANY_PASS)

        verify(view).hideLogInForm()
        verify(view).showLogOutForm()
    }

    @Test
    fun `should hide the logout form and show the login form if the logout process finishes`() {
        givenTheLogOutProcessReturns(true)

        presenter.onLogOutButtonTap()

        verify(view).hideLogOutForm()
        verify(view).showLogInForm()
    }

    private fun givenTheLogOutProcessReturns(result: Boolean) {
        runBlocking { whenever(kata.logOut()).thenReturn(result) }
    }

    private fun givenTheLogInProcessReturns(result: Either<LogInError, String>) {
        runBlocking { whenever(kata.logIn(any(), any())).thenReturn(result) }
    }
}
