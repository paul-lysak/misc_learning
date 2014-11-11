package tictactoe

import scala.util.{Success, Failure, Try}

/**
 * User: Paul Lysak
 * Date: 11/11/14
 * Time: 8:06 PM
 */
case class Board(xcells: Set[Cell] = Set(), ocells: Set[Cell] = Set()) {
  def putX(cell: Cell): Try[Board] = checkRange(cell).flatMap(checkBusy).map({cell => this.copy(xcells = xcells + cell)})

  def putO(cell: Cell): Try[Board] = checkRange(cell).flatMap(checkBusy).map({cell => this.copy(ocells = ocells + cell)})

  def checkWinner: Option[String] = {
    if(findWinner(xcells).isDefined)
      Some("X")
    else if(findWinner(ocells).isDefined)
      Some("O")
    else
      None
  }

  private def checkRange(cell: Cell): Try[Cell] =
    if(cell.row >= 1 && cell.row <= 3 && cell.col >= 1 && cell.col <= 3)
      Success(cell)
    else
      Failure(new IllegalArgumentException(s"Cell $cell falls beyond gameboard limits"))


  private def checkBusy(cell: Cell): Try[Cell] =
    if(xcells.contains(cell) || ocells.contains(cell))
      Failure(new IllegalArgumentException(s"Cell $cell already busy"))
    else
      Success(cell)

  private def findWinner(cells: Set[Cell]): Option[(Cell, Cell, Cell)] = {
    //TODO refactor this crap
    (for(a <- cells; b <- cells; c <- cells if(a != b && a != c && b != c &&
      (
        a.col == b.col && b.col == c.col ||
        a.row == b.row && b.row == c.row ||
        a.row - a.col == 0 && b.row - b.col == 0 && c.row - c.col == 0 ||
        a.row == 1 && a.col == 3 && b.row == 2 && b.col == 2 && c.row == 3 && c.col == 1
        )
      ))
    yield {
      (a, b, c)
    }
    ).toList.headOption
  }


}

case class Cell(row: Int, col: Int)
