package de.buschbaum.chess.engine.test;
import org.junit.jupiter.api.Test;

import de.buschbaum.chess.engine.game.Game;
import de.buschbaum.chess.engine.game.PlayerType;

class GameTest
{

	private final static int MAX_POSSIBLE_CHESS_GAMES_MOVES = 7000;
	
	@Test
	void testComputerGame() throws Exception
	{
		for (int i = 0; i < 10000; i++)
		{
			Game game = new Game(PlayerType.COMPUTER, PlayerType.COMPUTER, 0);
			int moves = 0;
			while (!game.isGameEnded() && moves < MAX_POSSIBLE_CHESS_GAMES_MOVES)
			{
				game.applyComputerMove();
				moves++;
			}
			System.out.println("Game " + i + " -  Board after Move " + game.board.getAppliedMoves().size() + ":");
			System.out.println(game.board);
			
//			System.out.println("Waiting to start next game");
//			Thread.sleep(000l);
		}
	}
}
