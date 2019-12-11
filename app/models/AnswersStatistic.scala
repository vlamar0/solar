package models

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._

case class AnswersStatistic(
  items: List[Item],
  hasMore: Boolean,
  quotaMax: Int,
  quotaRemaining: Int,
)

trait AnswersStatisticJson {
  implicit val reads: Reads[AnswersStatistic] = (
    (__ \ "items").read[List[Item]] and
    (__ \ "has_more").read[Boolean] and
    (__ \ "quota_max").read[Int] and
    (__ \ "quota_remaining").read[Int]
  )(AnswersStatistic.apply _)
}

object AnswersStatistic extends AnswersStatisticJson





