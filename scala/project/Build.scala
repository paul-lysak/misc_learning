import sbt._
import Keys._

object BuildSettings {
  val buildSettings = Defaults.defaultSettings ++ Seq(
    version := "1.0.0",
    scalaVersion := "2.10.2",
    scalacOptions ++= Seq()
  )
}

object MyBuild extends Build {
  import BuildSettings._

  lazy val root: Project = Project(
    id = "root",
    base = file(".")
  ) dependsOn(macrodef)

  lazy val macrodef: Project = Project(
    id = "macrodef",
    base = file("macrodef"),
    settings = buildSettings ++ Seq(
      libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-reflect" % _))
  )
}