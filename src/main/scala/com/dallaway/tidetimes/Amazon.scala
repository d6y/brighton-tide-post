package com.dallaway.tidetimes

import java.time.Instant

import com.dallaway.tidetimes.{High, HighOrLow, Low, Metre}

import scala.util.Try

object DynamoImplicits {

  import com.gu.scanamo._

  implicit val metreFormat =
    DynamoFormat.coercedXmap[Metre, Double, IllegalArgumentException](
      Metre.apply)(_.value)

  implicit val instantFormat =
    DynamoFormat.coercedXmap[Instant, Long, IllegalArgumentException](
      Instant.ofEpochMilli)(_.toEpochMilli)

  implicit val highLowFormat =
    DynamoFormat.coercedXmap[HighOrLow, String, IllegalArgumentException] {
      case "high" => High
      case "low" => Low
    } {
      case High => "high"
      case Low => "low"
    }
}

object Amazon {

  import com.gu.scanamo._
  import com.amazonaws.regions.Regions
  import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
  import com.amazonaws.services.dynamodbv2.model.BatchWriteItemResult

  import DynamoImplicits._

  def write(rows: Set[TideRow]): Try[List[BatchWriteItemResult]] = Try {
    val client = AmazonDynamoDBClientBuilder
      .standard()
      .withRegion(Regions.EU_WEST_1)
      .build()
    val table = Table[TideRow]("brighton-tide")
    val writeResult = Scanamo.putAll(client)("brighton-tide")(rows)
    writeResult
  }
}
