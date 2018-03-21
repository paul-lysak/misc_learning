package com.aimplicits.cats

import cats.Id
import cats.arrow.Arrow
import cats.data.{Kleisli, Reader}
import org.scalatest.{Matchers, OptionValues, WordSpecLike}
import cats.implicits._

class SampleKleisliSpec extends WordSpecLike with Matchers with OptionValues {
  "Cats" should {
    "run the Kleisli" in {

      //Composition
      val parse = Kleisli((s: String) => if (s.matches("-?[0-9]+")) Some(s.toInt) else None)

      val reciprocal = Kleisli((i: Int) => if (i != 0) Some(1.0 / i) else None)

      val parseAndReciprocal = reciprocal.compose(parse)


      println("1/2="+parseAndReciprocal.run("2")) //Some(0.5)
      println("1/two="+parseAndReciprocal.run("two")) //None


      //Flat mapping
      val parseAndEven = parse.flatMap(i => Kleisli((s: String) => if (i % 2 == 0) Some(s"$i was even") else None))

      println("2 PaE="+parseAndEven.run("2")) //Some
      println("3 PaE="+parseAndEven.run("3")) //None
      println("two PaE="+parseAndEven.run("two")) //None

      //Collections
//      val repeat = Kleisli((i: Int) => if (i != 0) Seq(i, i) else Seq.empty)
      val repeat = Kleisli((i: Int) => if (i != 0) List(i, i) else List.empty)
//      val parseAndRepeat = repeat.compose(parse) //can't compose - different result container type

//      val parseToSeq = parse.mapK[List]((i: Option[_]) => i.toList) //doesn't work
      val parseToSeq = Kleisli((s: String) => if (s.matches("-?[0-9]+")) List(s.toInt) else List.empty)
      //Doesn't work with Seq - Cats doesn't have typeclass for Seq and will not have it in nearest future: https://github.com/typelevel/cats/issues/1222
//      val parseToSeq = Kleisli((s: String) => if (s.matches("-?[0-9]+")) Seq(s.toInt) else Seq.empty)

      val parseAndRepeat = repeat.compose(parseToSeq)

      println("2 rep="+parseAndRepeat.run("2")) //List(2,2)
      println("two rep="+parseAndRepeat.run("two")) //empty
      println("0 rep="+parseAndRepeat.run("0")) //empty
    }

    "run the Reader" in {
      //Reader is essentially Kleisli with Id
      val r1 = Reader((s: String) => s.toInt)
      val r2 = r1.flatMap(i => Reader((s: String) => s"$s transformed to $i"))
      println("r2(1)=" + r2.run("1"))
    }

    "run the Arrow" in {
      val parse: String => Int = _.toInt
      val triple: Int => Int = _ * 3

      val parseAndTriple = parse >>> triple
      val pAt1 = Arrow[Function1].lift(parseAndTriple)
//      val pAt2: Arrow[Function1] = Arrow[Function1].lift(parseAndTriple)

      println("PaT(2)="+ parseAndTriple("2"))
      println("PaT1(2)="+ pAt1("2"))
    }
  }
}


