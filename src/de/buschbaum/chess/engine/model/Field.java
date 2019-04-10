package de.buschbaum.chess.engine.model;

import java.util.ArrayList;
import java.util.List;

import de.buschbaum.chess.engine.model.piece.Piece;

public class Field {
	
	public final Coordinate coordinate;
	private Piece piece;

	/**
	 * Creates a new Field.
	 * x and y are 0-based.
	 */
	public Field(short x, short y, Piece piece)
	{
		this.coordinate = new Coordinate(x, y);
		this.piece = piece;
		
	}
	
	public Field(Coordinate coordinate, Piece piece)
	{
		this.coordinate = coordinate;
		this.piece = piece;
		
	}

	public String getFieldName()
	{
		return coordinate.getFieldName();
	}
	
	public List<Move> getAvailableMoves(Board board)
	{
		if (piece == null) return new ArrayList<>();
		return piece.getAvailableMoves(board, coordinate);
	}

	public void setPiece(Piece piece)
	{
		this.piece = piece;
	}
	
	public boolean isEmpty()
	{
		return piece == null;
	}
}
