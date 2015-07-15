package controllers

import play.api.mvc.{ Action, Controller }
import com.wordnik.swagger.annotations._
import javax.ws.rs._
import javax.ws.rs.PathParam
import play.api.libs.Files.TemporaryFile
import java.io.File
import play.api.libs.json.Json
import play.api.mvc.MultipartFormData
import play.api.db.slick._
import play.api.data.Form
import play.api.data.Forms._

@Api(value = "/hello", description = "Some operation like GET,POST and Upload Image")
object Hello extends Controller {

  val messageForm = Form(
    tuple(
      "nick_name" -> nonEmptyText,
      "address" -> nonEmptyText))

  /* 
     * It is Get method and returns a greeting Hello Guest
     * */
  @ApiOperation(
    nickname = "Message",
    value = "Return message",
    notes = "Check message",
    httpMethod = "GET")
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Success"),
    new ApiResponse(code = 400, message = "Bad Request"),
    new ApiResponse(code = 500, message = "DB connection error")))
  def getHello = Action { implicit request =>
    Ok("Hello Guest!")
  }

  /* 
     * It is POST method and takes a parameter @name and return a greeting "Hello name"
     * */

  @ApiOperation(
    nickname = "PostHello",
    value = "It returns a greeting with your name",
    notes = "Post Method",
    httpMethod = "POST")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(value = "Data For Post Hello", required = true, dataType = "string", paramType = "body")))
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Success"),
    new ApiResponse(code = 400, message = "Bad Request"),
    new ApiResponse(code = 500, message = "DB connection error")))
  def postHello(@ApiParam(value = "name", required = true)@PathParam(value = "name") name: String) = Action { implicit request =>
    messageForm.bindFromRequest.fold(
      formWithErrors =>
        BadRequest("Some error in form submission" + formWithErrors),
      receiptDetail => {
        val nickname = receiptDetail._1
        val address = receiptDetail._2
        Ok("Hello " + name + "!" + "" + "your nick name is " + nickname + "and" + "address is " + address)

      })
  }

  /* It takes an image as well as @nick_name and @address*/

  @ApiOperation(
    nickname = "imageUpload",
    value = "image uploading based on image and other data",
    notes = "image upload",
    httpMethod = "POST",
    consumes = "multipart/form-data", produces = "multipart/form-data")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(value = "Query string", name = "image", required = true, paramType = "form", dataType = "file"), new ApiImplicitParam(name = "receiptForms", value = "Query strings", required = true, paramType = "body")))
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Success"),
    new ApiResponse(code = 400, message = "Bad Request"),
    new ApiResponse(code = 500, message = "DB connection error")))
  def upload = Action(parse.multipartFormData) { implicit request =>
    request.body.file("image").map { image =>
      val file = new File(s"/tmp/${image.filename}")
      image.ref.moveTo(file)
      //Perform your logic for image
    }
    Ok("Successfully uploaded")

  }

}