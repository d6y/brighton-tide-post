name := "brighton-tide-post"

scalaVersion := "2.12.6"

val cirisVersion = "0.10.2"

libraryDependencies ++= Seq(
  "com.gu" %% "scanamo" % "1.0.0-M7",
  "org.typelevel" %% "cats-core" % "1.2.0",
  "com.softwaremill.sttp" %% "core" % "1.3.0",
  "is.cir" %% "ciris-core" % cirisVersion,
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
