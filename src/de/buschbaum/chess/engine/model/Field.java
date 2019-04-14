package de.buschbaum.chess.engine.model;

import java.util.ArrayList;
import java.util.List;

import de.buschbaum.chess.engine.model.piece.Piece;

public class Field {
	
	public final Coordinate coordinate;
	
	/**
	 * The piece which currently is located on this field. 
	 * Might be null if no piece is on the field.
	 */
	public Piece piece;

	/**
	 * Creates a new Field. 
	 * Remember that new instances of fields should only be created when the board is initialized.
	 * x and y are 0-based.
	 */
	public Field(int x, int y, Piece piece)
	{
		this.coordinate = new Coordinate(x, y);
		this.piece = piece;
		
	}
	
	/**
	 * Creates a new Field. 
	 * Remember that new instances of fields should only be created when the board is initialized.
	 */
	public Field(Coordinate coordinate, Piece piece)
	{
		this.coordinate = coordinate;
		this.piece = piece;
		
	}
	
	/**
	 * Returns the fieldName of the coordinate of the field. 
	 */
	public String getFieldName()
	{
		return coordinate.getFieldName();
	}
	
	/**
	 * Calculates the list of possible moves of the piece currently located on the field.
	 * Returns an empty list, if there's no piece on the field.
	 */
	public List<Move> getAvailableMoves(Board board)
	{
		if (piece == null) return new ArrayList<>();
		return piece.getAvailableMoves(board, coordinate);
	}

	public boolean isEmpty()
	{
		return piece == null;
	}

	@Override
	public String toString()
	{
		return "Field [coordinate=" + coordinate + ", piece=" + piece + "]";
	}
}
