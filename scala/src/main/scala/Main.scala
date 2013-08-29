/**
 * @author Paul Lysak
 *         Date: 20.08.13
 *         Time: 22:37
 */
object Main {

  def main(args: Array[String]) {
    println("hi all")

//    MacroSample.runMsgPrinter

    val lc1 = new LoggerChild1
    val lc2 = new LoggerChild2
    lc1.doStuff1
    lc2.doStuff1
    lc2.doStuff2
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

class LoggerChild1 extends MyLogger {
  def doStuff1 =
    log("doStuff1 from LoggerChild1")
}

class LoggerChild2 extends LoggerChild1 {
  def doStuff2 =
    log("doStuff2 from LoggerChild2")
}
