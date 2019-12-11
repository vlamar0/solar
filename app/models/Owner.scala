package models

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._

case class Owner(
  reputation: Option[Int],
  userId: Option[Int],
  userType: String,
  acceptRate: Option[Int],
  profileImage: Option[String],
  displayName: Option[String],
  link: Option[String]
)

trait OwnerJson {
  implicit val reads: Reads[Owner] = (
    (__ \ "reputation").readNullable[Int] and
    (__ \ "user_id").readNullable[Int] and
    (__ \ "user_type").read[String] and
    (__ \ "accept_rate").readNullable[Int] and
    (__ \ "profile_image").readNullable[String] and
    (__ \ "display_name").readNullable[String] and
    (__ \ "link").readNullable[String]
  )(Owner.apply _)
}

object Owner extends OwnerJson
