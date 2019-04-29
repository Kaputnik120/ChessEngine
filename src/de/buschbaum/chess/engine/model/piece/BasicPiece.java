package de.buschbaum.chess.engine.model.piece;

import de.buschbaum.chess.engine.model.Board;
import de.buschbaum.chess.engine.model.Color;
import de.buschbaum.chess.engine.model.Coordinate;
import de.buschbaum.chess.engine.model.Field;

/**
 * @author Uli
 *
 */
public abstract class BasicPiece implements Piece
{
	public boolean moved;
	protected final Color color;
	
	public BasicPiece(Color color)
	{
		this.color = color;
		this.moved = false;
	}

	@Override
	public void setMoved()
	{
		moved = true;
	}
	
	protected String getNotation(char pieceShortNotation)
	{
		char[] notation = new char[2];
		notation[0] = color.notation;
		notation[1] = pieceShortNotation;
		return new String(notation);
	}

	public Color getColor()
	{
		return color;
	}
	
	public boolean isOffending(Board board, int fromX, int fromY, int toX, int toY)
	{
		return isOffending(board, new Coordinate(fromX, fromY), new Coordinate(toX, toY));
	}
	
	public boolean isOffending(Board board, Field fromField, Field toField)
	{
		return isOffending(board, fromField.coordinate, toField.coordinate);
	}
}
