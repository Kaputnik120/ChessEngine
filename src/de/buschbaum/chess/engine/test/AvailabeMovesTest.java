package de.buschbaum.chess.engine.test;


import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import de.buschbaum.chess.engine.model.Board;
import de.buschbaum.chess.engine.model.Coordinate;
import de.buschbaum.chess.engine.model.Field;
import de.buschbaum.chess.engine.model.Move;
import de.buschbaum.chess.engine.model.piece.Piece;

class AvailabeMovesTest
{

	@Test
	void knightAvailableMoves()
	{
		Board board = new Board();
		
		Field knightField = board.fields[1][0];
		List<Move> moves = knightField.getAvailableMoves(board);
		assertTrue(moves.size() == 2);
		assertTrue(TestSuite.containsTargetCoordinateMove(2, 2, moves));
		assertTrue(TestSuite.containsTargetCoordinateMove(0, 2, moves));
		
		board.applyMove(3, 1, 3, 3);
		board.applyMove(1, 0, 2, 2);
		board.applyMove(4, 6, 4, 4);
		board.applyMove(5, 7, 1, 3);
		moves = knightField.getAvailableMoves(board);
		assertTrue(moves.isEmpty());
	}
	
	@Test
	void bishopAvailableMoves()
	{
		Board board = new Board();
		Field bishopField = board.fields[2][7];
		
		List<Move> moves = bishopField.getAvailableMoves(board);
		assertTrue(moves.isEmpty());
		
		board.applyMove(3, 1, 3, 3);
		board.applyMove(1, 0, 2, 2);
		board.applyMove(4, 6, 4, 4);
		board.applyMove(5, 7, 1, 3);
		
		bishopField = board.fields[1][3];
		moves = bishopField.getAvailableMoves(board);
		assertTrue(TestSuite.containsTargetCoordinateMove(0, 4, moves));
		assertTrue(TestSuite.containsTargetCoordinateMove(0, 2, moves));
		assertTrue(TestSuite.containsTargetCoordinateMove(2, 4, moves));
		assertTrue(TestSuite.containsTargetCoordinateMove(3, 5, moves));
		assertTrue(TestSuite.containsTargetCoordinateMove(4, 6, moves));
		assertTrue(TestSuite.containsTargetCoordinateMove(5, 7, moves));
		assertTrue(TestSuite.containsTargetCoordinateMove(2, 2, moves));
		assertTrue(moves.size() == 7);
		
		board.reset();
		bishopField = board.fields[3][6];
		
		board.applyMove(3, 6, 3, 4);
		board.applyMove(2, 7, 3, 6);
		board.applyMove(3, 0, 2, 5);
		
		moves = bishopField.getAvailableMoves(board);
		assertTrue(TestSuite.containsTargetCoordinateMove(2, 5, moves));
		assertTrue(moves.size() == 1);
	}
}
