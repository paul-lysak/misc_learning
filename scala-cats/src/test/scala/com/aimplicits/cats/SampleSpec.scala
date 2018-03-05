package com.aimplicits.cats

import org.scalatest.{Matchers, OptionValues, WordSpecLike}

class SampleSpec extends WordSpecLike with Matchers with OptionValues {
  "Cats" should {
    "do the job" in {
      println(s"hi there")
    }
  }
}


