package models

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._

case class Item(
  tags: List[String],
  owner: Owner,
  isAnswered: Boolean,
  viewCount: Int,
  answerCount: Int,
  score: Int,
  lastActivityDate: Long,
  creationDate: Long,
  lastEditDate: Option[Long],
  questionId: Int,
  link: String,
  title: String
)

trait ItemJson {
  implicit val reads: Reads[Item] = (
    (__ \ "tags").read[List[String]] and
    (__ \ "owner").read[Owner] and
    (__ \ "is_answered").read[Boolean] and
    (__ \ "view_count").read[Int] and
    (__ \ "answer_count").read[Int] and
    (__ \ "score").read[Int] and
    (__ \ "last_activity_date").read[Long] and
    (__ \ "creation_date").read[Long] and
    (__ \ "last_edit_date").readNullable[Long] and
    (__ \ "question_id").read[Int] and
    (__ \ "link").read[String] and
    (__ \ "title").read[String]
  )(Item.apply _)
}

object Item extends ItemJson
