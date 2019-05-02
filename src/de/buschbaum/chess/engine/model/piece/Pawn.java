package de.buschbaum.chess.engine.model.piece;

import java.util.List;
import java.util.Objects;

import de.buschbaum.chess.engine.model.Board;
import de.buschbaum.chess.engine.model.Color;
import de.buschbaum.chess.engine.model.Coordinate;
import de.buschbaum.chess.engine.model.Field;
import de.buschbaum.chess.engine.model.Move;

public class Pawn extends BasicPiece implements Piece 
{
	public Pawn(Color color)
	{
		super(color);
	}

	@Override
	public List<Move> getAvailableMoves(Board board, Coordinate coordinate) {
		/*
		 * int diffX = to.x - from.x;
		int diffY = to.y - from.y;
		int diffXAbs = Math.abs(diffX);
		if (diffXAbs > 1 || Math.abs(diffY) > 2) return false;
		
		boolean isWhite = pawn.getColor().equals(Color.WHITE);
		int direction = isWhite ? 1 : -1;
		
		//simple move
		if (diffY == direction && diffX == 0)
		{
			Piece pieceInPawnsWay = board.fields[to.x][to.y].piece;
			if (pieceInPawnsWay == null)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		//initial two field move
		if (diffY == 2 * direction && diffX == 0)
		{
			if ((isWhite && from.y == 1) || (!isWhite && from.y == 6))
			{
				Piece pieceInPawnsWay = board.fields[from.x][from.y + 1 * direction].piece;
				if (pieceInPawnsWay == null)
				{
					return true;
				}
			}
			else
			{
				return false;
			}
		}

		//potential capture
		if (diffY == direction && diffXAbs == 1)
		{
			return true;
		}
		 * //capture
		if (diffY == direction && diffX == 1)
		{
			Piece pieceInPawnsWay = board.fields[to.x][to.y].piece;
			if (pieceInPawnsWay != null)
			{
				return true;
			}
			else
			{
				//check en passent rule
				Move lastMove = board.getLastMove();
				if (lastMove == null) return false;
				
				Field lastToField = lastMove.to;
				if(!(lastToField.piece instanceof Pawn)) return false;
				
				Field lastFromField = lastMove.from;
				if(Math.abs(lastToField.coordinate.x - from.x) == 1 && lastFromField.coordinate.y == from.y + 2 * direction)
				{
					return true;
				}
			}
		}
		 */
		
		return null;
	}

	@Override
	public int getScoringValue() {
		return 1;
	}

	@Override
	public boolean isOffending(Board board, Coordinate from, Coordinate to) 
	{
		int diffX = to.x - from.x;
		int diffY = to.y - from.y;
		int diffXAbs = Math.abs(diffX);
		if (diffXAbs > 1 || Math.abs(diffY) > 2) return false;
		
		boolean isWhite = getColor().equals(Color.WHITE);
		int direction = isWhite ? 1 : -1;
		
		//potential capture
		if (diffY == direction && diffXAbs == 1)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public String getNotation()
	{
		return getNotation('p');
	}

	@Override
	public String toString()
	{
		return "Pawn [color=" + color + "]";
	}
}
