package de.buschbaum.chess.engine.model.piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.buschbaum.chess.engine.model.Board;
import de.buschbaum.chess.engine.model.Color;
import de.buschbaum.chess.engine.model.Coordinate;
import de.buschbaum.chess.engine.model.Field;
import de.buschbaum.chess.engine.model.Move;

public class King extends BasicPiece implements Piece
{
	
	public King(Color color)
	{
		super(color);
	}

	@Override
	public List<Move> getAvailableMoves(Board board, Coordinate coordinate) {
		
		int x = coordinate.x;
		int y = coordinate.y;
		
		List<Move> moves = new ArrayList<>();
		
		//basic Moves
		addPossibleMove(board, coordinate, x, y - 1, moves);
		addPossibleMove(board, coordinate, x, y + 1, moves);
		addPossibleMove(board, coordinate, x + 1, y, moves);
		addPossibleMove(board, coordinate, x + 1, y + 1, moves);
		addPossibleMove(board, coordinate, x + 1, y - 1, moves);
		addPossibleMove(board, coordinate, x - 1, y, moves);
		addPossibleMove(board, coordinate, x - 1, y + 1, moves);
		addPossibleMove(board, coordinate, x - 1, y - 1, moves);
		
		board.stripCheckMoves(moves, getColor());
		
		//Rochades
		if (getMoved() == true) return moves;
		if (board.isCheck(getColor())) return moves;
		int rochadeY = getColor().equals(Color.WHITE) ? 0 : 7;
		if (coordinate.y != rochadeY) return moves;
		
		//long rochade
		Piece rookLong = board.fields[0][rochadeY].piece;
		//Check colors, pieces and moved status
		if ((rookLong instanceof Rook) && rookLong.getColor().equals(getColor()) && !rookLong.getMoved())
		{
			//Check for pieces in the way
			if (board.fields[1][rochadeY].piece == null && board.fields[2][rochadeY].piece == null && board.fields[3][rochadeY].piece == null)
			{
				//Check for pieces offending rochade fields
				if (!isOpponentOffendingField(board, 1, rochadeY) && !isOpponentOffendingField(board, 2, rochadeY)) //3 is allowed to be offended by rules!
				{
					moves.add(new Move(board.getLastMove(), board.getField(coordinate), board.fields[2][rochadeY], getColor(), null, null));
				}
			}
		}
		
		//short rochade
		Piece rookShort = board.fields[7][rochadeY].piece;
		if ((rookShort instanceof Rook) && rookShort.getColor().equals(getColor()) && !rookShort.getMoved())
		{
			//Check for pieces in the way
			if (board.fields[5][rochadeY].piece == null && board.fields[6][rochadeY].piece == null)
			{
				//Check for pieces offending rochade fields
				if (!isOpponentOffendingField(board, 5, rochadeY) && !isOpponentOffendingField(board, 6, rochadeY))
				{
					moves.add(new Move(board.getLastMove(), board.getField(coordinate), board.fields[6][rochadeY], getColor(), null, null));
				}
			}
		}
		
		return moves;
	}
	
	private boolean isOpponentOffendingField(Board board, int x, int y)
	{
		return board.isAnyPieceOffendingField(Color.getOpposite(getColor()), board.fields[x][y]);
	}
	
	private void addPossibleMove(Board board, Coordinate coordinate, int toX, int toY, List<Move> moves)
	{
		Move move = board.createValidMove(coordinate, toX, toY);
		if (move != null) moves.add(move);
	}

	@Override
	public int getScoringValue() {
		//0 because the king never part of the calculation
		return 0;
	}

	@Override
	public boolean isOffending(Board board, Coordinate from, Coordinate to) 
	{
		if (Math.abs(from.x - to.x) > 1 || Math.abs(from.y - to.y) > 1) return false;
		
		return true;
	}
	
	@Override
	public String getNotation()
	{
		return getNotation('k');
	}

	@Override
	public String toString()
	{
		return "King [moved=" + moved + ", color=" + color + "]";
	}
}
