package com.dallaway.tidetimes

import java.time.{Instant, LocalDate, ZoneOffset}

import scala.util.{Failure, Success, Try}

case class TideRow(
    instant: Instant,
    height: Metre,
    highOrLow: HighOrLow,
    dow: String, // E.g., "Sunday"
    date: String, // E.g., "2017-12-25"
    time12: String, // E.g., "02:15"
    time24: String, // E.g., "2:15 AM",
    source: String // E.g., "EASYTIDE"
)

// Environment vars needed:
// AWS_ACCESS_KEY_ID
// AWS_SECRET_ACCESS_KEY

object Post {

  def main(args: Array[String]): Unit = {

    import scala.concurrent.Await
    import scala.concurrent.duration._
    import scala.concurrent.ExecutionContext.Implicits.global

    val now = LocalDate.now

  }

}
