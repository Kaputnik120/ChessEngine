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
	
	public final int depth;
	
	public Game(PlayerType playerWhite, PlayerType playerBlack, int depth)
	{
		this.playerWhite = playerWhite;
		this.playerBlack = playerBlack;
		
		this.board = new Board();
		this.depth = depth;
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
	
	public boolean isGameEnded()
	{
		Color nextTurnColor = board.getNextTurnColor();
		DrawReason drawReason = board.getDrawReason(nextTurnColor);
		boolean isCheckmate = board.isCheckmate(nextTurnColor);
		if (drawReason != null || isCheckmate)
		{
			System.out.println("drawReason: " + drawReason);
			System.out.println("isCheckmate: " + isCheckmate);
		}
		return  drawReason != null || isCheckmate;
	}
	
	public void applyComputerMove()
	{
		Color nextTurnColor = board.getNextTurnColor();
		MoveCalculation moveCalculation = new MoveCalculation(board, nextTurnColor, depth);
		board.applyMove(moveCalculation.calculatedMove, true);
	}
}