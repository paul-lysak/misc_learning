package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class User(id: Int, name: String)

object User {
  def structure(prefix: String = "") =
    int(prefix+"id") ~
    str(prefix+"name") map {
      case id~name => User(id, name)
    }  	
  
  def getAll(): List[User] = {
    DB.withConnection { implicit connection =>
      SQL("select * from APP_USERS").as(User.structure() *)
    }
  }
  
  def create(user: User, password: String): Option[User] = {
	 DB.withTransaction { implicit c =>
		SQL("insert into APP_USERS (name, password) values ({name}, {password})").on(
		  'name -> user.name,
		  'password -> password
		).executeUpdate()
		//TODO nice handling of duplicate names
		
		SQL("select * from APP_USERS where name={name}").on(
			'name->user.name
		).as(User.structure().singleOpt)
	 }	 
  }
  
  def delete(id: Int) = {
	 DB.withConnection { implicit c =>
		SQL("delete from APP_USERS WHERE id={id}").on(
		  'id -> id
		).executeUpdate()
	 }    
  }
  
  def checkCredentials(name: String, password: String): Option[User] = {
	DB.withConnection { implicit connection =>
	  SQL("select * from APP_USERS where name={name} and password={password}").on(
		'name->name, 'password -> password
		).as(User.structure().singleOpt)
	}  
  }
  
  def getById(id: Int): Option[User] = {
	DB.withConnection { implicit connection =>
	  SQL("select * from APP_USERS where id={id}").on(
		'id->id
		).as(User.structure().singleOpt)
	}
  }
}