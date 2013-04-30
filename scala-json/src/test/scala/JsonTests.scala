import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.specs2.mutable.Specification

/**
 * User: Paul Lysak
 * Date: 4/30/13
 * Time: 12:46 PM
 */
class JsonTests extends Specification {
  val str1 =
    """
      |{"name": "Vasya", "surname": "Pupkin"}
    """.stripMargin

  val mapper = {
    val m = new ObjectMapper
    m.registerModule(DefaultScalaModule)
    m
  }

  "It" should {
    "do the trick" in {
      println("hiall")

      val v1 = mapper.readValue(str1, classOf[java.util.Map[String, String]])
      println("v1="+v1)

      val v2 = mapper.readValue(str1, classOf[Person])
      println("v2="+v2)
    }
  }
}

case class Person(name: String, surname: String)