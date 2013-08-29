import scala.reflect.macros.Context
import scala.language.experimental.macros

/**
 * @author Paul Lysak
 *         Date: 20.08.13
 *         Time: 22:44
 */

class MsgPrinter(val enabled: Boolean) {

  def print(msg: String) {
    if(enabled)
      println("MSG: "+msg)
    else
      println("message_skipped")
  }

  def conditionalPrint(msg: String) = macro MacroContainer.condPrintImpl

}


