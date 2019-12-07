package de.buschbaum.chess.engine.model.piece;

import java.util.List;

import de.buschbaum.chess.engine.model.Board;
import de.buschbaum.chess.engine.model.Color;
import de.buschbaum.chess.engine.model.Coordinate;
import de.buschbaum.chess.engine.model.Field;
import de.buschbaum.chess.engine.model.Move;

public class Queen extends BasicPiece implements Piece
{
	public Queen(Color color)
	{
		super(color);
	}

	@Override
	public List<Move> getAvailableMoves(Board board, Coordinate coordinate) 
	{
		List<Move> moves = Bishop.getAvailableMovesStatic(board, coordinate, getColor());
		moves.addAll(Rook.getAvailableMovesStatic(board, coordinate, getColor()));
		return moves;
	}

	@Override
	public int getScoringValue() {
		return 9;
	}

	@Override
	public boolean isOffending(Board board, Coordinate from, Coordinate to) {
		return Bishop.isOffendingStatic(board, from, to) || Rook.isOffendingStatic(board, from, to);
	}

	@Override
	public String getNotation()
	{
		return getNotation('q');
	}

	@Override
	public int getNumber()
	{
		return 5 + (color.equals(Color.BLACK) ? 7 : 0);
	}
	
	@Override
	public String toString()
	{
		return "Queen [color=" + color + "]";
	}
}
