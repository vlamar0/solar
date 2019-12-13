package services

import com.google.inject.Inject
import models.AnswersStatistic
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.libs.ws.{WSClient, WSResponse}

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext

class StackOverFlowService @Inject()(
  ws: WSClient
)(implicit ec: ExecutionContext) {

  private val TIMEOUT = 100.seconds
  private val HOST = "https://api.stackexchange.com"
  private val API = "/search"

  def validateAnswersStatistic(response: WSResponse): Option[AnswersStatistic] = {
    Json.parse(response.body).validate[AnswersStatistic] match {
      case JsSuccess(answersStatistic, _) => Some(answersStatistic)
      case JsError(_) => None
    }
  }

  def getInfo(tag: String) = {
    ws.url(HOST + API)
      .withRequestTimeout(TIMEOUT)
      .withQueryStringParameters(
        "pagesize" -> "100",
        "tagged" -> tag,
        "site" -> "stackoverflow",
        "order" -> "desc",
        "sort" -> "creation"
      )
      .get
      .map(validateAnswersStatistic)
  }
}
