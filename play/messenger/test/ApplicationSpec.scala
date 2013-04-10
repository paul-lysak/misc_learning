package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

import models.User
import models.Message

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ApplicationSpec extends Specification { //sequential
   
  "Application" should {
    "send 404 on a bad request" in {
      running(FakeApplication()) {
        route(FakeRequest(GET, "/boum")) must beNone        
      }
    }
    
    "render the index page" in {
      running(FakeApplication()) {
        val home = route(FakeRequest(GET, "/")).get
        
        status(home) must equalTo(OK)
        contentType(home) must beSome.which(_ == "text/html")
        contentAsString(home) must contain ("Your new application is ready.")
      }
    }	
  }

/*  
  "User DAO" should {
	"get all users" in {
//		running(FakeApplication(additionalConfiguration = Map("db.default.driver" -> "org.h2.Driver", "db.default.url" -> "jdbc:h2:mem:test;MODE=PostgreSQL")
//		)) {
		running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
			User.create(User(0, "testname"), "testname");
			val allUsers = User.getAll()
			println("all users="+allUsers)
		}
	}
  }
  */
}