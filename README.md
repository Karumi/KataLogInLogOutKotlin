![Karumi logo][karumilogo] Kata LogIn/LogOut in Kotlin [![Build Status](https://travis-ci.org/Karumi/KataLogInLogOutKotlin.svg?branch=master)](https://travis-ci.org/Karumi/KataLogInLogOutKotlin)
============================

- We are here to practice unit testing.
- We are going to use [JUnit][espresso] to interact with the Application UI.
- We are going to practice pair programming.

---

## Tasks

Your task as Android Developer is to implement all the tasks described below one by one without skipping any step. 

**This repository is ready to build the application, pass the checkstyle and your tests in Travis-CI environments.**


Our recommendation for this exercise is:

  * Before starting
    1. Create a git repository with an empty Android app inside.
    2. Configure Travis CI or any other CI tool.
    3. Read the requirements twice.

  
## Tasks

**Important requirement:** Until the testing stage, all the code should be written inside the Activity but the fake API client.

* Movement 1: Writing the app.

  * Build an app with only one screen that allows you to log in if the username and the password are admin. Simulate there is a asynchronous network connection before considering the login as a success or an error.
  * After the log in, the user should be able to log out if the log out button is pressed and the second when the button was pressed was even.
  * Add validation for the username field so chars as ``,``, ``.``, and ``;`` are not allowed. Show a toast explaining the error if the username contain these chars.  

* Movement 2: Testing the app using just unit tests.

  * Take some time to think about what should you test and how. Did you find any issue with this code that does not let you test it?
  * Using some common testing patterns re-implement part of your app making it testable and start adding coverage. Think twice your changes before start coding and test all the code you move using just unit tests.
  
## Extra Tasks
  
* Movement 3: Persist information.

  * Make the app persist the user session and show the UI buttons based on the user persisted session.  
  
---

**If you get stuck, `master` branch contains the solution for this kata.**

# License

Copyright 2017 Karumi

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

[karumilogo]: https://cloud.githubusercontent.com/assets/858090/11626547/e5a1dc66-9ce3-11e5-908d-537e07e82090.png
[junit]: https://junit.org
