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

case class Mastodon(accessToken: String) {

  import com.softwaremill.sttp._

  def post(msg: String): Unit = {

    val request = sttp
      .body(Map("status" -> msg))
      .header("Authorization", s"Bearer $accessToken")
      .header("Idempotency-Key", msg.hashCode.toString)
      .post(uri"https://mastodon.social/api/v1/statuses")

    println(request)
    //  synchronous backend
    implicit val backend = HttpURLConnectionBackend()
    val response: Id[Response[String]] = request.send()
    println(response)
  }
}
