package com.aimplicits.cats

import cats.effect.IO
import org.scalatest.{Matchers, OptionValues, WordSpecLike}

import scala.concurrent.Promise
import scala.concurrent.ExecutionContext.Implicits.global

class SampleEffectsSpec extends WordSpecLike with Matchers with OptionValues {
  "Cats" should {
    "do the job" in {
      val eff1 = IO {
        println("Effect1")
      }

      val promise1 = Promise[String]()
      val eff2 = IO.async[String] { cb =>
        promise1.future
          .map((Right.apply[Throwable, String] _).andThen(cb))
          .failed
          .map((Left.apply[Throwable, String] _).andThen(cb))
      }
        .map { str =>
          println(s"Effect2 success map: $str")
        }
        .attempt
        .map { case Left(e) =>
          println(s"Effect2 failed: $e")
        case Right(v) =>
          println(s"Effect2 post processing: $v")
        }
      eff1.unsafeRunSync()

//      promise1.success("Successful Promise 1")
      promise1.failure(new RuntimeException("screw that"))
      eff2.unsafeRunSync()
      println(s"hi there")
    }

    "handle the exception" in {
      val eff1 = IO {
        println("Create a number")
        2
      }

      val eff2 = eff1.map {n =>
        if(n == 2) {
          println("Fail to map a number")
          sys.error("Something went wrong")
        }
        else {
          println("Multiply a number")
          n * 2
        }
      }


      val res1 = eff1.attempt.unsafeRunSync()
      println("res1="+res1) //Right(2)
      val res2 = eff2.attempt.unsafeRunSync()
      println("res2="+res2) //Left(RuntimeException(...))
    }
  }
}


