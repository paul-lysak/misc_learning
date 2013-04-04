package controllers

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._

import models.User

object Application extends Controller {
	val userForm = Form(//TODO map to User object instead of tuple
	  tuple("name" -> nonEmptyText,
	  "password" -> nonEmptyText)
	)
  
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def ajaxIndex = TODO
  
  def classicIndex = Action {
    Ok(views.html.classicIndex())
  }
 
  def listUsers = Action {
	val users = User.getAll()
    Ok(views.html.listUsers(users, userForm))
  }
  
  def newUser = Action { implicit request =>
	  userForm.bindFromRequest.fold(
		errors => BadRequest(views.html.listUsers(User.getAll(), errors)),
		userTuple => {
		  User.create(userTuple._1, userTuple._2) //TODO use mapped object
		  Redirect(routes.Application.listUsers)
		}
	  )
  }
}
