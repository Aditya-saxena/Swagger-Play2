package controllers

import play.api.mvc.{ Action, Controller }



object Application extends Controller {
  def index = Action {
    Ok(views.html.index("Hello Swagger!!!"))
  }
  def swagger = Action {
    request =>
      Ok(views.html.swagger())
  }

}
