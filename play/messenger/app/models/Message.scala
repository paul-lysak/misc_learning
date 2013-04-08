package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class Message(id: Int, sender: User, receiver: User, body: String)

object Message {	
  val structure = {
    int("id") ~
    User.structure("sender_") ~
    User.structure("receiver_") ~
    str("body") map {
      case id~sender~receiver~body => Message(id, sender, receiver, body)
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
  
  def getOutbox(userId: Int) = {
    DB.withConnection { implicit connection =>
      SQL("""
		SELECT m.id, s.id AS sender_id, s.name AS sender_name, r.id AS receiver_id, r.name AS receiver_name, m.body 
		FROM MESSAGES m 
			JOIN APP_USERS s ON m.from_user_id = s.id 
			JOIN APP_USERS r ON m.to_user_id = r.id 
		WHERE m.from_user_id={fromId}
	  """).on(
		'fromId -> userId
	  ).as(Message.structure *)
    }
  }
  
  //TODO reuse large part of SQL
  def getInbox(userId: Int) = {
    DB.withConnection { implicit connection =>
      SQL("""
		SELECT m.id, s.id AS sender_id, s.name AS sender_name, r.id AS receiver_id, r.name AS receiver_name, m.body 
		FROM MESSAGES m 
			JOIN APP_USERS s ON m.from_user_id = s.id 
			JOIN APP_USERS r ON m.to_user_id = r.id 
		WHERE m.to_user_id={toId}
	  """).on(
		'toId -> userId
	  ).as(Message.structure *)
    }
  }  
} 
