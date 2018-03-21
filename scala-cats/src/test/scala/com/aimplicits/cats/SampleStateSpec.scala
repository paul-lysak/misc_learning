package com.aimplicits.cats

import cats.Eval
import cats.data.State
import org.scalatest.{Matchers, OptionValues, WordSpecLike}

class SampleStateSpec extends WordSpecLike with Matchers with OptionValues {
  "Cats" should {
    "run the State" in {

      def divWithRemainder(d: Int): State[Int, Int] = State(n =>
        (n / d, n % d)
      )

      val div2WithRemainder: State[Int, Int] = divWithRemainder(2)


      def toBin(n: Int): Eval[String] = {
        //TBD: how to chain State in loops? Here we rely  on Eval API and recreate State from scratch rather than pass it via State API
        div2WithRemainder.run(n).flatMap {
          case (0, 0) => Eval.now("")
          case (num, rem) => toBin(num).map(rem.toString + _)
        }
      }

      println(s"div 5 2 =" + divWithRemainder(2).run(5).value)
      println(s"toBin 5 =" + toBin(5).value)
      println(s"toBin 13 =" + toBin(13).value)
    }
  }
}


