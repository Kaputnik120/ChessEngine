package de.buschbaum.chess.engine.test;

import java.util.List;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

import de.buschbaum.chess.engine.model.Move;

@RunWith(JUnitPlatform.class)
@SelectClasses({AvailabeMovesTest.class, BasicTest.class, IsOffendingTest.class})
public class TestSuite
{
	public static boolean containsTargetCoordinateMove(int x, int y, List<Move> moves)
	{
		for (Move move : moves)
		{
			if (move.to.coordinate.x == x && move.to.coordinate.y == y)
			{
				return true;
			}
		}
		return false;
	}
}
