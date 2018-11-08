package com.dallaway.tidetimes

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

    def tide(time: String, source: String = "A"): TideRow =
      TideRow(Instant.parse(s"2018-01-01T${time}:00Z"), Metre(1.0), Low, "", "", "", "", source)

    val examples = Table(
      ("tides"                                                                          ,  "unique tides"),
      ( Nil                                                                             ,  Nil),
      ( tide("13:00") :: Nil                                                            ,  tide("13:00") :: Nil),
      ( tide("13:00") :: tide("19:00") :: Nil                                           ,  tide("13:00") :: tide("19:00") :: Nil),
      ( tide("13:00") :: tide("13:02") :: tide("19:00") :: Nil                          ,  tide("13:00") :: tide("19:00") :: Nil),
      ( tide("13:00", source="A") :: tide("13:00", source="B") :: tide("19:00") :: Nil  ,  tide("13:00") :: tide("19:00") :: Nil),
    )

    forAll(examples) { (inputTides, expectedTides) =>
      uniqueTides(inputTides.to[SortedSet]).toList shouldBe expectedTides
    }
  }
}
