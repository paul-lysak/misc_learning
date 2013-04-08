package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class Message(id: Int, senderId: Int, receiverId: Int, body: String)

object Message {

  val structure = {
    get[Int]("id") ~
    get[Int]("senderId") ~
    get[Int]("receiverId") ~
    get[String]("body") map {
      case id~senderId~receiverId~body => Message(id, senderId, receiverId, body)
    }
  }
 
  //def getAll(): List[User] = {
  //  DB.withConnection { implicit connection =>
  //    SQL("select * from APP_USERS").as(User.structure *)
  //  }
  //}
  
  def send(fromId: Int, toId: Int, body: String) = {
	 DB.withTransaction { implicit c =>
		SQL("insert into MESSAGES (from_user_id, to_user_id, body) values ({fromId}, {toId}, {body})").on(
		  'fromId -> fromId,
		  'toId -> toId,
		  'body -> body
		).executeUpdate()
		//TODO get latest create message	
	 }	 
  }
} 
