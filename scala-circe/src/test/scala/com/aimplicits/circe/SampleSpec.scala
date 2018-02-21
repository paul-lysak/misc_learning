package com.aimplicits.circe

import org.scalatest.{Matchers, OptionValues, WordSpecLike}
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import io.circe.optics.JsonPath._
import io.circe.generic.semiauto._

sealed trait Super1

case class Sub1(name: String) extends Super1

case class Sub2(count: Int) extends Super1

case class Sub3(name: String) extends Super1

object Super1 {
  implicit val encoder: Encoder[Super1] = deriveEncoder
  implicit val decoder: Decoder[Super1] = deriveDecoder
}

class SampleSpec extends WordSpecLike with Matchers with OptionValues {
  "Circe" should {
    "work with find" in {
      val arr1Ids = json.findAllByKey("arr1").flatMap(_.findAllByKey("id")).flatMap(_.asString)
//      ***** arr1Ids=List(1, 2, 3)
      println(s"***** arr1Ids=$arr1Ids")
    }

    "work with hcursor" in {
      val downArr1 = json.hcursor.downField("arr1")
      println(s"***** downArr1=${downArr1.focus.value}")

      val downArr1Id = downArr1.downArray.downField("id")
//      ***** downArr1Id="1" - only retrieves 1st element of the array
      println(s"***** downArr1Id=${downArr1Id.focus.value}")

      val allArr1Ids = downArr1
        .focus
        .flatMap(_.asArray)
        .toIterable.flatten //Option[Vector[Json]] => Iterable[Json]
        .flatMap(_.hcursor.downField("id").as[String].right.toOption)
//      ***** allArr1Ids=List(1, 2, 3)
      println(s"***** allArr1Ids=${allArr1Ids}")
    }

    "work with optics" in {
      val opticObj1Id = root.obj1.id.string
      val obj1Id = opticObj1Id.getOption(json).value
      println(s"***** obj1Id = $obj1Id")

      //Doesn't work - getOption returns null
//      val opticArr1Id = root.arr1.id.string
//      val arr1Id = opticArr1Id.getOption(json).value
//      println(s"***** arr1Id = $arr1Id")

      val opticArr1Id2 = root.arr1.each.id.string
      val arr1Id2 = opticArr1Id2.getAll(json)
      println(s"***** arr1Id2 = $arr1Id2")

    }

    "work with hierarchy encoding/decoding" in {
      val obj1: Super1 = Sub1("sub1")
      val obj2: Super1 = Sub2(100500)
      val obj3: Super1 = Sub3("sub3")

      val json1 = obj1.asJson
      //Uses nested object to distinguish subtypes:
//      ***** json1={
//        "Sub1" : {
//          "name" : "sub1"
//        }
//      }
      println(s"***** json1=$json1")
      val json2 = obj2.asJson
      println(s"***** json2=$json2")
      val json3 = obj3.asJson
      println(s"***** json3=$json3")


      val supDec1 = json1.as[Super1].right.toOption.value
      println(s"***** decoded sup1 = $supDec1")
      val supDec2 = json2.as[Super1].right.toOption.value
      println(s"***** decoded sup2 = $supDec2")
      val supDec3 = json3.as[Super1].right.toOption.value
      println(s"***** decoded sup3 = $supDec3")



    }
  }

  private lazy val json = parse(sampleJsonString).right.toOption.value

  private lazy val sampleJsonString =
    """
      |{
      |  "id": "rootObj",
      |  "arr1": [
      |    {"id": "1", "name": "a"},
      |    {"id": "2", "name": "b"},
      |    {"id": "3", "name": "c"}
      |  ],
      |  "obj1": {"id": "10", "name": "aa"}
      |}
    """.stripMargin
}


