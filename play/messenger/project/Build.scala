import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "messenger"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm,
	"postgresql" % "postgresql" % "9.1-901.jdbc4",
	"be.objectify" %% "deadbolt-scala" % "2.1-RC2"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
	resolvers += Resolver.url("Objectify Play Repository", url("http://schaloner.github.com/releases/"))(Resolver.ivyStylePatterns)     	
  )

}
