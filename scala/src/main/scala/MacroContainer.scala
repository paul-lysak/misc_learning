import scala.reflect.macros.Context
import scala.language.experimental.macros

/**
 * @author Paul Lysak
 *         Date: 29.08.13
 *         Time: 21:07
 */
object MacroContainer {
  def condPrintImpl(c: Context {type PrefixType = MsgPrinter})(msg: c.Expr[String]) =
    c.universe.reify(
      if(c.prefix.splice.enabled) {
        println("macro - print")
        c.prefix.splice.print(msg.splice)
      } else {
        println("macro - skip")
      }
    )
}
