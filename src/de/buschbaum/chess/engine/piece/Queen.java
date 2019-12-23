package de.buschbaum.chess.engine.piece;

import java.util.List;

import de.buschbaum.chess.engine.Board;
import de.buschbaum.chess.engine.Color;
import de.buschbaum.chess.engine.Coordinate;
import de.buschbaum.chess.engine.Field;
import de.buschbaum.chess.engine.Move;

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
	public String toString()
	{
		return "Queen [color=" + color + "]";
	}
}
