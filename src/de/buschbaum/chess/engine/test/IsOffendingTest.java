package de.buschbaum.chess.engine.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.buschbaum.chess.engine.model.Color;
import de.buschbaum.chess.engine.model.Field;
import de.buschbaum.chess.engine.model.piece.Knight;
import de.buschbaum.chess.engine.model.piece.Queen;

class IsOffendingTest
{
	@Test
	void offendingPawn()
	{
		UnitTestBoard board = new UnitTestBoard();
		
		Field pawnField = board.fields[3][1];
		assertTrue(pawnField.isOffending(board, 2, 2));
		assertTrue(pawnField.isOffending(board, 4, 2));
	}
	
	@Test
	void offendingBishop()
	{
		UnitTestBoard board = new UnitTestBoard();
		board.applyMove(4, 1, 4, 3);
		
		Field bishopField = board.fields[5][0];
		assertTrue(bishopField.isOffending(board, 4, 1));
		assertTrue(bishopField.isOffending(board, 3, 2));
		assertTrue(bishopField.isOffending(board, 2, 3));
		assertTrue(bishopField.isOffending(board, 1, 4));
		assertTrue(bishopField.isOffending(board, 0, 5));
		
		assertFalse(bishopField.isOffending(board, 5, 5));
		assertFalse(bishopField.isOffending(board, 1, 5));
		assertFalse(bishopField.isOffending(board, 3, 5));
		assertFalse(bishopField.isOffending(board, 7, 7));
		assertFalse(bishopField.isOffending(board, 0, 7));
		assertFalse(bishopField.isOffending(board, 7, 0));
		assertFalse(bishopField.isOffending(board, 3, 3));
		assertFalse(bishopField.isOffending(board, 2, 2));
		
		board.reset();
		board.applyMove(3, 1, 3, 2);
		board.applyMove(3, 6, 3, 4);
		board.applyMove(2, 0, 6, 4);
		board.applyMove(5, 6, 5, 5);
		
		bishopField = board.fields[6][4];
		assertTrue(bishopField.isOffending(board, 5, 5));
		assertTrue(bishopField.isOffending(board, 7, 3));
		assertTrue(bishopField.isOffending(board, 7, 5));
		assertTrue(bishopField.isOffending(board, 5, 3));
		assertTrue(bishopField.isOffending(board, 4, 2));
		assertTrue(bishopField.isOffending(board, 3, 1));
		assertTrue(bishopField.isOffending(board, 2, 0));
		
		assertFalse(bishopField.isOffending(board, 5, 6));
		assertFalse(bishopField.isOffending(board, 4, 6));
		assertFalse(bishopField.isOffending(board, 3, 3));
		assertFalse(bishopField.isOffending(board, 2, 1));
		assertFalse(bishopField.isOffending(board, 0, 7));
		assertFalse(bishopField.isOffending(board, 7, 4));
		assertFalse(bishopField.isOffending(board, 3, 7));
	}
	
	@Test
	void offendingKing()
	{
		UnitTestBoard board = new UnitTestBoard();
		
		Field kingField = board.fields[4][0];
		assertTrue(kingField.isOffending(board, 5, 1));
		assertTrue(kingField.isOffending(board, 5, 0));
		assertFalse(kingField.isOffending(board, 5, 5));
		assertFalse(kingField.isOffending(board, 7, 7));
		assertFalse(kingField.isOffending(board, 0, 7));
		assertFalse(kingField.isOffending(board, 7, 0));
		
		board.applyMove(4, 1, 4, 3);
		assertTrue(kingField.isOffending(board, 4, 1));
		
		board.reset();
		board.applyMove(4, 6, 4, 1);
		assertTrue(kingField.isOffending(board, 4, 1));
	}
	
	@Test
	void offendingKnight()
	{
		UnitTestBoard board = new UnitTestBoard();
		
		Field knightField = board.fields[1][0];
		assertTrue(knightField.isOffending(board, 2, 2));
		assertTrue(knightField.isOffending(board, 0, 2));
		
		assertFalse(knightField.isOffending(board, 1, 2));
		assertFalse(knightField.isOffending(board, 3, 2));
		assertFalse(knightField.isOffending(board, 3, 3));
		assertFalse(knightField.isOffending(board, 3, 4));
		
		board.applyMove(1, 7, 2, 2);
		knightField = board.fields[2][2];
		
		assertTrue(knightField.isOffending(board, 1, 0));
		assertTrue(knightField.isOffending(board, 1, 4));
		assertTrue(knightField.isOffending(board, 3, 4));
		assertTrue(knightField.isOffending(board, 3, 0));
		
		assertFalse(knightField.isOffending(board, 3, 3));
		assertFalse(knightField.isOffending(board, 3, 5));
		assertFalse(knightField.isOffending(board, 3, 2));
		
		board.fields[1][0].piece = new Knight(Color.BLACK);
		
		//Color shouldn't matter to isOffending method
		assertTrue(knightField.isOffending(board, 1, 0));
	}
	
	@Test
	void offendingRook()
	{
		UnitTestBoard board = new UnitTestBoard();
		
		Field rookField = board.fields[0][0];
		assertTrue(rookField.isOffending(board, 0, 1));
		assertTrue(rookField.isOffending(board, 1, 0));
		
		assertFalse(rookField.isOffending(board, 2, 0));
		assertFalse(rookField.isOffending(board, 2, 2));
		assertFalse(rookField.isOffending(board, 2, 1));
		assertFalse(rookField.isOffending(board, 0, 2));
		
		board.applyMove(0, 1, 0, 3);
		
		assertTrue(rookField.isOffending(board, 0, 1));
		assertTrue(rookField.isOffending(board, 0, 2));
		assertTrue(rookField.isOffending(board, 0, 3));
		
		assertFalse(rookField.isOffending(board, 0, 4));
		assertFalse(rookField.isOffending(board, 0, 5));
		assertFalse(rookField.isOffending(board, 0, 6));
		assertFalse(rookField.isOffending(board, 0, 7));
		assertFalse(rookField.isOffending(board, 7, 0));
	}	
	
	@Test
	void offendingQueen()
	{
		UnitTestBoard board = new UnitTestBoard();
		board.fields[0][0].piece = new Queen(Color.WHITE);
		
		Field queenField = board.fields[0][0];
		assertTrue(queenField.isOffending(board, 0, 1));
		assertTrue(queenField.isOffending(board, 1, 0));
		
		assertFalse(queenField.isOffending(board, 2, 0));
		assertFalse(queenField.isOffending(board, 2, 2));
		assertFalse(queenField.isOffending(board, 2, 1));
		assertFalse(queenField.isOffending(board, 0, 2));
		
		board.applyMove(0, 1, 0, 3);
		
		assertTrue(queenField.isOffending(board, 0, 1));
		assertTrue(queenField.isOffending(board, 0, 2));
		assertTrue(queenField.isOffending(board, 0, 3));
		
		assertFalse(queenField.isOffending(board, 0, 4));
		assertFalse(queenField.isOffending(board, 0, 5));
		assertFalse(queenField.isOffending(board, 0, 6));
		assertFalse(queenField.isOffending(board, 0, 7));
		assertFalse(queenField.isOffending(board, 7, 0));
		
		UnitTestBoard board2 = new UnitTestBoard();
		board2.fields[5][0].piece = new Queen(Color.WHITE);
		board2.applyMove(4, 1, 4, 3);
		
		Field queen2Field = board2.fields[5][0];
		assertTrue(queen2Field.isOffending(board2, 4, 1));
		assertTrue(queen2Field.isOffending(board2, 3, 2));
		assertTrue(queen2Field.isOffending(board2, 2, 3));
		assertTrue(queen2Field.isOffending(board2, 1, 4));
		assertTrue(queen2Field.isOffending(board2, 0, 5));
		
		assertFalse(queen2Field.isOffending(board2, 5, 5));
		assertFalse(queen2Field.isOffending(board2, 1, 5));
		assertFalse(queen2Field.isOffending(board2, 3, 5));
		assertFalse(queen2Field.isOffending(board2, 7, 7));
		assertFalse(queen2Field.isOffending(board2, 0, 7));
		assertFalse(queen2Field.isOffending(board2, 7, 0));
		assertFalse(queen2Field.isOffending(board2, 3, 3));
		assertFalse(queen2Field.isOffending(board2, 2, 2));
		
		board2.reset();
		board2.fields[2][0].piece = new Queen(Color.WHITE);
		
		board2.applyMove(3, 1, 3, 2);
		board2.applyMove(3, 6, 3, 4);
		board2.applyMove(2, 0, 6, 4);
		board2.applyMove(5, 6, 5, 5);
		queen2Field = board2.fields[6][4];
		
		assertTrue(queen2Field.isOffending(board2, 5, 5));
		assertTrue(queen2Field.isOffending(board2, 7, 3));
		assertTrue(queen2Field.isOffending(board2, 7, 5));
		assertTrue(queen2Field.isOffending(board2, 5, 3));
		assertTrue(queen2Field.isOffending(board2, 4, 2));
		assertTrue(queen2Field.isOffending(board2, 3, 1));
		assertTrue(queen2Field.isOffending(board2, 2, 0));
		assertTrue(queen2Field.isOffending(board2, 7, 4));
		assertTrue(queen2Field.isOffending(board2, 6, 5));
		assertTrue(queen2Field.isOffending(board2, 6, 4));
		assertTrue(queen2Field.isOffending(board2, 3, 4));
		assertTrue(queen2Field.isOffending(board2, 4, 4));
		assertTrue(queen2Field.isOffending(board2, 5, 4));
		
		assertFalse(queen2Field.isOffending(board2, 5, 6));
		assertFalse(queen2Field.isOffending(board2, 4, 6));
		assertFalse(queen2Field.isOffending(board2, 3, 3));
		assertFalse(queen2Field.isOffending(board2, 2, 1));
		assertFalse(queen2Field.isOffending(board2, 0, 7));
		assertFalse(queen2Field.isOffending(board2, 3, 7));
	}
}
