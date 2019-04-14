package de.buschbaum.chess.engine.model.piece;

import java.util.List;
import java.util.Objects;

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
		return null;
	}

	@Override
	public int getScoringValue() 
	{
		return 3;
	}

	@Override
	public boolean isOffending(Board board, Coordinate from, Coordinate to) 
	{
		Piece knight = board.fields[from.x][from.y].piece;
		Objects.requireNonNull(knight);
		Objects.requireNonNull(from);
		Objects.requireNonNull(to);
		
		int absX = Math.abs(from.x - to.x);
		int absY = Math.abs(from.y - to.y);
		if (absX > 2 || absY > 2) return false;
		if (absX < 2 && absY < 2) return false;
		if (absY == 2 && absX != 1) return false;
		if (absY == 1 && absX != 2) return false;
		
		Piece toPiece = board.getField(to).piece;
		if (toPiece != null && toPiece.getColor().equals(knight.getColor())) return false;
		
		return true;
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
