package com.aimplicits.http4s

import cats.data.OptionT
import cats.effect._
import cats.implicits._
import fs2.{Stream, StreamApp}
import fs2.StreamApp.ExitCode
import org.http4s._
import org.http4s.implicits._
import org.http4s.dsl.io._
import org.http4s.server.blaze._

import scala.concurrent.ExecutionContext.Implicits.global

object SampleMain2 extends StreamApp[IO] {

  val helloWorldService = HttpService[IO] {
    case req@(GET -> Root / "hello" / name) =>
      println(s"request headers=${req.headers}")
      Ok.apply(s"Hello, $name.", Header("X-Auth-Token", "sampleValue"))
  }

  override def stream(args: List[String], requestShutdown: IO[Unit]): Stream[IO, ExitCode] = {
    val s = MyMiddle(helloWorldService, Header("SomeHeader", "SomeValue"))
    BlazeBuilder[IO].bindHttp(8080, "localhost").mountService(s, "/").serve
  }

}

object MyMiddle {
  def addHeader(resp: Response[IO], header: Header) =
    resp match {
      case Status.Successful(resp) => resp.putHeaders(header)
      case resp => resp
    }

  def apply(service: HttpService[IO], header: Header) =
    service.map(addHeader(_, header))
}
