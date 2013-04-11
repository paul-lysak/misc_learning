import sbt._
import Keys._
import play.Project._
import anorm._
import java.sql.{Connection, DriverManager, ResultSet};

object ApplicationBuild extends Build {

  val appName         = "messenger"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm,
	"postgresql" % "postgresql" % "9.1-901.jdbc4" % "compile,it",
	"org.specs2" %% "specs2" % "1.14" % "it",
	"play" %% "play-test" % play.core.PlayVersion.current % "test,it"
  )

   def setUpDatabase = {
   //TODO conditionally clean
	executeIntegrationDbUpdate("DROP DATABASE INTEGRATION_TEST");
	executeIntegrationDbUpdate("CREATE DATABASE INTEGRATION_TEST");
  }
  
   def tearDownDatabase = {
//	executeIntegrationDbUpdate("DROP DATABASE INTEGRATION_TEST");
  }  
  
  
  
  val main = play.Project(appName, appVersion, appDependencies).configs(IntegrationTest)
  .settings( Defaults.itSettings : _*).
  settings(
    // Add your own project settings here
//	scalaVersion := "2.10.0",
	testOptions in IntegrationTest += Tests.Setup( setUpDatabase _ ),
	testOptions in IntegrationTest += Tests.Cleanup( tearDownDatabase _ )
  )
  
  
  def executeIntegrationDbUpdate(query: String) {
	//TODO move out config
	var res = executeUpdate("jdbc:postgresql://localhost/", "postgres", "1Qaz2Wsx", "org.postgresql.Driver", query);
  }

  def executeUpdate(conn_str: String, user: String, password: String, driver_class: String, query: String): Int = {
		Class.forName(driver_class)
		
		// Setup the connection
		val conn = DriverManager.getConnection(conn_str, user, password)
		try {
			val statement = conn.createStatement()
			return statement.executeUpdate(query)
		}
		finally {
			conn.close
		}  
  }
}


