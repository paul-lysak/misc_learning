package com.aimplicits.http4s

import cats.data.{Kleisli, OptionT}
import cats.effect._
import cats.implicits._
import fs2.{Stream, StreamApp}
import fs2.StreamApp.ExitCode
import org.http4s._
import org.http4s.implicits._
import org.http4s.dsl.io._
import org.http4s.server.AuthMiddleware
import org.http4s.server.blaze._

import scala.concurrent.ExecutionContext.Implicits.global

object SampleAuth extends StreamApp[IO] {
//
//  case class User(id: Long, name: String)
//
//  private val hardcodedUser: Option[User] = Some(User(100500, "foobar"))
//
////  val authUser: Kleisli[OptionT[IO, ?], Request[IO], User] = Kleisli(_ => OptionT.liftF(IO(???)))
//  val authUser: Kleisli[OptionT[IO, ?], Request[IO], User] = Kleisli(_ => OptionT.liftF(IO(User(100500, "foobar"))))
//
//  val middleware: AuthMiddleware[IO, User] = AuthMiddleware(authUser)
//
//  val authedService: AuthedService[User, IO] =
//  AuthedService {
//    case GET -> Root / "welcome" as user => Ok(s"Welcome, ${user.name}")
//  }
//
//  override def stream(args: List[String], requestShutdown: IO[Unit]): Stream[IO, ExitCode] = {
//    val s = middleware(authedService)
//    BlazeBuilder[IO].bindHttp(8080, "localhost").mountService(s, "/").serve
//  }

}


