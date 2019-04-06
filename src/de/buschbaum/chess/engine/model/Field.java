package de.buschbaum.chess.engine.model;

import java.util.ArrayList;
import java.util.List;

import de.buschbaum.chess.engine.model.piece.Piece;

public class Field {
	
	public final short x;
	public final short y;
	public final Piece piece;
	public final Color color;

	/**
	 * Creates a new Field.
	 * x and y are 0-based.
	 */
	public Field(short x, short y, Piece piece)
	{
		super();
		this.x = x;
		this.y = y;
		this.piece = piece;
		this.color = (x % 2 == 1) && (y % 2 == 0) ? Color.WHITE : Color.BLACK; 
		
	}

	public String getFieldName()
	{
		char[] chars = new char[2];
		chars[0] = (char) (x + 65);
		chars[1] = Character.forDigit(y + 1, 10);
		return new String(chars);
	}
	
	public List<Move> getAvailableMoves(Board board)
	{
		if (piece == null) return new ArrayList<>();
		return piece.getAvailableMoves(board, this);
	}
}
