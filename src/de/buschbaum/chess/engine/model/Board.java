package de.buschbaum.chess.engine.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.PrimitiveIterator.OfDouble;

import de.buschbaum.chess.engine.model.piece.Bishop;
import de.buschbaum.chess.engine.model.piece.King;
import de.buschbaum.chess.engine.model.piece.Knight;
import de.buschbaum.chess.engine.model.piece.Pawn;
import de.buschbaum.chess.engine.model.piece.Piece;
import de.buschbaum.chess.engine.model.piece.Queen;
import de.buschbaum.chess.engine.model.piece.Rook;

public class Board {

	/**
	 * The array of board fields always contains of the same field instances 
	 * Different states of play are reflected by setting different pieces to the fields.
	 */
	public Field[][] fields = null;
	
	private List<Move> appliedMoves = new ArrayList<>();
	private List<Move> calculatedMoves = new ArrayList<>();
	
	/**
	 * Creates a board with the initial fields.
	 */
	public Board()
	{
		reset();
	}
	
	/**
	 * Create a board with the given fields. 
	 * This constructor should be used for testing. 
	 */
	public Board(Field[][] fields)
	{
		this.fields = fields;
	}
	
	public Field getField(Coordinate coordinate)
	{
		return fields[coordinate.x][coordinate.y];
	}
	
	/**
	 * Applies the given moves.
	 * No Rule checks are made.
	 * If the piece on the from field is null, an exception is thrown.
	 */
	public void applyMove(Move move)
	{
		Field to = move.to;
		Field from = move.from;
		
		Objects.requireNonNull(from.piece);
		to.piece = from.piece;
		from.piece = null;
		to.piece.setMoved();
		
		appliedMoves.add(move);
	}
	
	/**
	 * Shortcut for applying a move.
	 * Designed for testing - no promotion or lastMoves are added to the created moves! 
	 */
	public void applyMove(int fromX, int fromY, int toX, int toY)
	{
		applyMove(new Move(fields[fromX][fromY], fields[toX][toY]));
	}

	/**
	 * Returns the last move or null if there are no moves applied, yet.
	 */
	public Move getLastMove()
	{
		if (appliedMoves == null || appliedMoves.isEmpty()) return null;
		return appliedMoves.get(appliedMoves.size() - 1);
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		for (int y = 7; y >= 0; y--)
		{
			for (int x = 0; x <= 7; x++)
			{
				Piece piece = fields[x][y].piece;
				if (piece != null)
				{
					sb.append(piece.getNotation());
				}
				else
				{
					sb.append("--");
				}
				sb.append(" ");
			}	
			sb.append(" ");
			sb.append(y);
			sb.append("\n");
		}
		
		sb.append("\n");
		
		for (int x = 0; x <= 7; x++)
		{
			sb.append(x);
			sb.append("  ");
		}
		sb.append("\n");
		
		return sb.toString();
	}
	
	/**
	 * Resets the board to start 
	 */
	public void reset()
	{
		if (fields == null)
		{
			fields = new Field[8][8];
		}
		for (int x = 0; x <= 7; x++)
		{
			for (int y = 0; y <= 7; y++)
			{
				if (x == 0 && y == 0 || x == 7 && y == 0)
				{
					Rook rook = new Rook(Color.WHITE);
					fields[x][y] = new Field(x, y, rook);
				}
				else if (x == 0 && y == 7 || x == 7 && y == 7)
				{
					Rook rook = new Rook(Color.BLACK);
					fields[x][y] = new Field(x, y, rook);
				}
				else if (x == 1 && y == 0 || x == 6 && y == 0)
				{
					Knight knight = new Knight(Color.WHITE);
					fields[x][y] = new Field(x, y, knight);
				}
				else if (x == 1 && y == 7 || x == 6 && y == 7)
				{
					Knight knight = new Knight(Color.BLACK);
					fields[x][y] = new Field(x, y, knight);
				}
				else if (x == 2 && y == 0 || x == 5 && y == 0)
				{
					Bishop bishop = new Bishop(Color.WHITE);
					fields[x][y] = new Field(x, y, bishop);
				}
				else if (x == 2 && y == 7 || x == 5 && y == 7)
				{
					Bishop bishop = new Bishop(Color.BLACK);
					fields[x][y] = new Field(x, y, bishop);
				}
				else if (x == 3 && y == 0)
				{
					Queen queen = new Queen(Color.WHITE);
					fields[x][y] = new Field(x, y, queen);
				}
				else if (x == 3 && y == 7)
				{
					Queen queen = new Queen(Color.BLACK);
					fields[x][y] = new Field(x, y, queen);
				}
				else if (x == 4 && y == 0)
				{
					King king = new King(Color.WHITE);
					fields[x][y] = new Field(x, y, king);
				}
				else if (x == 4 && y == 7)
				{
					King king = new King(Color.BLACK);
					fields[x][y] = new Field(x, y, king);
				}
				else if (y == 1)
				{
					Pawn pawn = new Pawn(Color.WHITE);
					fields[x][y] = new Field(x, y, pawn);
				}
				else if (y == 6)
				{
					Pawn pawn = new Pawn(Color.BLACK);
					fields[x][y] = new Field(x, y, pawn);
				}
				else
				{
					fields[x][y] = new Field(x, y, null);
				}
			}	
		}
	}
	
	public Field getKing(Color color)
	{
		for (int y = 7; y >= 0; y--)
		{
			for (int x = 0; x <= 7; x++)
			{
				Field field = fields[x][y];
				Piece piece = field.piece;
				if ((piece instanceof King) && piece.getColor().equals(color))
				{
					return field;
				}
			}
		}
		return null;
	}
	
	/**
	 * Is any piece of Color color offending Field field?
	 */
	public boolean isAnyPieceOffendingField(Color color, Field field)
	{
		for (int y = 7; y >= 0; y--)
		{
			for (int x = 0; x <= 7; x++)
			{
				Field pieceField = fields[x][y];
				Piece piece = pieceField.piece;
				if (piece == null || !piece.getColor().equals(color)) continue;
				boolean isOffending = piece.isOffending(this, pieceField, field);
				if (isOffending) return true;
			}
		}
		return false;
	}
	
	public boolean isCheck(Color color)
	{
		Field kingsField = getKing(color);
		System.out.println(kingsField);
		
		
		
		
		return false;
	}
}
