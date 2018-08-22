name := "brighton-tide-post"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  "com.gu" %% "scanamo" % "1.0.0-M6",
  "org.typelevel" %% "cats-core" % "1.2.0",
  "com.softwaremill.sttp" %% "core" % "1.3.0",
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
