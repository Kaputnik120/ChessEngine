package de.buschbaum.chess.engine.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.buschbaum.chess.engine.model.piece.Bishop;
import de.buschbaum.chess.engine.model.piece.King;
import de.buschbaum.chess.engine.model.piece.Knight;
import de.buschbaum.chess.engine.model.piece.Pawn;
import de.buschbaum.chess.engine.model.piece.Piece;
import de.buschbaum.chess.engine.model.piece.Queen;
import de.buschbaum.chess.engine.model.piece.Rook;

public class Board 
{
	/**
	 * The array of board fields always contains of the same field instances 
	 * Different states of play are reflected by setting different pieces to the fields.
	 */
	public Field[][] fields = null;
	
	private List<Move> appliedMoves = new ArrayList<>();
	
	/**
	 * Counts how many times a given position arised. Used for threefold repition.
	 */
	private Map<Integer, Integer> positions = new HashMap<>();
	
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
	
	@Override
	public int hashCode()
	{
		StringBuilder hashCodeBuilder = new StringBuilder(); 
		for (int y = 7; y >= 0; y--)
		{
			for (int x = 0; x <= 7; x++)
			{
				Field field = fields[x][y];
				if (field.piece == null)
				{
					hashCodeBuilder.append("xx");
				}
				else
				{
					hashCodeBuilder.append(field.piece.getNotation());
				}
			}
		}
		
		return hashCodeBuilder.toString().hashCode();
	}
	
	public boolean isDraw(Color color)
	{
		if (isFiftyMovesWithoutCapture()) return true;
		if (isThreefoldRepition()) return true;
		if (isInsufficientMaterial()) return true;
		
		for (int y = 7; y >= 0; y--)
		{
			for (int x = 0; x <= 7; x++)
			{
				Field field = fields[x][y];
				Piece piece = field.piece;
				if (piece != null && piece.getColor().equals(color))
				{
					if (piece.getAvailableMoves(this, field.coordinate).size() > 0)
					{
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	public boolean isFiftyMovesWithoutCapture()
	{
		if (appliedMoves.size() < 50) return false;
		
		int i = 0;
		for (Move move : appliedMoves)
		{
			i++;
			if (i >= 50) return true;
			if (move.capture != null) return false;
		}
		return false;
	}
	
	public boolean isInsufficientMaterial()
	{
		int wBishops = 0;
		Color wBishopColor = null;
		int wKnights = 0;
		
		int bBishops = 0;
		Color bBishopColor = null;
		int bKnights = 0;
		
		for (int y = 7; y >= 0; y--)
		{
			for (int x = 0; x <= 7; x++)
			{
				Field field = fields[x][y];
				Piece piece = field.piece;
				if (piece == null) continue;
				if (piece instanceof Queen || piece instanceof Pawn || piece instanceof Rook)
				{
					return false;
				}
				else if (piece instanceof Bishop)
				{
					if (Color.WHITE.equals(piece.getColor()))
					{
						wBishops++;
						wBishopColor = field.coordinate.color;
					}
					else
					{
						bBishops++;
						bBishopColor = field.coordinate.color;
					}
				}
				else if (piece instanceof Knight)
				{
					if (Color.WHITE.equals(piece.getColor()))
					{
						wKnights++;
					}
					else
					{
						bKnights++;
					}
				}
			}
		}
		
		if (wKnights == 0 && bKnights == 0 && wBishops == 0 && bBishops == 0)
		{
			return true;
		}
		if (wKnights == 1 ^ bKnights == 1 ^ wBishops == 1 ^ bBishops == 1)
		{
			return true;
		}
		if (wKnights == 0 && bKnights == 0 && wBishops == 1 && bBishops == 1)
		{
			if (wBishopColor.equals(bBishopColor))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isThreefoldRepition()
	{
		Integer positionCount = positions.get(this.hashCode());
		if (positionCount == null) return false;
		return positionCount >= 3;
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
	 * like validating a check situation and not for finally applied moves.
	 * The rules for rochade and en passent moves are applied.
	 */
	public void applyMove(Move move, boolean setMoved)
	{
		boolean isNextMoveRochade = isNextMoveRochade(move); //Check before any changes to the board are made
		boolean isNextMoveEnPassentCapture = !isNextMoveRochade && isNextMoveEnPassentCapture(move);
		
		Field to = move.to;
		Field from = move.from;
		
		Objects.requireNonNull(from.piece);
		if (isNextMoveEnPassentCapture)
		{
			int enPassentX = to.coordinate.x;
			int enPassentY = from.coordinate.y;
			fields[enPassentX][enPassentY].piece = null;
		}
		else
		{
			move.capture = to.piece;
		}
		to.piece = from.piece;
		from.piece = null;
		if (setMoved) to.piece.setMoved();
		appliedMoves.add(move);
		
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
		
		incrementPositionCount();
	}
	
	private void incrementPositionCount()
	{
		Integer hashCode = this.hashCode();
		Integer currentCount = positions.get(hashCode);
		if (currentCount == null)
		{
			currentCount = 1;
		}
		else
		{
			currentCount++;
		}
		positions.put(hashCode, currentCount);
	}
	
	private void decrementPositionCount()
	{
		Integer hashCode = this.hashCode();
		Integer currentCount = positions.get(hashCode);
		if (currentCount == null)
		{
			return;
		}
		else if (currentCount == 1)
		{
			positions.remove(hashCode);
		}
		else
		{
			currentCount--;
			positions.put(hashCode, currentCount);
		}
	}
	
	/**
	 * Checks if the move given would be a en passent move if applied to the current board. Doesn't work for past moves!
	 */
	public boolean isNextMoveEnPassentCapture(Move move)
	{
		if (!(move.capture instanceof Pawn)) return false;
		if (Math.abs(move.from.coordinate.x - move.to.coordinate.x) != 1) return false;
		if (Math.abs(move.from.coordinate.y - move.to.coordinate.y) != 1) return false;
		if (!move.to.isEmpty()) return false;
		return true;
	}
	
	/**
	 * Checks if the last move was an en passent move
	 */
	public boolean isLastMoveEnPassentCapture()
	{
		Move lastMove = getLastMove();
		if (lastMove == null) return false;
		if (appliedMoves.size() - 2 < 0) return false;
		Move beforeLastMove = appliedMoves.get(appliedMoves.size() - 2);
		if (beforeLastMove == null) return false;
		
		if (!(lastMove.capture instanceof Pawn)) return false;
		if (Math.abs(lastMove.from.coordinate.x - lastMove.to.coordinate.x) != 1) return false;
		if (Math.abs(lastMove.from.coordinate.y - lastMove.to.coordinate.y) != 1) return false;

		//Move before must have been a double move
		if (Math.abs(beforeLastMove.from.coordinate.y - beforeLastMove.to.coordinate.y) != 2) return false;
		//Pawn must have been removed
		if (fields[beforeLastMove.to.coordinate.x][beforeLastMove.to.coordinate.y].piece != null) return false;
		//Before last move's target must be on same y coordinate as from coordinate of last move
		if (beforeLastMove.to.coordinate.y != lastMove.from.coordinate.y) return false;
		//Before last move's target must be only one x coordinate away from coordinate of last move
		if (Math.abs(beforeLastMove.to.coordinate.x - lastMove.from.coordinate.x) != 1) return false;
		
		return true;
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
		
		boolean lastMoveRochade = isLastMoveRochade(); //Check before any changes to the board are made
		boolean isLastMoveEnPassentCapture = !lastMoveRochade && isLastMoveEnPassentCapture();

		Move lastMove = getLastMove();
		if (lastMoveRochade)
		{
			lastMove.from.piece = lastMove.to.piece;
			lastMove.to.piece = lastMove.capture;
			
			Field kingFieldTo = lastMove.to;
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
		else if (isLastMoveEnPassentCapture)
		{
			lastMove.from.piece = lastMove.to.piece;
			lastMove.to.piece = null;
			
			Move beforeLastMove = appliedMoves.get(appliedMoves.size() - 2);
			beforeLastMove.to.piece = lastMove.capture;
		}
		else
		{
			lastMove.from.piece = lastMove.to.piece;
			lastMove.to.piece = lastMove.capture;
		}
		
		decrementPositionCount();
		appliedMoves.remove(appliedMoves.size() - 1);
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
		applyMove(new Move(fields[fromX][fromY], fields[toX][toY]), true);
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
	 * Sets an empty field array with only the white and the black king set.
	 * Should be used for testing and therefore is not performance optimized.
	 */
	public void resetWithKingOnly()
	{
		reset();
		if (fields == null)
		{
			fields = new Field[8][8];
		}
		for (int x = 0; x <= 7; x++)
		{
			for (int y = 0; y <= 7; y++)
			{
				if (x == 4 && y == 0)
				{
					King king = new King(Color.WHITE);
					fields[x][y] = new Field(x, y, king);
				}
				else if (x == 4 && y == 7)
				{
					King king = new King(Color.BLACK);
					fields[x][y] = new Field(x, y, king);
				}
				else
				{
					fields[x][y] = new Field(x, y, null);
				}
			}	
		}
	}
	
	/**
	 * Resets the board to start 
	 */
	public void reset()
	{
		appliedMoves.clear();
		positions.clear();
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
	
	public List<Move> getAppliedMoves()
	{
		return appliedMoves;
	}
}
