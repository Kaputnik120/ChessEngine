package de.buschbaum.chess.engine.game;


import de.buschbaum.chess.engine.calculation.MoveCalculation;
import de.buschbaum.chess.engine.rules.Board;
import de.buschbaum.chess.engine.rules.Color;
import de.buschbaum.chess.engine.rules.DrawReason;

public class Game
{
	public final PlayerType playerWhite;
	public final PlayerType playerBlack;
	public final Board board;
	
	public Game(PlayerType playerWhite, PlayerType playerBlack)
	{
		this.playerWhite = playerWhite;
		this.playerBlack = playerBlack;
		
		this.board = new Board();
		
		if (PlayerType.COMPUTER.equals(playerWhite))
		{
			applyComputerMove();
		}
	}
	
	public boolean isComputerOnlyGame()
	{
		return playerWhite.equals(PlayerType.COMPUTER) && playerBlack.equals(PlayerType.COMPUTER);
	}
	
	public PlayerType getNextTurnPlayerType()
	{
		Color nextTurnColor = board.getNextTurnColor();
		
		if (Color.WHITE.equals(nextTurnColor)) return playerWhite;
		return playerBlack;
	}
	
	public boolean isComputersTurn()
	{
		return getNextTurnPlayerType().equals(PlayerType.COMPUTER);
	}
	
	public boolean isHumansTurn()
	{
		return getNextTurnPlayerType().equals(PlayerType.HUMAN);
	}
	
	/**
	 * Returns a GameResult if the game has ended. Else returns null.
	 */
	public GameResult getGameResult()
	{
		Color nextTurnColor = board.getNextTurnColor();
		DrawReason drawReason = board.getDrawReason(nextTurnColor);
		boolean isCheckmate = board.isCheckmate(nextTurnColor);
		
		if (drawReason == null && !isCheckmate)
		{
			return null;
		}
		
		if (drawReason != null)
		{
			return new GameResult(drawReason, null, board);
		}
		
		return new GameResult(null, Color.getOpposite(nextTurnColor), board);
	}
	
	public void applyComputerMove()
	{
		Color nextTurnColor = board.getNextTurnColor();
		MoveCalculation moveCalculation = new MoveCalculation(board, nextTurnColor);
		board.applyMove(moveCalculation.calculatedMove, true);
	}
}
