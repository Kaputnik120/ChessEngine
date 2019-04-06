package de.buschbaum.chess.engine.model;

import java.util.List;

public class Move 
{
	public final Move lastMove;
	public final Field from;
	public final Field to;
	public final Color color;
	
	private double score;
	private List<Move> possibleFollowingMoves;
	
	public Move(Move lastMove, Field from, Field to, Color color)
	{
		this.lastMove = lastMove;
		this.from = from;
		this.to = to;
		this.color = color;
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
}
