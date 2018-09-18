package com.karumi.kataloginlogoutkotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import co.metalab.asyncawait.async
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  private val logInLogOutKata = LogInLogOutKata(Clock())

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    hookListeners()
  }

  private fun hookListeners() {
    logInButton.setOnClickListener {
      val username = username.text?.toString() ?: ""
      val password = password.text?.toString() ?: ""
      logIn(username, password)
    }
    logOutButton.setOnClickListener {
      logOut()
    }
  }

  private fun logIn(username: String, password: String) = async {
    val logInResult = await { logInLogOutKata.logIn(username, password) }
    logInResult.fold(
        {
          when (it) {
            is InvalidCredentials -> showError(R.string.log_in_error_message)
            is InvalidUsername -> showError(R.string.invalid_username_error_message)
          }
        }
        ,
        {
          hideLogInForm()
          showLogOutForm()
        }
    )
  }

  private fun logOut() = async {
    val logOutResult = await { logInLogOutKata.logOut() }
    logOutResult.fold(
        {
          showError(R.string.log_out_error_message)
        }
        ,
        {
          hideLogOutForm()
          showLogInForm()
        }
    )
  }

  private fun showLogInForm() {
    username.visibility = View.VISIBLE
    password.visibility = View.VISIBLE
    logInButton.visibility = View.VISIBLE
  }

  private fun hideLogOutForm() {
    logOutButton.visibility = View.GONE
  }

  private fun showError(errorMessage: Int) {
    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
  }

  private fun hideLogInForm() {
    username.visibility = View.GONE
    password.visibility = View.GONE
    logInButton.visibility = View.GONE
  }

  private fun showLogOutForm() {
    logOutButton.visibility = View.VISIBLE
  }
}
