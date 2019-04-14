package de.buschbaum.chess.engine.test.rules;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.buschbaum.chess.engine.model.Board;
import de.buschbaum.chess.engine.model.Color;
import de.buschbaum.chess.engine.model.Coordinate;
import de.buschbaum.chess.engine.model.Field;
import de.buschbaum.chess.engine.model.Move;
import de.buschbaum.chess.engine.model.piece.Piece;
import de.buschbaum.chess.engine.model.piece.Queen;

class BasicTest
{

	@Test
	void testFieldNames()
	{
		Queen queen = new Queen(Color.WHITE);
		
		Field a = new Field((short) 0,(short) 0, queen);
		assertEquals(a.getFieldName(), "A1");
		assertEquals(a.coordinate.color, Color.BLACK);
		
		Field b = new Field((short) 1,(short) 1, queen);
		assertEquals(b.getFieldName(), "B2");
		assertEquals(b.coordinate.color, Color.BLACK);
		
		Field c = new Field((short) 7,(short) 7, queen);
		assertEquals(c.getFieldName(), "H8");
		assertEquals(c.coordinate.color, Color.BLACK);
		
		Field d = new Field((short) 3,(short) 4, queen);
		assertEquals(d.getFieldName(), "D5");
		assertEquals(d.coordinate.color, Color.WHITE);
	}
	
	@Test
	void testCoordinateColors()
	{
		assertEquals(new Coordinate(0, 0).color, Color.BLACK);
		assertEquals(new Coordinate(5, 0).color, Color.WHITE);
		assertEquals(new Coordinate(4, 1).color, Color.WHITE);
		assertEquals(new Coordinate(3, 2).color, Color.WHITE);
		assertEquals(new Coordinate(2, 3).color, Color.WHITE);
		assertEquals(new Coordinate(1, 4).color, Color.WHITE);
		assertEquals(new Coordinate(0, 5).color, Color.WHITE);
		assertEquals(new Coordinate(1, 1).color, Color.BLACK);
	}
	
	@Test
	void testBishopOffendindFields()
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
		
		System.out.println(board);
		
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
}
