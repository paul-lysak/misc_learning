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

  def logImpl(c: Context {type PrefixType = MyLogger})(msg: c.Expr[String]) = { //: c.Expr[Unit] = {
    import c.universe._
    import c.universe.Flag._

    val t = c.enclosingClass.symbol
//    println("compile type="+t)

//    c.Expr[Unit](Apply(Ident(newTermName("println")), List(Literal(Constant(t.toString+": ")))))
//    val msgAst = Apply(Select(Ident(x), newTermName("$plus")), List(Literal(Constant(2))))
    val msgAst = Apply(Select(Literal(Constant(t.toString)), newTermName("$plus")), List(msg.tree))
    c.Expr[Unit](Apply(Ident(newTermName("println")), List(msgAst)))
   }

    def logImpl2(c: Context {type PrefixType = MyLogger})(msg: c.Expr[String]) = {
    val t = c.enclosingClass.symbol
    println("compile type="+t)
//    c.prefix
//    val t = c.prefix.staticType.typeSymbol.asClass
    c.universe.reify({

//      val t = c.unreifyTree(c.prefix.tree)
//      println("actual type="+t);
//      println("actual type2="+c.enclosingClass.symbol);
      println("class="+c.prefix.splice.getClass().getName);
      println(s"log: ${msg.splice}");
    })
    }
}
