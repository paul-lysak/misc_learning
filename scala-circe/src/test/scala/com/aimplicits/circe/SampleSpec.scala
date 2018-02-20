package com.aimplicits.circe

import org.scalatest.{Matchers, OptionValues, WordSpecLike}
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

class SampleSpec extends WordSpecLike with Matchers with OptionValues {
  "Circe" should {
    "do the job" in {

      val json = parse(sampleJsonString).right.toOption.value
      println(s"***** json=$json")

      val downArr1 = json.hcursor.downField("arr1")
      println(s"***** downArr1=${downArr1.focus.value}")
      val downArr1Id = downArr1.downArray.downField("id")
//      ***** downArr1Id="1" - only retrieves 1st element of the array
      println(s"***** downArr1Id=${downArr1Id.focus.value}")

    }
  }

  //TODO Optics

  private val sampleJsonString =
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

