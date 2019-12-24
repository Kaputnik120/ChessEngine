package de.buschbaum.chess.engine.test;

import java.util.List;
import java.util.Map;

import de.buschbaum.chess.engine.rules.Board;
import de.buschbaum.chess.engine.rules.Color;
import de.buschbaum.chess.engine.rules.Field;
import de.buschbaum.chess.engine.rules.Move;
import de.buschbaum.chess.engine.rules.piece.King;

public class UnitTestBoard extends Board
{
	/**
	 * Shortcut for applying a move.
	 * Designed for testing - no promotion or lastMoves are added to the created moves!
	 * Always sets Moved status to true. Never sets promotion values.
	 */
	public void applyMove(int fromX, int fromY, int toX, int toY)
	{
		applyMove(new Move(fields[fromX][fromY], fields[toX][toY]), true);
	}
	
	/**
	 * Sets an empty field array with only the white and the black king set.
	 * Should be used for testing and therefore is not performance optimized.
	 */
	public void resetWithKingOnly()
	{
		super.reset();
		if (fields == null)
		{
			fields = new Field[8][8];
		}
		for (int x = 0; x <= 7; x++)
		{
			for (int y = 0; y <= 7; y++)
			{
				if (x == 4 && y == 0)
				{
					King king = new King(Color.WHITE);
					fields[x][y] = new Field(x, y, king);
				}
				else if (x == 4 && y == 7)
				{
					King king = new King(Color.BLACK);
					fields[x][y] = new Field(x, y, king);
				}
				else
				{
					fields[x][y] = new Field(x, y, null);
				}
			}	
		}
	}
	
	public void clear()
	{
		super.reset();
		if (fields == null)
		{
			fields = new Field[8][8];
		}
		for (int x = 0; x <= 7; x++)
		{
			for (int y = 0; y <= 7; y++)
			{
				fields[x][y] = new Field(x, y, null);
			}	
		}
	}
	
	public void reset()
	{
		super.reset();
	}
	
	public Map<Integer, Integer> getPositions()
	{
		return positions;
	}
	
	public static boolean containsTargetCoordinateMove(int x, int y, List<Move> moves)
	{
		for (Move move : moves)
		{
			if (move.to.coordinate.x == x && move.to.coordinate.y == y)
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean containsTargetCoordinateMovePromotion(int x, int y, Class<?> pieceClass, List<Move> moves)
	{
		for (Move move : moves)
		{
			if (move.to.coordinate.x == x && move.to.coordinate.y == y && pieceClass.isInstance(move.promotion))
			{
				return true;
			}
		}
		return false;
	}
}
