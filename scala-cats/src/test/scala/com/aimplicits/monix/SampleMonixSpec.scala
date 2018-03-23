package com.aimplicits.monix

import org.scalatest.{Matchers, OptionValues, WordSpecLike}
import monix.eval._
import monix.execution.Ack
import org.scalatest.concurrent.ScalaFutures
import monix.execution.Scheduler.Implicits.global
import monix.reactive.{Observable, Observer}

import scala.concurrent.Future
import scala.concurrent.duration._

class SampleMonixSpec extends WordSpecLike with Matchers with OptionValues with ScalaFutures {
  "Monix" should {
    "run the Task" in {
      val t1 = Task {
        println("task1")
        1
      }
      val t2 = Task {
        println("task2")
        2
      }

      val t12 = for (r1 <- t1; r2 <- t2) yield r1 + r2 //flatMap all the things! - this one is also lazy and non-memoized

      println("running the tasks...")
      val v = t1.runAsync.futureValue
      println("task1 result = "+v)
      val v2 = t1.runAsync.futureValue //gets evaluated again - no memoization like in Future
      println("task1 result again = "+v2)

      val v12 = t12.runAsync.futureValue
      println("task12 result = "+v12)
    }

    "run the Observable" in {
      //Observables are typed
      val obs: Observable[Long] = Observable.interval(100.millis)
        .filter(_ % 2 == 0)
        .map(_ * 2)
        .flatMap(x => Observable.fromIterable(Seq(x, x)))
        .take(5) //stop after 5 elements
        .dump("Out") //print each item

      val c1 = obs.subscribe() //c1 can cancel the observer, but can't do futher transformations
      val c2 = obs.subscribe() //concurrently runs the same observable - dump strings will be interleaved

      val observer = new Observer[Long] {
        override def onNext(elem: Long): Future[Ack] = {
          println("Next: "+elem)
          Future.successful(Ack.Continue)
        }

        override def onError(ex: Throwable): Unit = {
          println("err: "+ex.getMessage)
        }

        override def onComplete(): Unit = {
          println("Done!")
        }
      }

      val c3 = obs.subscribe(observer) //with event callbacks
      Thread.sleep(1000)
    }

    "do the job" in {
      println(s"hi, Monix!")
    }
  }
}


