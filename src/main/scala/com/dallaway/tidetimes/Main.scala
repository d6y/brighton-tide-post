package com.dallaway.tidetimes

/*
  Copyright 2009-2018 Richard Dallaway

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

import java.time.Instant

// Environment vars needed:
// AWS_ACCESS_KEY_ID
// AWS_SECRET_ACCESS_KEY
// MASTODON_ACCESS_TOKEN

import cats.Show
import cats.implicits._

object Post {

  def nextTides(at: Instant): Either[Error, List[TideRow]] = {
    val mixedTidesAndErrors: List[Either[Error, TideRow]] =
      Amazon.fetchTides(at)
    val tides: Either[Error, List[TideRow]] = mixedTidesAndErrors.sequence
    tides.map(ts => ts.sortBy(_.instant))
  }

  implicit val tideShow: Show[TideRow] = t =>
    s"${t.dow} ${t.time24} (${t.height.value}m)"

  def main(args: Array[String]): Unit = {

    val now = Instant.now()

    val post = nextTides(now) match {
      case Left(err) =>
        Console.err.println(err)
        "Problem fetching the tide times. @d6y please help!"
      case Right(Nil) =>
        "Could not find tides. @d6y help!"
      case Right(tide :: Nil) => s"Next low tide is: ${tide.show}"
      case Right(tides) =>
        s"""Next low tides are:\n\n- ${tides
          .take(3)
          .map(_.show)
          .mkString("\n- ")}"""
    }

    println(post)

    import ciris.{env, Secret}
    env[Secret[String]]("MASTODON_ACCESS_TOKEN").sourceValue.foreach { token =>
      val client = Mastodon(token)
      client.post(post)
    }

  }

}
