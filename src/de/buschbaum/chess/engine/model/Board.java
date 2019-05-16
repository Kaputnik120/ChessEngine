package de.buschbaum.chess.engine.model;

import java.util.ArrayList;
import java.util.Iterator;
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
	 * Applies the given move.
	 * No Rule checks are made.
	 * If the piece on the from field is null, an exception is thrown.
	 * If setMoved=true the moved piece gets the moved status. setMoved=false should only be used for calculations
	 *  like validating a check situation and not for finally applied moves.
	 */
	public void applyMove(Move move, boolean setMoved)
	{
		boolean isNextMoveRochade = isNextMoveRochade(move); //Check before any changes to the board are made
		Field to = move.to;
		Field from = move.from;
		
		Objects.requireNonNull(from.piece);
		move.capture = to.piece;
		to.piece = from.piece;
		from.piece = null;
		if (setMoved) to.piece.setMoved();
		appliedMoves.add(move);
		
		//Check rochade and move rook as defined by rules
		if (isNextMoveRochade)
		{
			if (to.coordinate.x == 6)
			{
				Field rookRochadeField = fields[5][to.coordinate.y];
				Field rookBeforeRochadeField = fields[7][to.coordinate.y];
				rookRochadeField.piece = rookBeforeRochadeField.piece;
				rookBeforeRochadeField.piece = null;
				if (setMoved) rookRochadeField.piece.setMoved();
			}
			else if (to.coordinate.x == 2)
			{
				Field rookRochadeField = fields[3][to.coordinate.y];
				Field rookBeforeRochadeField = fields[0][to.coordinate.y];
				rookRochadeField.piece = rookBeforeRochadeField.piece;
				rookBeforeRochadeField.piece = null;
				if (setMoved) rookRochadeField.piece.setMoved();
			}
		}
	}
	
	/**
	 * Checks if the last move was a rochade move.
	 */
	public boolean isLastMoveRochade()
	{
		Move lastMove = getLastMove();
		if (lastMove == null) return false;
		Piece king = lastMove.to.piece;
		if (!(king instanceof King)) return false;
		
		int y = king.getColor().equals(Color.WHITE) ? 0 : 7;
		if (lastMove.to.coordinate.y != y) return false;
		
		int xDiffAbs = Math.abs(lastMove.to.coordinate.x - lastMove.from.coordinate.x); 
		return xDiffAbs == 2;
	}
	
	/**
	 * Checks if the move given would be a rochade if applied to the current board. Doesn't work for past moves!
	 */
	public boolean isNextMoveRochade(Move move)
	{
		if (!(move.from.piece instanceof King)) return false;
		int xDiffAbs = Math.abs(move.to.coordinate.x - move.from.coordinate.x); 
		return xDiffAbs == 2;
	}
	
	/**
	 * Unapplies the last move made. If applicable restores the captured piece
	 */
	public void unapplyMove()
	{
		if (appliedMoves == null || appliedMoves.isEmpty()) return;
		
		boolean lastMoveRochade = isLastMoveRochade(); //Check before any moves to the board are made
		
		Move lastMove = getLastMove();
		lastMove.from.piece = lastMove.to.piece;
		lastMove.to.piece = lastMove.capture;
		
		if (lastMoveRochade)
		{
			Field kingFieldFrom = lastMove.from;
			Field kingFieldTo = lastMove.to;
			Piece king = kingFieldFrom.piece;
			int direction = king.getColor().equals(Color.WHITE) ? -1 : 1;
			
			if (kingFieldTo.coordinate.x == 6)
			{
				Field rookRochadeField = fields[5][kingFieldTo.coordinate.y];
				Field rookBeforeRochadeField = fields[7][kingFieldTo.coordinate.y];
				rookBeforeRochadeField.piece = rookRochadeField.piece;
				rookRochadeField.piece = null;
			}
			else if (kingFieldTo.coordinate.x == 2)
			{
				Field rookRochadeField = fields[3][kingFieldTo.coordinate.y];
				Field rookBeforeRochadeField = fields[0][kingFieldTo.coordinate.y];
				rookBeforeRochadeField.piece = rookRochadeField.piece;
				rookRochadeField.piece = null;
			}	
		}
	}
	
	/**
	 * Creates a move instance or null if one the following rules is violated:
	 * <ul>
	 * <li>The coordinates are within bounds of the board</li>
	 * <li>The target field is empty or the piece on the field is of enemy color</li>
	 * </ul>
	 * The move is automatically set to not being a promotion move.
	 */
	public Move createValidMove(Coordinate fromCoordinate, int toX, int toY)
	{
		if (toX < 0 || toX > 7 || toY < 0 || toY > 7) return null;
		
		Field toField = fields[toX][toY];
		Piece toPiece = toField.piece;
		
		Field fromField = getField(fromCoordinate);
		Piece fromPiece = fromField.piece;
		
		if (toPiece != null && !fromPiece.isEnemy(toPiece)) return null;
		
		return new Move(getLastMove(), fromField, toField, fromPiece.getColor(), null, toPiece);
	}
	
	/**
	 * Removes all moves that end in check for the given color. 
	 */
	public void stripCheckMoves(List<Move> moves, Color color)
	{
		Iterator<Move> moveIterator = moves.iterator();
		while (moveIterator.hasNext())
		{
			Move move = moveIterator.next();
			applyMove(move, false);
			if (isCheck(color)) 
			{
				moveIterator.remove();
			}
			unapplyMove();
		}
	}
	
	/**
	 * Shortcut for applying a move.
	 * Designed for testing - no promotion or lastMoves are added to the created moves!
	 * Always sets Moved status to true. Never sets promotion values.
	 */
	public void applyMove(int fromX, int fromY, int toX, int toY)
	{
		applyMove(new Move(fields[fromX][fromY], fields[toX][toY]), false);
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
				boolean isOffending = pieceField.isOffending(this, field);
				if (isOffending) return true;
			}
		}
		return false;
	}
	
	public boolean isCheck(Color color)
	{
		Field kingsField = getKing(color);
		return isAnyPieceOffendingField(Color.getOpposite(color), kingsField);
	}
}
