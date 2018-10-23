package com.aimplicits.cats

import cats.effect.IO
import org.scalatest.{Matchers, OptionValues, WordSpecLike}
import fs2.{Stream, async}
import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.ExecutionContext.Implicits.global

class SampleFs2Spec extends WordSpecLike with Matchers with OptionValues with ScalaFutures {
  "FS2" should {
    "do the job" in {
      val neff1 = Stream.emits(Seq(1, 2, 3))
      println(s"neff1=" + neff1)
      val neff1res = neff1.toList
      println(s"neff1res=" + neff1res)
      //      val neff1Cres = neff1.compile.toList //doesn't compile - it's already Pure
      //      println(s"neff1Cres="+neff1Cres)

      val eff1 = Stream.eval(IO {
        println("effect1"); 1 + 1
      })

      println(s"eff1=" + eff1)
      val eff1res = eff1.compile.toList.unsafeRunSync()
      println(s"eff1res=" + eff1res)
    }

    "work with streams" in {

      val queue = fs2.async.boundedQueue[IO, String](maxSize = 1000).unsafeRunSync()

      queue.enqueue1("el 1")
      queue.enqueue1("el 2")

      val compiledStream = queue
        .dequeue
        .takeWhile(_ < "el 4")
        .map { el =>
        println("dequeued elelement $el")
        el
      }.compile

      queue.enqueue1("el 3")
      queue.enqueue1("el 4")


      val listIo = compiledStream.toList

      queue.enqueue1("el 5")
      queue.enqueue1("el 6")

      //      val f = listIo.unsafeToFuture().map { res =>
      //        println("Complete list: $res")
      //        res
      //      }
      val res = listIo.unsafeRunSync()
      println(s"res=$res")

      queue.enqueue1("el 7")
      queue.enqueue1("el 8")
    }
  }


}


