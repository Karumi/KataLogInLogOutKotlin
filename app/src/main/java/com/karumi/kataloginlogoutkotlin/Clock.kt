package com.karumi.kataloginlogoutkotlin

import org.joda.time.DateTime

class Clock {
  val now: DateTime
    get() = DateTime.now()

  val secondsSince1970: Int
    get() = (now.toDate().time / 1000).toInt()
}