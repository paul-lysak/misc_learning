package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class User(id: Int, name: String, password: String)

object User {

  val structure = {
    get[Int]("id") ~
    get[String]("name") ~
    get[String]("password") map {
      case id~name~password => User(id, name, password)
    }
  }
  
  def getAll(): List[User] = {
    DB.withConnection { implicit connection =>
      SQL("select * from APP_USERS").as(User.structure *)
    }
  }
  
  def create(name: String, password: String) = {
	 DB.withConnection { implicit c =>
		SQL("insert into APP_USERS (name, password) values ({name}, {password})").on(
		  'name -> name,
		  'password -> password
		).executeUpdate()
	 }  
  }
}