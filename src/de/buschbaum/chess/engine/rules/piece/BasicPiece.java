package de.buschbaum.chess.engine.rules.piece;

import java.util.List;
import java.util.Objects;

import de.buschbaum.chess.engine.rules.Board;
import de.buschbaum.chess.engine.rules.Color;
import de.buschbaum.chess.engine.rules.Coordinate;
import de.buschbaum.chess.engine.rules.Field;
import de.buschbaum.chess.engine.rules.Move;

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
	
	@Override
	public boolean getMoved()
	{
		return moved;
	}
	
	@Override
	public void resetMoved()
	{
		moved = false;
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
	
	public boolean isEnemy(Piece piece)
	{
		return isEnemyStatic(piece, getColor());
	}
	
	public static boolean isEnemyStatic(Piece piece, Color color)
	{
		if (piece == null) return false;
		return piece.getColor().isOpposite(color);
	}
	
	/**
	 * Adds all moves in a straight line as given by x- and y-direction. The methods stops as soon as an enemy piece,
	 * a friendly piece or the end of the board is reached. 
	 */
	protected static void addLinearMoves(Board board, Field fromField, Coordinate coordinate, List<Move> moves, int xDirection, int yDirection, Color color)
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
				moves.add(new Move(board.getLastMove(), fromField, fieldBetween, color, null, fieldBetweenPiece));
				continue;
			}
			else if (isEnemyStatic(fieldBetweenPiece, color))
			{
				//Enemy piece field
				moves.add(new Move(board.getLastMove(), fromField, fieldBetween, color, null, fieldBetweenPiece));
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
	public int hashCode() 
	{
		return Objects.hash(color);
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicPiece other = (BasicPiece) obj;
		return color == other.color;
	}
}
