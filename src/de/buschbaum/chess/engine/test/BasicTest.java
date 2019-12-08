package de.buschbaum.chess.engine.test;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import de.buschbaum.chess.engine.model.Board;
import de.buschbaum.chess.engine.model.Color;
import de.buschbaum.chess.engine.model.Coordinate;
import de.buschbaum.chess.engine.model.Field;
import de.buschbaum.chess.engine.model.Move;
import de.buschbaum.chess.engine.model.piece.Bishop;
import de.buschbaum.chess.engine.model.piece.King;
import de.buschbaum.chess.engine.model.piece.Knight;
import de.buschbaum.chess.engine.model.piece.Pawn;
import de.buschbaum.chess.engine.model.piece.Piece;
import de.buschbaum.chess.engine.model.piece.Queen;
import de.buschbaum.chess.engine.model.piece.Rook;

class BasicTest
{

	@Test
	void fieldNames()
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
	void coordinateColors()
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
	void getKingTest()
	{
		Board board =  new Board();
		Field field = board.getKing(Color.BLACK);
		assertTrue(field.coordinate.x == 4);
		assertTrue(field.coordinate.y == 7);
		
		field = board.getKing(Color.WHITE);
		assertTrue(field.coordinate.x == 4);
		assertTrue(field.coordinate.y == 0);
	}
	
	@Test
	void checkTest()
	{
		Board board = new Board();
		assertFalse(board.isCheck(Color.WHITE));
		assertFalse(board.isCheck(Color.BLACK));
		
		board.applyMove(4, 0, 4, 3);
		board.applyMove(2, 7, 2, 5);
		assertTrue(board.isCheck(Color.WHITE));
		assertFalse(board.isCheck(Color.BLACK));

		board.applyMove(6, 7, 5, 5);
		assertTrue(board.isCheck(Color.WHITE));
		assertFalse(board.isCheck(Color.BLACK));
		
		board.applyMove(2, 5, 2, 7);
		assertTrue(board.isCheck(Color.WHITE));
		assertFalse(board.isCheck(Color.BLACK));
		
		board = new Board();
		board.applyMove(4, 0, 4, 2);
		board.applyMove(4, 7, 5, 3);
		assertTrue(board.isCheck(Color.WHITE));
		assertTrue(board.isCheck(Color.BLACK));
	}
	
	@Test
	void applyAndUnapplyMoveTest()
	{
		Board board = new Board();
		board.fields[1][0].piece = null;
		board.fields[2][0].piece = null;
		board.fields[3][0].piece = null;
		board.fields[5][0].piece = null;
		board.fields[6][0].piece = null;
		
		//Rochade
		board.applyMove(new Move(board.fields[4][0], board.fields[6][0]), false);
		assertTrue(board.fields[5][0].piece instanceof Rook);
		assertTrue(board.fields[6][0].piece instanceof King);
		assertTrue(board.fields[7][0].piece == null);
		assertTrue(board.fields[4][0].piece == null);
		
		board.unapplyMove();
		assertTrue(board.fields[5][0].piece == null);
		assertTrue(board.fields[6][0].piece == null);
		assertTrue(board.fields[7][0].piece instanceof Rook);
		assertTrue(board.fields[4][0].piece instanceof King);
		
		board.applyMove(new Move(board.fields[4][0], board.fields[2][0]), false);
		assertTrue(board.fields[3][0].piece instanceof Rook);
		assertTrue(board.fields[2][0].piece instanceof King);
		assertTrue(board.fields[0][0].piece == null);
		assertTrue(board.fields[4][0].piece == null);
		
		board.unapplyMove();
		assertTrue(board.fields[3][0].piece == null);
		assertTrue(board.fields[2][0].piece == null);
		assertTrue(board.fields[0][0].piece instanceof Rook);
		assertTrue(board.fields[4][0].piece instanceof King);
		
		//En Passent
		board = new Board();
		board.applyMove(4, 1, 4, 3);
		board.applyMove(4, 3, 4, 4);
		board.applyMove(3, 6, 3, 4);
		//For the board to function correctly the Move must be passed with a capture
		Move enPassentMove = new Move(board.getLastMove(), board.fields[4][4], board.fields[3][5], Color.WHITE, null, board.fields[3][4].piece);
		board.applyMove(enPassentMove, false);
		
		assertTrue(board.fields[3][4].piece == null);
		assertTrue(board.fields[3][5].piece instanceof Pawn);
		assertTrue(board.fields[4][4].piece == null);
		
		//unaplly en passent
		board.unapplyMove();
		assertTrue(board.fields[3][4].piece instanceof Pawn);
		assertTrue(board.fields[3][5].piece == null);
		assertTrue(board.fields[4][4].piece instanceof Pawn);
	}
	
	@Test
	public void testDraw()
	{
		//Insufficient material
		Board board = new Board();
		board.resetWithKingOnly();
		assertTrue(board.isDraw(Color.WHITE));
		assertTrue(board.isDraw(Color.BLACK));
		
		board.fields[0][0].piece = new Knight(Color.WHITE);
		assertTrue(board.isDraw(Color.WHITE));
		assertTrue(board.isDraw(Color.BLACK));
		
		board.fields[0][0].piece = new Bishop(Color.BLACK);
		assertTrue(board.isDraw(Color.WHITE));
		assertTrue(board.isDraw(Color.BLACK));
		
		board.fields[0][1].piece = new Bishop(Color.WHITE);
		assertFalse(board.isDraw(Color.WHITE));
		assertFalse(board.isDraw(Color.BLACK));
		
		board.fields[0][1].piece = null;
		board.fields[1][1].piece = new Bishop(Color.WHITE);
		assertTrue(board.isDraw(Color.WHITE));
		assertTrue(board.isDraw(Color.BLACK));
		
		board.fields[3][3].piece = new Rook(Color.WHITE);
		assertFalse(board.isDraw(Color.WHITE));
		assertFalse(board.isDraw(Color.BLACK));
		
		//TODO test stalemate
		//TODO test threefold repition
		//TODO test 50 moves without capture
	}
}
