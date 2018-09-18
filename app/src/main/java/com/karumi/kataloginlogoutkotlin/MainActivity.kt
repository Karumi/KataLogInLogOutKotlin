package com.karumi.kataloginlogoutkotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Presenter.View {

  private val presenter = Presenter(
      logInLogOutKata = LogInLogOutKata(Clock()),
      view = this
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    hookListeners()
  }

  private fun hookListeners() {
    logInButton.setOnClickListener {
      val username = username.text?.toString() ?: ""
      val password = password.text?.toString() ?: ""
      presenter.onLogInButtonTap(username, password)
    }
    logOutButton.setOnClickListener {
      presenter.onLogOutButtonTap()
    }
  }

  override fun showLogInForm() {
    username.visibility = View.VISIBLE
    password.visibility = View.VISIBLE
    logInButton.visibility = View.VISIBLE
  }

  override fun hideLogOutForm() {
    logOutButton.visibility = View.GONE
  }

  override fun hideLogInForm() {
    username.visibility = View.GONE
    password.visibility = View.GONE
    logInButton.visibility = View.GONE
  }

  override fun showLogOutForm() {
    logOutButton.visibility = View.VISIBLE
  }

  override fun showError(resource: Int) {
    Toast.makeText(this, resource, Toast.LENGTH_SHORT).show()
  }
}
