package de.buschbaum.chess.engine.test.rules;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.buschbaum.chess.engine.model.Board;
import de.buschbaum.chess.engine.model.Color;
import de.buschbaum.chess.engine.model.piece.Knight;
import de.buschbaum.chess.engine.model.piece.Piece;
import de.buschbaum.chess.engine.model.piece.Queen;

class IsOffendingTest
{
	@Test
	void offendingPawn()
	{
		Board board = new Board();
		
		Piece pawn = board.fields[3][1].piece;
		assertTrue(pawn.isOffending(board, 3, 1, 2, 2));
		assertTrue(pawn.isOffending(board, 3, 1, 4, 2));
	}
	
	@Test
	void offendingBishop()
	{
		Board board = new Board();
		board.applyMove(4, 1, 4, 3);
		
		Piece bishop = board.fields[5][0].piece;
		assertTrue(bishop.isOffending(board, 5, 0, 4, 1));
		assertTrue(bishop.isOffending(board, 5, 0, 3, 2));
		assertTrue(bishop.isOffending(board, 5, 0, 2, 3));
		assertTrue(bishop.isOffending(board, 5, 0, 1, 4));
		assertTrue(bishop.isOffending(board, 5, 0, 0, 5));
		
		assertFalse(bishop.isOffending(board, 5, 0, 5, 5));
		assertFalse(bishop.isOffending(board, 5, 0, 1, 5));
		assertFalse(bishop.isOffending(board, 5, 0, 3, 5));
		assertFalse(bishop.isOffending(board, 5, 0, 7, 7));
		assertFalse(bishop.isOffending(board, 5, 0, 0, 7));
		assertFalse(bishop.isOffending(board, 5, 0, 7, 0));
		assertFalse(bishop.isOffending(board, 5, 0, 3, 3));
		assertFalse(bishop.isOffending(board, 5, 0, 2, 2));
		
		board.reset();
		board.applyMove(3, 1, 3, 2);
		board.applyMove(3, 6, 3, 4);
		board.applyMove(2, 0, 6, 4);
		board.applyMove(5, 6, 5, 5);
		
		bishop = board.fields[6][4].piece;
		assertTrue(bishop.isOffending(board, 6, 4, 5, 5));
		assertTrue(bishop.isOffending(board, 6, 4, 7, 3));
		assertTrue(bishop.isOffending(board, 6, 4, 7, 5));
		assertTrue(bishop.isOffending(board, 6, 4, 5, 3));
		assertTrue(bishop.isOffending(board, 6, 4, 4, 2));
		assertTrue(bishop.isOffending(board, 6, 4, 3, 1));
		assertTrue(bishop.isOffending(board, 6, 4, 2, 0));
		
		assertFalse(bishop.isOffending(board, 6, 4, 5, 6));
		assertFalse(bishop.isOffending(board, 6, 4, 4, 6));
		assertFalse(bishop.isOffending(board, 6, 4, 3, 3));
		assertFalse(bishop.isOffending(board, 6, 4, 2, 1));
		assertFalse(bishop.isOffending(board, 6, 4, 0, 7));
		assertFalse(bishop.isOffending(board, 6, 4, 7, 4));
		assertFalse(bishop.isOffending(board, 6, 4, 3, 7));
	}
	
	@Test
	void offendingKing()
	{
		Board board = new Board();
		
		Piece king = board.fields[4][0].piece;
		assertTrue(king.isOffending(board, 4, 0, 5, 1));
		assertTrue(king.isOffending(board, 4, 0, 5, 0));
		assertFalse(king.isOffending(board, 4, 0, 5, 5));
		assertFalse(king.isOffending(board, 4, 0, 7, 7));
		assertFalse(king.isOffending(board, 4, 0, 0, 7));
		assertFalse(king.isOffending(board, 4, 0, 7, 0));
		
		board.applyMove(4, 1, 4, 3);
		assertTrue(king.isOffending(board, 4, 0, 4, 1));
		
		board.reset();
		board.applyMove(4, 6, 4, 1);
		assertTrue(king.isOffending(board, 4, 0, 4, 1));
	}
	
	@Test
	void offendingKnight()
	{
		Board board = new Board();
		
		Piece knight = board.fields[1][0].piece;
		assertTrue(knight.isOffending(board, 1, 0, 2, 2));
		assertTrue(knight.isOffending(board, 1, 0, 0, 2));
		
		assertFalse(knight.isOffending(board, 1, 0, 1, 2));
		assertFalse(knight.isOffending(board, 1, 0, 3, 2));
		assertFalse(knight.isOffending(board, 1, 0, 3, 3));
		assertFalse(knight.isOffending(board, 1, 0, 3, 4));
		
		board.applyMove(1, 7, 2, 2);
		knight = board.fields[2][2].piece;
		
		assertTrue(knight.isOffending(board, 2, 2, 1, 0));
		assertTrue(knight.isOffending(board, 2, 2, 1, 4));
		assertTrue(knight.isOffending(board, 2, 2, 3, 4));
		assertTrue(knight.isOffending(board, 2, 2, 3, 0));
		
		assertFalse(knight.isOffending(board, 2, 2, 3, 3));
		assertFalse(knight.isOffending(board, 2, 2, 3, 5));
		assertFalse(knight.isOffending(board, 2, 2, 3, 2));
		
		board.fields[1][0].piece = new Knight(Color.BLACK);
		
		//Color shouldn't matter to isOffending method
		assertTrue(knight.isOffending(board, 2, 2, 1, 0));
	}
	
	@Test
	void offendingRook()
	{
		Board board = new Board();
		
		Piece rook = board.fields[0][0].piece;
		assertTrue(rook.isOffending(board, 0, 0, 0, 1));
		assertTrue(rook.isOffending(board, 0, 0, 1, 0));
		
		assertFalse(rook.isOffending(board, 0, 0, 2, 0));
		assertFalse(rook.isOffending(board, 0, 0, 2, 2));
		assertFalse(rook.isOffending(board, 0, 0, 2, 1));
		assertFalse(rook.isOffending(board, 0, 0, 0, 2));
		
		board.applyMove(0, 1, 0, 3);
		
		assertTrue(rook.isOffending(board, 0, 0, 0, 1));
		assertTrue(rook.isOffending(board, 0, 0, 0, 2));
		assertTrue(rook.isOffending(board, 0, 0, 0, 3));
		
		assertFalse(rook.isOffending(board, 0, 0, 0, 4));
		assertFalse(rook.isOffending(board, 0, 0, 0, 5));
		assertFalse(rook.isOffending(board, 0, 0, 0, 6));
		assertFalse(rook.isOffending(board, 0, 0, 0, 7));
		assertFalse(rook.isOffending(board, 0, 0, 7, 0));
	}	
	
	@Test
	void offendingQueen()
	{
		Board board = new Board();
		board.fields[0][0].piece = new Queen(Color.WHITE);
		
		Piece queen = board.fields[0][0].piece;
		assertTrue(queen.isOffending(board, 0, 0, 0, 1));
		assertTrue(queen.isOffending(board, 0, 0, 1, 0));
		
		assertFalse(queen.isOffending(board, 0, 0, 2, 0));
		assertFalse(queen.isOffending(board, 0, 0, 2, 2));
		assertFalse(queen.isOffending(board, 0, 0, 2, 1));
		assertFalse(queen.isOffending(board, 0, 0, 0, 2));
		
		board.applyMove(0, 1, 0, 3);
		
		assertTrue(queen.isOffending(board, 0, 0, 0, 1));
		assertTrue(queen.isOffending(board, 0, 0, 0, 2));
		assertTrue(queen.isOffending(board, 0, 0, 0, 3));
		
		assertFalse(queen.isOffending(board, 0, 0, 0, 4));
		assertFalse(queen.isOffending(board, 0, 0, 0, 5));
		assertFalse(queen.isOffending(board, 0, 0, 0, 6));
		assertFalse(queen.isOffending(board, 0, 0, 0, 7));
		assertFalse(queen.isOffending(board, 0, 0, 7, 0));
		
		Board board2 = new Board();
		board2.fields[5][0].piece = new Queen(Color.WHITE);
		board2.applyMove(4, 1, 4, 3);
		
		Piece queen2 = board2.fields[5][0].piece;
		assertTrue(queen2.isOffending(board2, 5, 0, 4, 1));
		assertTrue(queen2.isOffending(board2, 5, 0, 3, 2));
		assertTrue(queen2.isOffending(board2, 5, 0, 2, 3));
		assertTrue(queen2.isOffending(board2, 5, 0, 1, 4));
		assertTrue(queen2.isOffending(board2, 5, 0, 0, 5));
		
		assertFalse(queen2.isOffending(board2, 5, 0, 5, 5));
		assertFalse(queen2.isOffending(board2, 5, 0, 1, 5));
		assertFalse(queen2.isOffending(board2, 5, 0, 3, 5));
		assertFalse(queen2.isOffending(board2, 5, 0, 7, 7));
		assertFalse(queen2.isOffending(board2, 5, 0, 0, 7));
		assertFalse(queen2.isOffending(board2, 5, 0, 7, 0));
		assertFalse(queen2.isOffending(board2, 5, 0, 3, 3));
		assertFalse(queen2.isOffending(board2, 5, 0, 2, 2));
		
		board2.reset();
		board2.fields[2][0].piece = new Queen(Color.WHITE);
		
		board2.applyMove(3, 1, 3, 2);
		board2.applyMove(3, 6, 3, 4);
		board2.applyMove(2, 0, 6, 4);
		board2.applyMove(5, 6, 5, 5);
		queen2 = board2.fields[6][4].piece;
		System.out.println(board2);
		
		assertTrue(queen2.isOffending(board2, 6, 4, 5, 5));
		assertTrue(queen2.isOffending(board2, 6, 4, 7, 3));
		assertTrue(queen2.isOffending(board2, 6, 4, 7, 5));
		assertTrue(queen2.isOffending(board2, 6, 4, 5, 3));
		assertTrue(queen2.isOffending(board2, 6, 4, 4, 2));
		assertTrue(queen2.isOffending(board2, 6, 4, 3, 1));
		assertTrue(queen2.isOffending(board2, 6, 4, 2, 0));
		assertTrue(queen2.isOffending(board2, 6, 4, 7, 4));
		assertTrue(queen2.isOffending(board2, 6, 4, 6, 5));
		assertTrue(queen2.isOffending(board2, 6, 4, 6, 4));
		assertTrue(queen2.isOffending(board2, 6, 4, 3, 4));
		assertTrue(queen2.isOffending(board2, 6, 4, 4, 4));
		assertTrue(queen2.isOffending(board2, 6, 4, 5, 4));
		
		assertFalse(queen2.isOffending(board2, 6, 4, 5, 6));
		assertFalse(queen2.isOffending(board2, 6, 4, 4, 6));
		assertFalse(queen2.isOffending(board2, 6, 4, 3, 3));
		assertFalse(queen2.isOffending(board2, 6, 4, 2, 1));
		assertFalse(queen2.isOffending(board2, 6, 4, 0, 7));
		assertFalse(queen2.isOffending(board2, 6, 4, 3, 7));
	}
}
