package de.buschbaum.chess.engine.model.piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.buschbaum.chess.engine.model.Board;
import de.buschbaum.chess.engine.model.Color;
import de.buschbaum.chess.engine.model.Coordinate;
import de.buschbaum.chess.engine.model.Field;
import de.buschbaum.chess.engine.model.Move;

public class Pawn extends BasicPiece implements Piece 
{
	public Pawn(Color color)
	{
		super(color);
	}

	@Override
	public List<Move> getAvailableMoves(Board board, Coordinate coordinate) 
	{
		List<Move> moves = new ArrayList<>();
		boolean isWhite = getColor().equals(Color.WHITE);
		int direction = isWhite ? 1 : -1;
		
		//simple move and potential promotion
		int simpleMoveTargetY = coordinate.y + direction;
		boolean isSimpleMoveTargetPromotionField = (isWhite && simpleMoveTargetY == 7 ) || (!isWhite && simpleMoveTargetY == 0);
		Field simpleMoveTargetField = board.fields[coordinate.x][simpleMoveTargetY];
 		if (simpleMoveTargetField.isEmpty())
		{
			if (isSimpleMoveTargetPromotionField)
			{
				moves.add(new Move(board.getLastMove(), board.getField(coordinate), simpleMoveTargetField, getColor(), new Queen(getColor()), null));
				moves.add(new Move(board.getLastMove(), board.getField(coordinate), simpleMoveTargetField, getColor(), new Knight(getColor()), null));
				moves.add(new Move(board.getLastMove(), board.getField(coordinate), simpleMoveTargetField, getColor(), new Rook(getColor()), null));
				moves.add(new Move(board.getLastMove(), board.getField(coordinate), simpleMoveTargetField, getColor(), new Bishop(getColor()), null));
			}
			else
			{
				moves.add(new Move(board.getLastMove(), board.getField(coordinate), simpleMoveTargetField, getColor(), null, null));
			}
			
			//initial two field move (is never promotion)
			if ((getColor().equals(Color.WHITE) && coordinate.y == 1) || (getColor().equals(Color.WHITE) && coordinate.y == 6))
			{
				int doubleMoveTargetY = simpleMoveTargetY + direction;
				if (doubleMoveTargetY <= 7 && doubleMoveTargetY >= 0)
				{
					Field doubleMoveTargetField = board.fields[coordinate.x][doubleMoveTargetY];
					if (doubleMoveTargetField.isEmpty())
					{
						moves.add(new Move(board.getLastMove(), board.getField(coordinate), doubleMoveTargetField, getColor(), null, null));
					}
				}
			}
		}
		
		//capture and opt. promotion
		int leftCaptureX = coordinate.x - 1;
		int rightCaptureX = coordinate.x + 1;
		
		if (leftCaptureX >= 0)
		{
			Field leftCaptureField = board.fields[leftCaptureX][simpleMoveTargetY];
			Piece leftCapturePiece = leftCaptureField.piece;
			if(isEnemy(leftCapturePiece))
			{
				if (isSimpleMoveTargetPromotionField)
				{
					moves.add(new Move(board.getLastMove(), board.getField(coordinate), leftCaptureField, getColor(), new Queen(getColor()), leftCapturePiece));
					moves.add(new Move(board.getLastMove(), board.getField(coordinate), leftCaptureField, getColor(), new Knight(getColor()), leftCapturePiece));
					moves.add(new Move(board.getLastMove(), board.getField(coordinate), leftCaptureField, getColor(), new Rook(getColor()), leftCapturePiece));
					moves.add(new Move(board.getLastMove(), board.getField(coordinate), leftCaptureField, getColor(), new Bishop(getColor()), leftCapturePiece));
				}
				else
				{
					moves.add(new Move(board.getLastMove(), board.getField(coordinate), leftCaptureField, getColor(), null, leftCapturePiece));
				}
			}
		}
		if (rightCaptureX <= 7)
		{
			Field rightCaptureField = board.fields[rightCaptureX][simpleMoveTargetY];
			Piece rightCapturePiece = rightCaptureField.piece;
			if(isEnemy(rightCapturePiece))
			{
				if (isSimpleMoveTargetPromotionField)
				{
					moves.add(new Move(board.getLastMove(), board.getField(coordinate), rightCaptureField, getColor(), new Queen(getColor()), rightCapturePiece));
					moves.add(new Move(board.getLastMove(), board.getField(coordinate), rightCaptureField, getColor(), new Knight(getColor()), rightCapturePiece));
					moves.add(new Move(board.getLastMove(), board.getField(coordinate), rightCaptureField, getColor(), new Rook(getColor()), rightCapturePiece));
					moves.add(new Move(board.getLastMove(), board.getField(coordinate), rightCaptureField, getColor(), new Bishop(getColor()), rightCapturePiece));
				}
				else
				{
					moves.add(new Move(board.getLastMove(), board.getField(coordinate), rightCaptureField, getColor(), null, rightCapturePiece));
				}
			}
		}
		
		//en passent (is never promotion)
		Move lastMove = board.getLastMove();
		if (lastMove != null)
		{
			Field lastToField = lastMove.to;
			Piece lastToPiece = lastToField.piece;
			if(lastToPiece instanceof Pawn)
			{
				Field lastFromField = lastMove.from;
				if(Math.abs(lastToField.coordinate.x - coordinate.x) == 1 
						&& lastFromField.coordinate.y == coordinate.y + 2 * direction
						&& lastToField.coordinate.y == coordinate.y)
				{
					moves.add(new Move(board.getLastMove(), board.getField(coordinate), board.fields[lastFromField.coordinate.x][coordinate.y + direction], getColor(), null, lastToPiece));
				}
			}
		}
		
		board.stripCheckMoves(moves, getColor());
		
		return moves;
	}

	@Override
	public int getScoringValue() {
		return 1;
	}

	@Override
	public boolean isOffending(Board board, Coordinate from, Coordinate to) 
	{
		int diffX = to.x - from.x;
		int diffY = to.y - from.y;
		int diffXAbs = Math.abs(diffX);
		if (diffXAbs > 1 || Math.abs(diffY) > 2) return false;
		
		boolean isWhite = getColor().equals(Color.WHITE);
		int direction = isWhite ? 1 : -1;
		
		//potential capture
		if (diffY == direction && diffXAbs == 1)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public String getNotation()
	{
		return getNotation('p');
	}

	@Override
	public String toString()
	{
		return "Pawn [color=" + color + "]";
	}
}
