package com.aimplicits.http4s

import cats.effect._
import fs2.{Stream, StreamApp}
import fs2.StreamApp.ExitCode
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.server.blaze._

import scala.concurrent.ExecutionContext.Implicits.global

object SampleMain2 extends StreamApp[IO] {
  val helloWorldService = HttpService[IO] {
    case GET -> Root / "hello" / name =>
      Ok(s"Hello, $name.")
  }


  override def stream(args: List[String], requestShutdown: IO[Unit]): Stream[IO, ExitCode] =
    BlazeBuilder[IO].bindHttp(8080, "localhost").mountService(helloWorldService, "/").serve

}
