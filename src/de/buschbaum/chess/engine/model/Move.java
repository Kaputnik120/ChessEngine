package de.buschbaum.chess.engine.model;

import java.util.List;
import java.util.Objects;

import de.buschbaum.chess.engine.model.piece.Piece;

public class Move 
{
	public final Move lastMove;
	public final Field from;
	public final Field to;
	public final Color color;
	public final Piece promotion;
	
	public Piece capture;
	private double score;
	private List<Move> possibleFollowingMoves;

	/**
	 * Creates a complete in game move.
	 * @param lastMove May be null for the first move. Necessary for en-passent rule.
	 * @param from Not null
	 * @param to Not null
	 * @param color Not null
	 * @param promotion Is only unequal null if a pawn is promoted.
	 */
	public Move(Move lastMove, Field from, Field to, Color color, Piece promotion, Piece capture)
	{
		Objects.requireNonNull(from);
		Objects.requireNonNull(to);
		Objects.requireNonNull(color);
		
		this.lastMove = lastMove;
		this.from = from;
		this.to = to;
		this.color = color;
		this.promotion = promotion;
		this.capture = capture;
	}
	
	/**
	 * Creates a move for testing purposes. E.g. for creating a special field constellation. Moves created with this method are not valid for regular chess engine logic.
	 */
	public Move(Field from, Field to)
	{
		Objects.requireNonNull(from);
		Objects.requireNonNull(to);
		this.from = from;
		this.to = to;
		
		this.lastMove = null;
		this.color = null;
		this.promotion = null;
		this.capture = null;
	}

	public double getScore()
	{
		return score;
	}

	public void setScore(double score)
	{
		this.score = score;
	}

	public List<Move> getPossibleFollowingMoves()
	{
		return possibleFollowingMoves;
	}

	public void setPossibleFollowingMoves(List<Move> possibleFollowingMoves)
	{
		this.possibleFollowingMoves = possibleFollowingMoves;
	}

	@Override
	public String toString()
	{
		return "Move [from=" + from + ", to=" + to + ", color=" + color + ", promotion="
				+ promotion + "]";
	}
}
