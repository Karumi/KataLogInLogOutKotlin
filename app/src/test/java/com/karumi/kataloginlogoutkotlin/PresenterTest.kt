package com.karumi.kataloginlogoutkotlin

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executors

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
        Dispatchers.setMain(Executors.newSingleThreadExecutor().asCoroutineDispatcher())
        presenter = Presenter(kata, view)
    }

    @Test
    fun `should show an invalid credentials error if the log in process returns InvalidCredentials`() = runBlocking {
        givenTheLogInProcessReturns(InvalidCredentials.left())

        presenter.onLogInButtonTap(ANY_USERNAME, ANY_PASS).join()

        verify(view).showError(R.string.log_in_error_message)
    }

    @Test
    fun `should show an invalid username error if the log in process returns InvalidUsername`() = runBlocking {
        givenTheLogInProcessReturns(InvalidUsername.left())

        presenter.onLogInButtonTap(ANY_USERNAME, ANY_PASS).join()

        verify(view).showError(R.string.invalid_username_error_message)
    }

    @Test
    fun `should show a could not perform log out error if the log out process fails`() = runBlocking {
        givenTheLogOutProcessReturns(false)

        presenter.onLogOutButtonTap().join()

        verify(view).showError(R.string.log_out_error_message)
    }

    @Test
    fun `should hide the log in form and show the log out form if the log in process finished properly`() = runBlocking {
        givenTheLogInProcessReturns(ANY_USERNAME.right())

        presenter.onLogInButtonTap(ANY_USERNAME, ANY_PASS).join()

        verify(view).hideLogInForm()
        verify(view).showLogOutForm()
    }

    @Test
    fun `should hide the log out form and show the log in form if the log out process finished properly`() = runBlocking {
        givenTheLogOutProcessReturns(true)

        presenter.onLogOutButtonTap().join()

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
