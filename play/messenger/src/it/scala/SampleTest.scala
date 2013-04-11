package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import play.api.Play
import play.api.db._
import anorm._

import models.User

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ApplicationSpec extends Specification { //sequential
   
  "It" should {
    "multiply 2 by 2" in {
	  val four = 2*2
	  four === 4
    }
  }
  
  def integrationDb() : Map[String, String] = {
    var config = FakeApplication().configuration //get configuration as it is in conf files
	Map(
		("db.default.driver" -> config.getString("test.db.default.driver").getOrElse("")),
		("db.default.url" -> config.getString("test.db.default.url").getOrElse("")),
		("db.default.user" -> config.getString("test.db.default.user").getOrElse("")),
		("db.default.password" -> config.getString("test.db.default.password").getOrElse(""))
	)
  }
  
  
  "User DAO" should {
	"get all users" in {
		running(FakeApplication(additionalConfiguration = integrationDb())) {
			User.getAll() === List() //expecting fresh database
			User.create(User(0, "testname"), "testname");
			User.getAll() === List(User(1, "testname")) //expecting first user created
		}
	}
  }  
}