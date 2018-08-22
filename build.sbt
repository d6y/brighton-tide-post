name := "brighton-tide-post"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  "com.gu" %% "scanamo" % "1.0.0-M6",
  "org.typelevel" %% "cats-core" % "1.0.0",
  "org.scalatest" %% "scalatest" % "3.0.4" % "test"
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
