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
import cats.Show
import cats.implicits._
import scala.collection.SortedSet

object English {

  implicit val tideShow: Show[TideRow] = t =>
    s"${t.dow} ${t.time24} (${t.height.value}m)"

  def help(err: Error): String = {
    Console.err.println(err)
    "Problem fetching the tide times. @d6y please help!"
  }

  def text(tides: SortedSet[TideRow]): String = tides.toSeq match {

    case Seq() =>
      "Could not find tides. @d6y help!"

    case Seq(tide) => s"Next low tide is: ${tide.show}"

    case tides =>
      val text = tides
        .take(3)
        .map(_.show)
        .mkString("\n- ")
      s"""Next low tides are:\n\n- $text"""
  }
}

object Post {

  implicit val tideOrder: Ordering[TideRow] = (a, b) =>
    a.instant.getEpochSecond() compare b.instant.getEpochSecond()

  def nextTides(at: Instant): Either[Error, SortedSet[TideRow]] = {
    val mixedTidesAndErrors: List[Either[Error, TideRow]] =
      Amazon.fetchTides(at)
    val tides: Either[Error, List[TideRow]] = mixedTidesAndErrors.sequence
    tides.map(ts => uniqueTides(ts.to[SortedSet]))
  }

  def uniqueTides(tides: SortedSet[TideRow]): SortedSet[TideRow] = tides

  def main(args: Array[String]): Unit = {

    val now = Instant.now()
    val message = nextTides(now).fold(English.help, English.text)
    println(message)

    // TODO: the client.post methods should be value returning, so we can inspect results and log failures

    import ciris.{loadConfig, env}
    loadConfig(env[String]("MASTODON_ACCESS_TOKEN")) { Mastodon.apply }
      .foreach { client =>
        client.post(message)
      }

    loadConfig(
      env[String]("TWIT_CONSUMER_KEY"),
      env[String]("TWIT_TOKEN_VALUE"),
      env[String]("TWIT_CONSUMER_SECRET"),
      env[String]("TWIT_ACCESS_TOKEN")) { Twitter.apply }.foreach { client =>
      client.post(message)
    }

  }

}
