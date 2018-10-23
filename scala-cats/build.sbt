name := "learn-cats"

scalaVersion := "2.12.4"

version := "1.0"

val circeVersion = "0.9.1"


libraryDependencies += "org.typelevel" %% "cats-effect" % "0.10"
libraryDependencies += "org.typelevel" %% "cats-core" % "1.1.0"

libraryDependencies += "io.monix" %% "monix" % "3.0.0-RC1"

libraryDependencies += "co.fs2" %% "fs2-core" % "0.10.1" // For cats 1.0.1 and cats-effect 0.8
libraryDependencies += "co.fs2" %% "fs2-io" % "0.10.1"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.4" % "test"
)
