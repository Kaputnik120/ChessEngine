package de.buschbaum.chess.engine.test;
import org.junit.jupiter.api.Test;

import de.buschbaum.chess.engine.game.Game;
import de.buschbaum.chess.engine.game.PlayerType;

class GameTest
{

	@Test
	void testComputerGame()
	{
		Game game = new Game(PlayerType.COMPUTER, PlayerType.COMPUTER, 0);
		while (!game.isGameEnded())
		{
			game.applyComputerMove();
			System.out.println(game.board);
			System.out.println(game.board.getAppliedMoves().size());
		}
	}
	
}
