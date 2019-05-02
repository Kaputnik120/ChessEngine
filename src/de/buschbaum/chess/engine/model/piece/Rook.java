package de.buschbaum.chess.engine.model.piece;

import java.util.List;
import de.buschbaum.chess.engine.model.Board;
import de.buschbaum.chess.engine.model.Color;
import de.buschbaum.chess.engine.model.Coordinate;
import de.buschbaum.chess.engine.model.Move;

public class Rook extends BasicPiece implements Piece 
{
	public Rook(Color color)
	{
		super(color);
	}

	@Override
	public List<Move> getAvailableMoves(Board board, Coordinate coordinate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getScoringValue() 
	{
		return 5;
	}

	@Override
	public boolean isOffending(Board board, Coordinate from, Coordinate to) 
	{
		return Pieces.isOffendingRook(board, from, to);
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
}
