package cats.nonempty

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import cats.data.NonEmptyList
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import io.circe.Decoder.Result
import io.circe.{Decoder, Encoder, HCursor, Json}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe._, io.circe.generic.auto._

object Main extends App {
  implicit val system: ActorSystem = ActorSystem("parent-system")

  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val route =
    path("garage") {
      post {
        entity(as[Garage]) { garage =>
          complete(StatusCodes.OK, garage)
        }
      }
    }

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)



}

case class Car
(
  plate: String
)

case class Garage
(
  cars: NonEmptyList[Car]
)