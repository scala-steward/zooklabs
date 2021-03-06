package zooklabs.repository.model

import eu.timepit.refined.types.all.NonNegInt
import eu.timepit.refined.types.string.NonEmptyString
import io.circe.Encoder
import io.circe.refined.refinedEncoder

final case class TrialEntity(
    zookId: NonNegInt,
    name: NonEmptyString,
    score: Double,
    position: Int = Int.MaxValue,
    disqualified: Boolean = false
)

object TrialEntity {

  implicit val encodeTrialEntity: Encoder[TrialEntity] =
    Encoder.forProduct5(
      "zookId",
      "name",
      "score",
      "position",
      "disqualified"
    )(u =>
      (
        u.zookId,
        u.name,
        u.score,
        u.position,
        u.disqualified
      )
    )
}
