package de.buschbaum.chess.engine.model.piece;

import java.util.Objects;

import de.buschbaum.chess.engine.model.Board;
import de.buschbaum.chess.engine.model.Coordinate;
import de.buschbaum.chess.engine.model.Field;

public class Pieces
{

	/**
	 * @see Piece#isOffending(Board, Coordinate, Coordinate) 
	 */
	public static boolean ifOffendingBishop(Board board, Coordinate from, Coordinate to)
	{
		Objects.requireNonNull(board.fields[from.x][from.y].piece);
		Objects.requireNonNull(from);
		Objects.requireNonNull(to);
		
		if (!from.color.equals(to.color)) return false;
		if (from.x == to.x) return false;
		if (from.y == to.y) return false;
		
		int diffAbsX = Math.abs(from.x - to.x);
		if (diffAbsX != Math.abs(from.y - to.y)) return false;
		
		int maxSteps = diffAbsX -1; //Maximal die Felder dazwischen prüfen
		int xDirection = from.x > to.x ? -1 : 1;
		int yDirection = from.y > to.y ? -1 : 1;
		for (int i = 1; i <= maxSteps; i++)
		{
			Field fieldBetween = board.fields[from.x + i * xDirection][from.y + i * yDirection];
			if (!fieldBetween.isEmpty()) return false;
		}
		
		return true;
	}
	
	/**
	 * @see Piece#isOffending(Board, Coordinate, Coordinate) 
	 */
	public static boolean ifOffendingRook(Board board, Coordinate from, Coordinate to)
	{
		Objects.requireNonNull(board.fields[from.x][from.y].piece);
		Objects.requireNonNull(from);
		Objects.requireNonNull(to);
		
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
