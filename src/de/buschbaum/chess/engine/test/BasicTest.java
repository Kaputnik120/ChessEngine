package de.buschbaum.chess.engine.test;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import de.buschbaum.chess.engine.Color;
import de.buschbaum.chess.engine.Coordinate;
import de.buschbaum.chess.engine.Field;
import de.buschbaum.chess.engine.Move;
import de.buschbaum.chess.engine.piece.Bishop;
import de.buschbaum.chess.engine.piece.King;
import de.buschbaum.chess.engine.piece.Knight;
import de.buschbaum.chess.engine.piece.Pawn;
import de.buschbaum.chess.engine.piece.Queen;
import de.buschbaum.chess.engine.piece.Rook;

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
		UnitTestBoard board =  new UnitTestBoard();
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
		UnitTestBoard board = new UnitTestBoard();
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
		
		board = new UnitTestBoard();
		board.applyMove(4, 0, 4, 2);
		board.applyMove(4, 7, 5, 3);
		assertTrue(board.isCheck(Color.WHITE));
		assertTrue(board.isCheck(Color.BLACK));
	}
	
	@Test
	void applyAndUnapplyMoveTest()
	{
		UnitTestBoard board = new UnitTestBoard();
		board.fields[1][0].piece = null;
		board.fields[2][0].piece = null;
		board.fields[3][0].piece = null;
		board.fields[5][0].piece = null;
		board.fields[6][0].piece = null;
		
		//Rochade
		int sizeBefore = board.getAppliedMoves().size();
		board.applyMove(new Move(board.fields[4][0], board.fields[6][0]), false);
		assertTrue(board.fields[5][0].piece instanceof Rook);
		assertTrue(board.fields[6][0].piece instanceof King);
		assertTrue(board.fields[7][0].piece == null);
		assertTrue(board.fields[4][0].piece == null);
		
		board.unapplyMove();
		assertTrue(sizeBefore == board.getAppliedMoves().size());
		assertTrue(board.fields[5][0].piece == null);
		assertTrue(board.fields[6][0].piece == null);
		assertTrue(board.fields[7][0].piece instanceof Rook);
		assertTrue(board.fields[4][0].piece instanceof King);
		
		sizeBefore = board.getAppliedMoves().size();
		board.applyMove(new Move(board.fields[4][0], board.fields[2][0]), false);
		assertTrue(board.fields[3][0].piece instanceof Rook);
		assertTrue(board.fields[2][0].piece instanceof King);
		assertTrue(board.fields[0][0].piece == null);
		assertTrue(board.fields[4][0].piece == null);
		
		board.unapplyMove();
		assertTrue(sizeBefore == board.getAppliedMoves().size());
		assertTrue(board.fields[3][0].piece == null);
		assertTrue(board.fields[2][0].piece == null);
		assertTrue(board.fields[0][0].piece instanceof Rook);
		assertTrue(board.fields[4][0].piece instanceof King);
		
		//En Passent
		board = new UnitTestBoard();
		board.applyMove(4, 1, 4, 3);
		board.applyMove(4, 3, 4, 4);
		board.applyMove(3, 6, 3, 4);
		sizeBefore = board.getAppliedMoves().size();
		//For the board to function correctly the Move must be passed with a capture
		Move enPassentMove = new Move(board.getLastMove(), board.fields[4][4], board.fields[3][5], Color.WHITE, null, board.fields[3][4].piece);
		board.applyMove(enPassentMove, false);
		
		assertTrue(board.fields[3][4].piece == null);
		assertTrue(board.fields[3][5].piece instanceof Pawn);
		assertTrue(board.fields[4][4].piece == null);
		
		//unaplly en passent
		board.unapplyMove();
		assertTrue(sizeBefore == board.getAppliedMoves().size());
		assertTrue(board.fields[3][4].piece instanceof Pawn);
		assertTrue(board.fields[3][5].piece == null);
		assertTrue(board.fields[4][4].piece instanceof Pawn);
	}
	
	@Test
	public void testDraw()
	{
		//Insufficient material
		UnitTestBoard board = new UnitTestBoard();
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
		
		//threefold repitition
		board = new UnitTestBoard();
		
		board.applyMove(3, 1, 3, 3);
		board.applyMove(3, 6, 3, 4);
		
		//1.repetition
		board.applyMove(3, 0, 3, 1);
		board.applyMove(3, 7, 3, 6);
		board.applyMove(3, 1, 3, 0);
		board.applyMove(3, 6, 3, 7);
		assertFalse(board.isDraw(Color.WHITE));
		assertFalse(board.isDraw(Color.BLACK));
		
		//2.repetition
		board.applyMove(3, 0, 3, 1);
		board.applyMove(3, 7, 3, 6);
		board.applyMove(3, 1, 3, 0);
		board.applyMove(3, 6, 3, 7);
		assertFalse(board.isDraw(Color.WHITE));
		assertFalse(board.isDraw(Color.BLACK));
		
		//3.repetition
		board.applyMove(3, 0, 3, 1);
		assertTrue(board.isDraw(Color.WHITE));
		assertTrue(board.isDraw(Color.BLACK));
		
		//50 moves without capture
		board = new UnitTestBoard();
		assertFalse(board.isDraw(Color.WHITE));
		assertFalse(board.isDraw(Color.BLACK));
		
		//1-16 moves: pawn one field moves
		board.applyMove(0, 1, 0, 2);
		board.applyMove(0, 6, 0, 5);
		board.applyMove(1, 1, 1, 2);
		board.applyMove(1, 6, 1, 5);
		board.applyMove(2, 1, 2, 2);
		board.applyMove(2, 6, 2, 5);
		board.applyMove(3, 1, 3, 2);
		board.applyMove(3, 6, 3, 5);
		board.applyMove(4, 1, 4, 2);
		board.applyMove(4, 6, 4, 5);
		board.applyMove(5, 1, 5, 2);
		board.applyMove(5, 6, 5, 5);
		board.applyMove(6, 1, 6, 2);
		board.applyMove(6, 6, 6, 5);
		board.applyMove(7, 1, 7, 2);
		board.applyMove(7, 6, 7, 5);
		assertFalse(board.isDraw(Color.WHITE));
		assertFalse(board.isDraw(Color.BLACK));
		
		//17-32 moves: pawn next one field moves
		board.applyMove(0, 2, 0, 3);
		board.applyMove(0, 5, 0, 4);
		board.applyMove(1, 2, 1, 3);
		board.applyMove(1, 5, 1, 4);
		board.applyMove(2, 2, 2, 3);
		board.applyMove(2, 5, 2, 4);
		board.applyMove(3, 2, 3, 3);
		board.applyMove(3, 5, 3, 4);
		board.applyMove(4, 2, 4, 3);
		board.applyMove(4, 5, 4, 4);
		board.applyMove(5, 2, 5, 3);
		board.applyMove(5, 5, 5, 4);
		board.applyMove(6, 2, 6, 3);
		board.applyMove(6, 5, 6, 4);
		board.applyMove(7, 2, 7, 3);
		board.applyMove(7, 5, 7, 4);
		assertFalse(board.isDraw(Color.WHITE));
		assertFalse(board.isDraw(Color.BLACK));
		
		//33-40 moves: rook two times one piece forward
		board.applyMove(0, 0, 0, 1);
		board.applyMove(0, 7, 0, 6);
		board.applyMove(0, 1, 0, 2);
		board.applyMove(0, 6, 0, 5);
		board.applyMove(7, 0, 7, 1);
		board.applyMove(7, 7, 7, 6);
		board.applyMove(7, 1, 7, 2);
		board.applyMove(7, 6, 7, 5);
		assertFalse(board.isDraw(Color.WHITE));
		assertFalse(board.isDraw(Color.BLACK));
		
		//41-48 moves: queen and king two times one piece forward
		board.applyMove(3, 0, 3, 1);
		board.applyMove(3, 7, 3, 6);
		board.applyMove(3, 1, 3, 2);
		board.applyMove(3, 6, 3, 5);
		board.applyMove(4, 0, 4, 1);
		board.applyMove(4, 7, 4, 6);
		board.applyMove(4, 1, 4, 2);
		board.applyMove(4, 6, 4, 5);
		assertFalse(board.isDraw(Color.WHITE));
		assertFalse(board.isDraw(Color.BLACK));
		
		//49-50 moves: knights move forward
		board.applyMove(1, 0, 2, 2);
		assertFalse(board.isDraw(Color.WHITE));
		assertFalse(board.isDraw(Color.BLACK));
		board.applyMove(1, 7, 2, 5);
		assertTrue(board.isDraw(Color.WHITE));
		assertTrue(board.isDraw(Color.BLACK));
		
		//Stalemate
		board = new UnitTestBoard();
		board.resetWithKingOnly();
		board.fields[4][6].piece = new Pawn(Color.WHITE);
		assertFalse(board.isDraw(Color.WHITE));
		assertFalse(board.isDraw(Color.BLACK));
		board.fields[4][1].piece = new Queen(Color.WHITE);
		assertFalse(board.isDraw(Color.WHITE));
		assertFalse(board.isDraw(Color.BLACK));
		board.fields[3][0].piece = new Rook(Color.WHITE);
		assertFalse(board.isDraw(Color.WHITE));
		assertFalse(board.isDraw(Color.BLACK));
		board.fields[5][0].piece = new Rook(Color.WHITE);
		assertFalse(board.isDraw(Color.WHITE));
		assertTrue(board.isDraw(Color.BLACK));
		
		//Checkmate
		board = new UnitTestBoard();
		board.resetWithKingOnly();
		board.applyMove(4, 0, 4, 5);
		board.fields[7][7].piece = new Rook(Color.WHITE);
		assertFalse(board.isDraw(Color.WHITE));
		assertFalse(board.isDraw(Color.BLACK));
		assertFalse(board.isCheckmate(Color.WHITE));
		assertTrue(board.isCheckmate(Color.BLACK));
	}
}
