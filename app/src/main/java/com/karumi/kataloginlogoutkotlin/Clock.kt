package com.karumi.kataloginlogoutkotlin

import org.joda.time.DateTime

class Clock {
  val now: DateTime
    get() = DateTime.now()
}