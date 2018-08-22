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
  import com.gu.scanamo.syntax._
  import com.amazonaws.regions.Regions
  import com.gu.scanamo.error.DynamoReadError
  import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
  import DynamoImplicits._

  def fetchTides(at: Instant): List[Either[Error, TideRow]] = {
    val client = AmazonDynamoDBClientBuilder
      .standard()
      .withRegion(Regions.EU_WEST_1)
      .build()
    val table = Table[TideRow]("brighton-tide")
    val low: HighOrLow = Low
    val ops = table.filter('instant >= at and ('highOrLow -> low)).scan()
        Scanamo.exec(client)(ops).toList.collect { convertDynamoErrsIntoError }
    }

  private val convertDynamoErrsIntoError = {
    case Left(err) => Left(Error(err.toString))
    case Right(tide) => Right(tide)
  } : PartialFunction[Either[DynamoReadError,TideRow], Either[Error,TideRow]]
}

