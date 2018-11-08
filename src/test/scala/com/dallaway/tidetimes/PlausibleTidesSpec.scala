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

import org.scalatest._
abstract class Spec extends FreeSpec with Matchers

class PlausibleTidesSpect extends Spec {

  import java.time.Instant
  import scala.collection.SortedSet
  import org.scalatest.prop.TableDrivenPropertyChecks._
  import Post._

  // Tides may be from mixed sources, with slightly different times for the same tide.
  // We guard against confusing reporting by ignoring tides that occur within a short period of another.
  // For more discussion: https://gitlab.com/d6y/brightontide-post/snippets/1752912
  "Tides should not occur too frequently" in {

    // format: off
    def tide(time: String): TideRow =
      TideRow( Instant.parse(s"2018-01-01T${time}:00Z"), Metre(1.0), Low, "", "", "", "", "") 

    val examples = Table(
      ("tides"                                                 ,  "unique tides"),
      (Nil                                                     ,  Nil),
      (tide("13:00") :: Nil                                    ,  tide("13:00") :: Nil),
      ( tide("13:00") :: tide("19:00") :: Nil                  ,  tide("13:00") :: tide("19:00") :: Nil),
      ( tide("13:00") :: tide("13:02") :: tide("19:00") :: Nil ,  tide("13:00") :: tide("19:00") :: Nil),
      ( tide("13:00") :: tide("13:00") :: tide("19:00") :: Nil ,  tide("13:00") :: tide("19:00") :: Nil),
    )
    // format: on

    forAll(examples) { (inputTides, expectedTides) =>
      uniqueTides(inputTides.to[SortedSet]).toList shouldBe expectedTides
    }
  }
}
