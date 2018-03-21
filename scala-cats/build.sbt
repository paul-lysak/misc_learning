name := "learn-cats"

scalaVersion := "2.12.4"

version := "1.0"

val circeVersion = "0.9.1"


libraryDependencies += "org.typelevel" %% "cats-effect" % "0.10"
libraryDependencies += "org.typelevel" %% "cats-core" % "1.1.0"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.4" % "test"
)
