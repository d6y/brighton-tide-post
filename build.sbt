name := "brighton-tide-post"

scalaVersion := "2.12.8"

val cirisVersion = "0.11"

libraryDependencies ++= Seq(
  "com.gu" %% "scanamo" % "1.0.0-M8",
  "org.typelevel" %% "cats-core" % "1.6.1",
  "com.softwaremill.sttp" %% "core" % "1.6.3",
  "is.cir" %% "ciris-core" % cirisVersion,
  "org.twitter4j" % "twitter4j-core" % "4.0.7",
  "org.scalatest" %% "scalatest" % "3.0.8" % "test",
)


scalacOptions := Seq(
  "-deprecation",
  "-encoding",
  "utf-8",
  "-unchecked",
  "-feature",
  "-Xfatal-warnings",
  "-Xfuture",
  "-Ypartial-unification",
  "-Ywarn-value-discard",
  "-Xlint:missing-interpolator",
  "-Xlint:infer-any",
  "-Ywarn-unused:imports",
  "-language:higherKinds",
  "-language:postfixOps",
)
