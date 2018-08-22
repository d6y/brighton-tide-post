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

import java.time._
import util.Try

sealed trait HighOrLow
case object Low extends HighOrLow
case object High extends HighOrLow

sealed trait DataSource
case object EasyTide extends DataSource
case object VisitBrighton extends DataSource

case class Metre(value: Double) extends AnyVal
case class Tide(gmt: LocalDateTime, height: Metre, highOrLow: HighOrLow)
