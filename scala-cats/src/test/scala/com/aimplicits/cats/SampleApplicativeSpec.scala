package com.aimplicits.cats

import cats.Applicative
import cats.data.{NonEmptyList, Validated, ValidatedNel}
import org.scalatest.{Matchers, OptionValues, WordSpecLike}
import cats.implicits._



class SampleApplicativeSpec extends WordSpecLike with Matchers with OptionValues {
  def mul(a: Int, b: Int): Int = a * b

  "Cats" should {
    "run the Applicative" in {

      println(s"mul Some(2) Some(3)="+Applicative[Option].map2(Some(2), Some(3))(mul))
      println(s"mul Some(2) None="+Applicative[Option].map2(Some(2), None)(mul))
      println(s"mul None Some(3)="+Applicative[Option].map2(None, Some(3))(mul))

      //lower-level example - how it uses Apply.ap - the core method of Apply/Applicative:
      val cmul: Int => Int => Int = (mul(_, _)).curried
      val cmul2: Option[Int => Int] = Some(2).map(cmul)
      println(s"cmul2  Some(3)="+Applicative[Option].ap(cmul2)(Some(3)))
    }

    //In order to have mapN working it's essential to have type alias for Validated with just 1 type parameter
    type MyValidation[T] = ValidatedNel[String, T]
    "run the Validated" in {

      def validateA(a: Int): MyValidation[Int] = {
        if(a > 0 && a < 10) a.validNel else "A out of interval".invalidNel
      }

      def validateB(b: Int): MyValidation[Int] = {
        if(b > 1 && b < 20) Validated.valid(b) else Validated.invalid(NonEmptyList("B out of interval", Nil))
      }

      val validatedMul1 = cats.syntax.apply.catsSyntaxTuple2Semigroupal((validateA(30), validateB(40))).mapN(mul)
      println("vmul1="+validatedMul1)
      val validatedMul2 = cats.syntax.apply.catsSyntaxTuple2Semigroupal((validateA(3), validateB(4))).mapN(mul)
      println("vmul2="+validatedMul2)
    }
  }
}

