name := "hello"

version := "1.0"

scalaVersion := "2.10.1"

libraryDependencies ++= Seq("org.specs2" %% "specs2" % "1.14" % "test",
    "com.fasterxml.jackson.core" % "jackson-core" % "2.1.4",
    "com.fasterxml.jackson.core" % "jackson-databind" % "2.1.4",
    "com.fasterxml.jackson.core" % "jackson-annotations" % "2.1.4",
    "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.1.3"
    )