package de.buschbaum.chess.engine.calculation;

import java.util.List;

import de.buschbaum.chess.engine.rules.Board;
import de.buschbaum.chess.engine.rules.Color;
import de.buschbaum.chess.engine.rules.Move;

public class MoveCalculation
{
	public final Board board;
	public final Color color;
	
	public final int depth;
	
	public Move calculatedMove;
	
	public MoveCalculation(Board board, Color color, int depth)
	{
		this.board = board;
		this.depth = depth;
		this.color = color;
		
		calculateMove();
	}
	
	private void calculateMove()
	{
		//Returning random move for the beginning
		List<Move> availableMoves = board.getAvailableMoves(color);
		
		if (availableMoves.size() > 0)
		{
			calculatedMove = availableMoves.get(Math.max((int) Math.round(Math.random() * availableMoves.size()) - 1, 0));
		}
	}
}
