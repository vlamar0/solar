package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json.Json
import cats.data.OptionT
import cats.implicits._
import services.StackOverFlowService

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}

class SolarController @Inject()(
  stackOverFlowService: StackOverFlowService
)(implicit ec: ExecutionContext) extends InjectedController {

  def search(tag: List[String]) = Action { _ =>

    val jsonsF = tag.map { lang =>
      OptionT(stackOverFlowService.getInfo(lang)).map { statistic =>
        Json.toJson(lang -> statistic.items.count(_.isAnswered))
      }.getOrElse(Json.toJson(lang -> "Bad request"))
    }

    Ok(Json.toJson(jsonsF.map(Await.result(_, Duration.Inf))))
  }
}