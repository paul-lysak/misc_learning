package tictactoe

import org.testng.Assert
import org.testng.annotations.Test

import scala.util.Success

/**
 * User: Paul Lysak
 * Date: 11/11/14
 * Time: 8:05 PM
 */
class TicTacToeTest {

  @Test
  def testXWinOneRow(): Unit = {
    var b = Board()
    b = b.putX(Cell(1,1)).get
    Assert.assertEquals(b.checkWinner, None)
    b = b.putX(Cell(1,2)).get
    Assert.assertEquals(b.checkWinner, None)
    b = b.putX(Cell(1,3)).get
    println(b)
    Assert.assertEquals(b.checkWinner, Some("X"))
  }

  @Test
  def testOWinDiag(): Unit = {
    var b = Board()
    b = b.putO(Cell(1,1)).get
    Assert.assertEquals(b.checkWinner, None)
    b = b.putO(Cell(2,2)).get
    Assert.assertEquals(b.checkWinner, None)
    b = b.putO(Cell(3,3)).get
    println(b)
    Assert.assertEquals(b.checkWinner, Some("O"))
  }

  @Test
  def testGame(): Unit = {
    val game = new Game()
    Assert.assertEquals(game.turn("1,1"), Success(None))
    Assert.assertEquals(game.turn("2,1"), Success(None))
    Assert.assertEquals(game.turn("1,2"), Success(None))
    Assert.assertEquals(game.turn("2,2"), Success(None))
    Assert.assertEquals(game.turn("1,3"), Success(Some("X")))
  }

  @Test
  def testBatchGameSuccess(): Unit = {
    val game = new Game()
    val res = game.batchGame(
      """
        |1,1
        |1,2
        |2,2
        |3,2
        |3,3
      """.stripMargin)
    Assert.assertEquals(res, "X")
  }

  @Test
  def testBatchGameDraw(): Unit = {
    val game = new Game()
    val res = game.batchGame(
      """
        |1,1
        |1,2
        |2,2
        |3,2
      """.stripMargin)
    Assert.assertEquals(res, "DRAW")

  }


}
