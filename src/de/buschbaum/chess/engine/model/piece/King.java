package de.buschbaum.chess.engine.model.piece;

import java.util.List;
import java.util.Objects;

import de.buschbaum.chess.engine.model.Board;
import de.buschbaum.chess.engine.model.Color;
import de.buschbaum.chess.engine.model.Coordinate;
import de.buschbaum.chess.engine.model.Field;
import de.buschbaum.chess.engine.model.Move;

public class King extends BasicPiece implements Piece
{
	
	public King(Color color)
	{
		super(color);
	}

	@Override
	public List<Move> getAvailableMoves(Board board, Coordinate coordinate) {
		return null;
	}

	@Override
	public int getScoringValue() {
		//0 because the king always is not part of the calculation
		return 0;
	}

	@Override
	public boolean isOffending(Board board, Coordinate from, Coordinate to) 
	{
		if (Math.abs(from.x - to.x) > 1 || Math.abs(from.y - to.y) > 1) return false;
		
		return true;
	}
	
	@Override
	public String getNotation()
	{
		return getNotation('k');
	}

	@Override
	public String toString()
	{
		return "King [moved=" + moved + ", color=" + color + "]";
	}
}
