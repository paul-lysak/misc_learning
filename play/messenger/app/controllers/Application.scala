package controllers

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._

import models.User

object Application extends Controller {
	val userForm = Form(
	  mapping("name" -> text.verifying(nonEmpty),
	  "password" -> text.verifying(nonEmpty))
	((name, password) => User(0, name, password))(user => Option((user.name, user.password))))
  
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
		user => {
		  User.create(user)
		  Redirect(routes.Application.listUsers)
		}
	  )
  }
  
  def deleteUser(id: Int) = Action {
	User.delete(id)
	Redirect(routes.Application.listUsers)
  }
}
