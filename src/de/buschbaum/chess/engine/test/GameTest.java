package de.buschbaum.chess.engine.test;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import de.buschbaum.chess.engine.game.Game;
import de.buschbaum.chess.engine.game.GameResult;
import de.buschbaum.chess.engine.game.PlayerType;
import de.buschbaum.chess.engine.rules.Color;
import de.buschbaum.chess.engine.rules.DrawReason;

class GameTest
{

	private final static int MAX_POSSIBLE_CHESS_GAMES_MOVES = 7000;
	
//	@Test
	void testComputerGame() throws Exception
	{
		Map<String, Integer> results = new HashMap<>();
		results.put(DrawReason.FIFTY_MOVES_NO_CAPTURE.toString(), 0);
		results.put(DrawReason.INSUFFICIENT_MATERIAL.toString(), 0);
		results.put(DrawReason.STALEMATE.toString(), 0);
		results.put(DrawReason.THREEFOLD_REPITION.toString(), 0);
		results.put(Color.WHITE.toString(), 0);
		results.put(Color.BLACK.toString(), 0);
		
		for (int i = 0; i < 1_000; i++)
		{
			Game game = new Game(PlayerType.COMPUTER, PlayerType.COMPUTER, 0);
			int moves = 0;
			GameResult gameResult = null;
			while ((gameResult = game.getGameResult()) == null && moves < MAX_POSSIBLE_CHESS_GAMES_MOVES)
			{
				game.applyComputerMove();
				moves++;
			}
			
			if (gameResult.drawReason != null)
			{
				results.put(gameResult.drawReason.toString(), results.get(gameResult.drawReason.toString()) + 1);
			}
			else
			{
				results.put(gameResult.winner.toString(), results.get(gameResult.winner.toString()) + 1);
			}
			
			if (i % 100 == 0)
			{
				System.out.println(results);
			}
		}
	}
}
