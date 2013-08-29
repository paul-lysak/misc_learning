import scala.reflect.macros.Context
import scala.language.experimental.macros

/**
 * @author Paul Lysak
 *         Date: 20.08.13
 *         Time: 22:44
 */
object MacroSample {
  def runMsgPrinter {
    val mp1 = new MsgPrinter(true)
    val mp2 = new MsgPrinter(false)

    mp1.print(buildMessage("first_message"))
    mp2.print(buildMessage("second_message"))

    mp1.conditionalPrint(buildMessage("first_messageC"))
    mp2.conditionalPrint(buildMessage("second_messageC"))
  }

  def buildMessage(msgHead: String): String = {
    println(s"buildMessage invoked for $msgHead")
    msgHead+"->msg_tail"
  }
}

class MsgPrinter(val enabled: Boolean) {

  def print(msg: String) {
    if(enabled)
      println("MSG: "+msg)
    else
      println("message_skipped")
  }

  def conditionalPrint(msg: String) = macro MacroContainer.condPrintImpl

}


