package de.buschbaum.chess.engine.rules.piece;

import java.util.ArrayList;
import java.util.List;

import de.buschbaum.chess.engine.rules.Board;
import de.buschbaum.chess.engine.rules.Color;
import de.buschbaum.chess.engine.rules.Coordinate;
import de.buschbaum.chess.engine.rules.Field;
import de.buschbaum.chess.engine.rules.Move;

public class Rook extends BasicPiece implements Piece 
{
	public Rook(Color color)
	{
		super(color);
	}

	@Override
	public List<Move> getAvailableMoves(Board board, Coordinate coordinate) 
	{
		return getAvailableMovesStatic(board, coordinate, color);
	}
	
	public static List<Move> getAvailableMovesStatic(Board board, Coordinate coordinate, Color color) 
	{
		Field fromField = board.getField(coordinate);
		
		List<Move> moves = new ArrayList<>();
		
		addLinearMoves(board, fromField, coordinate, moves, 0, 1, color);
		addLinearMoves(board, fromField, coordinate, moves, 0, -1, color);
		addLinearMoves(board, fromField, coordinate, moves, 1, 0, color);
		addLinearMoves(board, fromField, coordinate, moves, -1, 0, color);
		
		board.stripCheckMoves(moves,color);
		
		return moves;
	}
	
	@Override
	public int getScoringValue() 
	{
		return 5;
	}

	@Override
	public boolean isOffending(Board board, Coordinate from, Coordinate to) 
	{
		return isOffendingStatic(board, from, to);
	}

	@Override
	public String getNotation()
	{
		return getNotation('r');
	}

	@Override
	public String toString()
	{
		return "Rook [moved=" + moved + ", color=" + color + "]";
	}
	
	/**
	 * @see Piece#isOffending(Board, Coordinate, Coordinate) 
	 */
	public static boolean isOffendingStatic(Board board, Coordinate from, Coordinate to)
	{
		if (from.x != to.x && from.y != to.y ) return false;
		
		int diffAbsX = Math.abs(from.x - to.x);
		int diffAbsY = Math.abs(from.y - to.y);
		boolean xAxis = diffAbsX != 0;
		
		int direction;
		if (xAxis)
		{
			direction = from.x > to.x ? -1 : 1;
		}
		else
		{
			direction = from.y > to.y ? -1 : 1;
		}
		
		int maxSteps = xAxis ? diffAbsX - 1 : diffAbsY - 1;
		for (int i = 1; i <= maxSteps; i++)
		{
			Field fieldBetween;
			if (xAxis)
			{
				fieldBetween = board.fields[from.x + i * direction][from.y];
			}
			else
			{
				fieldBetween = board.fields[from.x][from.y + i * direction];
			}
			if (!fieldBetween.isEmpty()) return false;
		}
		
		return true;
	}
}
