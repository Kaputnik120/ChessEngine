package de.buschbaum.chess.engine.model.piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.buschbaum.chess.engine.model.Board;
import de.buschbaum.chess.engine.model.Color;
import de.buschbaum.chess.engine.model.Coordinate;
import de.buschbaum.chess.engine.model.Field;
import de.buschbaum.chess.engine.model.Move;

public class Bishop extends BasicPiece implements Piece 
{
	public Bishop(Color color)
	{
		super(color);
	}

	@Override
	public List<Move> getAvailableMoves(Board board, Coordinate coordinate) 
	{
		Field fromField = board.getField(coordinate);
		
		List<Move> moves = new ArrayList<>();
		
		addMoves(board, fromField, coordinate, moves, 1, 1);
		addMoves(board, fromField, coordinate, moves, 1, -1);
		addMoves(board, fromField, coordinate, moves, -1, 1);
		addMoves(board, fromField, coordinate, moves, -1, -1);
		
		board.stripCheckMoves(moves, getColor());
		
		return moves;
	}

	private void addMoves(Board board, Field fromField, Coordinate coordinate, List<Move> moves, int xDirection, int yDirection)
	{
		for (int i = 1; i <= 7; i++)
		{
			int newX = coordinate.x + i * xDirection;
			int newY = coordinate.y + i * yDirection;
			if (newX < 0 || newX > 7 || newY < 0 || newY > 7) break;
			
			Field fieldBetween = board.fields[newX][newY];
			Piece fieldBetweenPiece = fieldBetween.piece;
			if (fieldBetweenPiece == null)
			{
				//Empty field
				moves.add(new Move(board.getLastMove(), fromField, fieldBetween, getColor(), null, fieldBetweenPiece));
				continue;
			}
			else if (isEnemy(fieldBetweenPiece))
			{
				//Enemy piece field
				moves.add(new Move(board.getLastMove(), fromField, fieldBetween, getColor(), null, fieldBetweenPiece));
				break;
			}
			else
			{
				//Friendly piece field
				break;
			}
		}
	}

	@Override
	public int getScoringValue() {
		return 3;
	}

	@Override
	public boolean isOffending(Board board, Coordinate from, Coordinate to) 
	{
		return Pieces.isOffendingBishop(board, from, to);
	}

	@Override
	public String getNotation()
	{
		return getNotation('b');
	}

	@Override
	public String toString()
	{
		return "Bishop [color=" + color + "]";
	}
}
