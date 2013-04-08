package controllers

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._

import play.api.libs.json.Json
import play.api.libs.json.Format
import play.api.libs.json.JsValue
import play.api.libs.json.JsObject
import play.api.libs.json.JsNumber
import play.api.libs.json.JsString
import play.api.libs.json.Reads
import play.api.libs.json.Reads._
import play.api.libs.json._
import play.api.libs.json.util._
import play.api.libs.functional.syntax._

import models.User
import models.Message

object Application extends Controller {
	val STATUS_OK = "OK"
	val STATUS_FAIL = "FAIL"
	
	
	val userForm = Form(
	  tuple("name" -> text.verifying(nonEmpty),
	  "password" -> text.verifying(nonEmpty))
	)
 
 
  val userRead = ((__ \ "id").read[Int] and
		(__ \ "name").read[String])(User.apply _);
  val userWrite = ((__ \ "id").write[Int] and
		(__ \ "name").write[String])(unlift(User.unapply));
  implicit val UserFormat: Format[User] = Format (userRead, userWrite);

//  val messageRead = ((__ \ "id").read[Int] and
//		(__ \ "body").read[String])(Message.apply _);
  implicit val messageWrite = ((__ \ "id").write[Int] and
		(__ \ "sender").write[User] and
		(__ \ "receiver").write[User] and
		(__ \ "body").write[String])(unlift(Message.unapply));
//  implicit val MessageFormat: Format[Message] = Format (messageRead, messageWrite);
 
 //------
 //Index pages
 //------
 
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def ajaxIndex = Action {
    Ok(views.html.ajaxIndex())
  }
  
  def classicIndex = Action {
    Ok(views.html.classicIndex())
  }
 
 //------
 // User management
 //------ 
 
  def listUsers = Action {
	val users = User.getAll()
    Ok(views.html.listUsers(users, userForm))
  }
  
  def newUser = Action { implicit request =>
	  userForm.bindFromRequest.fold(
		errors => BadRequest(views.html.listUsers(User.getAll(), errors)),
		namePwd => {
		  User.create(User(0, namePwd._1), namePwd._2)
		  Redirect(routes.Application.listUsers)
		}
	  )
  }
  
  def deleteUser(id: Int) = Action {
	User.delete(id)
	Redirect(routes.Application.listUsers)
  }

//TODO make available only for authenticated
  def listUsersAjax = Action {
  	Ok(Json.toJson(User.getAll()));
  }

 //------
 // Authentication
 //------
  
 def getCurrentUser = Action { implicit request =>
	request.session.get("userId").map(userIdStr => userIdStr.toInt).flatMap(
			userId => User.getById(userId)
		).map(
			user => Ok(Json.toJson(user))
		).getOrElse {
			Unauthorized("No current user")
		}
 }
  
 def logIn = Action { implicit request =>
	val post = request.body.asFormUrlEncoded;
	val name = post.get("name")(0)
	val password = post.get("password")(0)
	User.checkCredentials(name, password) match {
		case Some(user) => 
			Ok(Json.toJson(user)).withSession("userId" -> user.id.toString)
		case None => 
			Unauthorized("Login failed")	
	}
 }

 def logOut = Action {
	Ok(Json.toJson(Json.obj("status"->STATUS_OK))).withNewSession
 } 
 
 def signUp = Action { implicit request =>
	  userForm.bindFromRequest.fold(
		errors => BadRequest("TODO: write errors"),
		namePwd => {
		  User.create(User(0, namePwd._1), namePwd._2) match {
			case Some(user) => Ok(Json.toJson(user)).withSession("userId" -> user.id.toString)
			case None => InternalServerError("Could not create user")
		  }
		}
	  )
  }

 //------
 // Messaging
 //------
//TODO wrap security around this method to ensure that user that sends a message is logged in
 def sendMessage(fromId: Int, toId:Int) = Action(parse.tolerantText) { implicit request =>
 	val messageText = request.body
	println("got message: "+messageText);
 	Message.send(fromId, toId, messageText);	
 	Ok("message sent");
 }

 def getInbox(userId: Int) = Action {
	val messages = Message.getInbox(userId);
	Ok(Json.toJson(messages));
 }
 
 def getOutbox(userId: Int) = Action {
	val messages = Message.getOutbox(userId);
	Ok(Json.toJson(messages));
 }
 
}
