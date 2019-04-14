package de.buschbaum.chess.engine.model.piece;

import java.util.List;

import de.buschbaum.chess.engine.model.Board;
import de.buschbaum.chess.engine.model.Color;
import de.buschbaum.chess.engine.model.Coordinate;
import de.buschbaum.chess.engine.model.Field;
import de.buschbaum.chess.engine.model.Move;

public class Knight extends BasicPiece implements Piece 
{
	public Knight(Color color)
	{
		super(color);
	}

	@Override
	public List<Move> getAvailableMoves(Board board, Coordinate coordinate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getScoringValue() {
		return 3;
	}

	@Override
	public boolean isOffending(Board board, Coordinate from, Coordinate to) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getNotation()
	{
		return getNotation('n');
	}

	@Override
	public String toString()
	{
		return "Knight [color=" + color + "]";
	}
}
