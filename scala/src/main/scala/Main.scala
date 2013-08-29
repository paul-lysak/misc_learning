/**
 * @author Paul Lysak
 *         Date: 20.08.13
 *         Time: 22:37
 */
object Main {

  def main(args: Array[String]) {
    println("hi all")

    MacroSample.runMsgPrinter
  }
}


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
