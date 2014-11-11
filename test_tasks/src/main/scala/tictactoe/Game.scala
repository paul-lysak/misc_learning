package tictactoe

import scala.util.{Success, Failure, Try}

/**
 * User: Paul Lysak
 * Date: 11/11/14
 * Time: 8:43 PM
 */
class Game {
  def batchGame(turns: String) = {
    turns.lines.map(_.trim).filter(_.nonEmpty).map(line => turn(line)).
    collectFirst({
      case Failure(e) => "ERROR: "+e.getMessage
      case Success(Some(winner)) => winner
    }).getOrElse("DRAW")
  }

  def turn(turnStr: String): Try[Option[String]] = {
    Try {
      println("ts="+turnStr)
      val col :: row :: Nil = turnStr.trim.split(",").toList
      Cell(col.toInt, row.toInt)
    }.flatMap({cell =>
      if(evenTurn) board.putX(cell) else board.putO(cell)
    }).map({b =>
      board = b
      evenTurn = !evenTurn
      b.checkWinner
    })
  }

  private var board = Board()

  private var evenTurn = true

}
