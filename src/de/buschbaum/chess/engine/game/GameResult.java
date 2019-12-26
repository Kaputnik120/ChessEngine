package de.buschbaum.chess.engine.game;

import de.buschbaum.chess.engine.rules.Board;
import de.buschbaum.chess.engine.rules.Color;
import de.buschbaum.chess.engine.rules.DrawReason;

public class GameResult
{
	public final DrawReason drawReason;
	public final Color winner;
	public final Board board;
	
	public GameResult(DrawReason drawReason, Color winner, Board board)
	{
		this.drawReason = drawReason;
		this.winner = winner;
		this.board = board;
	}
}
