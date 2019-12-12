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

  /**
   * @api {GET} localhost:9000/search
   * @apiGroup Solar
   * @apiName Search
   * @apiParam   {Array[String]}  tag
   * @apiSuccess {Array[Object]}  list
   * @apiSuccess {String}         list.tag
   * @apiSuccess {Number}         list.tag.total
   * @apiSuccess {Number}         list.tag.answered
   * @apiSuccessExample {json} Ответ:
   *  [
   *    {
   *      "clojure": {
   *          "total": 100,
   *          "answered": 74
   *      }
   *    },
   *    {
   *      "scala": {
   *          "total": 100,
   *          "answered": 41
   *      }
   *    },
   *        ...
   *  ]
   */

  def search(tag: List[String]) = Action { _ =>

    val resultStatisticF = tag.map { lang =>
      OptionT(stackOverFlowService.getInfo(lang)).map { statistic =>
        Json.obj(
          lang -> Json.obj(
            "total" -> statistic.items.length,
            "answered" -> statistic.items.count(_.isAnswered)
          )
        )
      } getOrElse Json.obj(lang -> "Bad request")
    }

    Ok(Json.toJson(resultStatisticF.map(Await.result(_, Duration.Inf))))
  }
}