package com.aimplicits.http4s

import cats.effect._, org.http4s._, org.http4s.dsl.io._, scala.concurrent.ExecutionContext.Implicits.global
import cats.implicits._
import org.http4s.server.blaze._
import org.http4s.implicits._

object SampleMain1 {
  val helloWorldService = HttpService[IO] {
    case GET -> Root / "hello" / name =>
      Ok(s"Hello, $name.")
  }

  def main(args: Array[String]): Unit = {
    println("Hi there")

    helloWorldService.run
//    val stream = BlazeBuilder[IO].bindHttp(8080, "localhost").mountService(helloWorldService, "/").serve
    val builder = BlazeBuilder[IO].bindHttp(8080, "localhost").mountService(helloWorldService, "/").start
    val server = builder.unsafeRunSync()
    server.shutdown
    Thread.sleep(10000)
  }
}
