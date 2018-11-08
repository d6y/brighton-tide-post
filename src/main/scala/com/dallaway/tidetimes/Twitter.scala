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

case class Twitter(
    consumer_key: String,
    token_value: String,
    consumer_secret: String,
    access_token_secret: String) {

  import twitter4j.TwitterFactory
  import twitter4j.auth.AccessToken
  import scala.util.Try

  def post(msg: String): Unit = {
    val twitter = TwitterFactory.getSingleton
    twitter.setOAuthConsumer(consumer_key, consumer_secret)
    twitter.setOAuthAccessToken(
      new AccessToken(token_value, access_token_secret))
    val status = Try(twitter.updateStatus(msg))
    println(status.map(_.getText))
  }
}
