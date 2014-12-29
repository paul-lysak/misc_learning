import org.specs2.mutable.Specification
import play.api.libs.iteratee.{Enumerator, Iteratee}
import rx.lang.scala.Observable

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.DurationInt


/**
 * Created by gefox on 29.12.14.
 */
class IterateeSpec extends Specification {
  val data = Seq[Int](10, 20, 30, 40, 50)

  "Iteratee" should {
    "do the work" in {
      println("hi all")

      val itPrint = Iteratee.foreach[Int](a => println("element="+a))
      val itSum = Iteratee.fold[Int, Int](0)(_ + _)

      val en = Enumerator(data: _*)
      val fp1 = en.run(itPrint)
      val fp2 = en.run(itPrint)
      val fp3 = en.run(itPrint)
      val fp = Future.sequence(Seq(fp1, fp2, fp3))
      Await.ready(fp, DurationInt(10).seconds)

      val fs1 = en.run(itSum)
      fs1.foreach(s => println("sum="+s))

      true must beTrue
    }
  }

  "Stream" should {
    "do the work" in {
      val str = data.toStream
      str.foreach(a => println("elStr="+a))

      val s = str.fold(0)(_ + _)
      println("sumStr="+s)

      true must beTrue
    }
  }

  "rxScala" should {
    "do the work" in {
        val o = Observable.from(data)
        val o1 = Observable.from(data)

        o.subscribe(a => println("rxItem="+a))

        val so = o1.foldLeft(0)(_ + _)

        so.subscribe(a => println("rxSum="+a))

        true must beTrue
    }
  }

}
