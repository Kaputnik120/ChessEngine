package de.buschbaum.chess.engine.test;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import de.buschbaum.chess.engine.rules.Color;
import de.buschbaum.chess.engine.rules.Field;
import de.buschbaum.chess.engine.rules.Move;
import de.buschbaum.chess.engine.rules.piece.Bishop;
import de.buschbaum.chess.engine.rules.piece.Knight;
import de.buschbaum.chess.engine.rules.piece.Pawn;
import de.buschbaum.chess.engine.rules.piece.Queen;
import de.buschbaum.chess.engine.rules.piece.Rook;

class AvailabeMovesTest
{

	@Test
	void knightAvailableMoves()
	{
		UnitTestBoard board = new UnitTestBoard();
		
		Field knightField = board.fields[1][0];
		List<Move> moves = knightField.getAvailableMoves(board);
		assertTrue(moves.size() == 2);
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(2, 2, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(0, 2, moves));
		
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
		UnitTestBoard board = new UnitTestBoard();
		Field bishopField = board.fields[2][7];
		
		List<Move> moves = bishopField.getAvailableMoves(board);
		assertTrue(moves.isEmpty());
		
		board.applyMove(3, 1, 3, 3);
		board.applyMove(1, 0, 2, 2);
		board.applyMove(4, 6, 4, 4);
		board.applyMove(5, 7, 1, 3);
		
		bishopField = board.fields[1][3];
		moves = bishopField.getAvailableMoves(board);
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(0, 4, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(0, 2, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(2, 4, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(3, 5, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(4, 6, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(5, 7, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(2, 2, moves));
		assertTrue(moves.size() == 7);
		
		board.reset();
		bishopField = board.fields[3][6];
		
		board.applyMove(3, 6, 3, 4);
		board.applyMove(2, 7, 3, 6);
		board.applyMove(3, 0, 2, 5);
		
		moves = bishopField.getAvailableMoves(board);
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(2, 5, moves));
		assertTrue(moves.size() == 1);
	}
	
	@Test
	void kingAvailableMoves()
	{
		UnitTestBoard board = new UnitTestBoard();
		
		Field kingField = board.getKing(Color.WHITE);
		List<Move> moves = kingField.getAvailableMoves(board);
		assertTrue(moves.isEmpty());
		
		
		board.applyMove(4, 0, 4, 3);
		kingField = board.getKing(Color.WHITE);
		moves = kingField.getAvailableMoves(board);
		assertTrue(moves.size() == 8);
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(3, 3, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(4, 4, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(4, 2, moves));
		assertFalse(UnitTestBoard.containsTargetCoordinateMove(4, 3, moves)); //king himself
		
		board.applyMove(1, 7, 2, 5);
		
		moves = kingField.getAvailableMoves(board);
		assertTrue(moves.size() == 6);
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(4, 2, moves));
		assertFalse(UnitTestBoard.containsTargetCoordinateMove(4, 3, moves)); //king himself
		assertFalse(UnitTestBoard.containsTargetCoordinateMove(3, 3, moves)); //check by knight
		assertFalse(UnitTestBoard.containsTargetCoordinateMove(4, 4, moves)); //check by knight
		
		board.applyMove(5, 1, 5, 3);
		board.applyMove(7, 7, 5, 4);
		
		moves = kingField.getAvailableMoves(board);
		assertTrue(moves.size() == 4);
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(4, 2, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(5, 4, moves)); //capturing enemy rook
		assertFalse(UnitTestBoard.containsTargetCoordinateMove(4, 3, moves)); //king himself
		assertFalse(UnitTestBoard.containsTargetCoordinateMove(3, 3, moves)); //check by knight
		assertFalse(UnitTestBoard.containsTargetCoordinateMove(4, 4, moves)); //check by knight
		assertFalse(UnitTestBoard.containsTargetCoordinateMove(3, 4, moves)); //check by rook
		assertFalse(UnitTestBoard.containsTargetCoordinateMove(5, 3, moves)); //allied pawn in the way
		
		board.reset();
		board.fields[1][0].piece = null;
		board.fields[2][0].piece = null;
		board.fields[3][0].piece = null;
		board.fields[5][0].piece = null;
		board.fields[6][0].piece = null;
		
		kingField = board.getKing(Color.WHITE);
		moves = kingField.getAvailableMoves(board);
		
		assertTrue(moves.size() == 4);
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(5, 0, moves)); //basic king move
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(3, 0, moves)); //basic king move
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(2, 0, moves)); //rochade
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(6, 0, moves)); //rochade
		
		board.fields[3][0].piece = new Pawn(Color.WHITE);
		kingField = board.getKing(Color.WHITE);
		moves = kingField.getAvailableMoves(board);
		
		assertTrue(moves.size() == 2);
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(5, 0, moves)); //basic king move
		assertFalse(UnitTestBoard.containsTargetCoordinateMove(3, 0, moves)); //basic king move
		assertFalse(UnitTestBoard.containsTargetCoordinateMove(2, 0, moves)); //rochade
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(6, 0, moves)); //rochade
		
		board.fields[3][0].piece = new Pawn(Color.WHITE);
		board.applyMove(7, 0, 6, 0);
		board.applyMove(6, 0, 7, 0);
		kingField = board.getKing(Color.WHITE);
		moves = kingField.getAvailableMoves(board);
		
		assertTrue(moves.size() == 1);
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(5, 0, moves)); //basic king move
		assertFalse(UnitTestBoard.containsTargetCoordinateMove(3, 0, moves)); //basic king move
		assertFalse(UnitTestBoard.containsTargetCoordinateMove(2, 0, moves)); //rochade
		assertFalse(UnitTestBoard.containsTargetCoordinateMove(6, 0, moves)); //rochade
		
		board.fields[7][0].piece.resetMoved();
		board.fields[6][1].piece = new Queen(Color.BLACK);
		
		kingField = board.getKing(Color.WHITE);
		moves = kingField.getAvailableMoves(board);
		
		assertTrue(moves.size() == 0);
		
		board.fields[6][1].piece = new Rook(Color.BLACK);
		
		kingField = board.getKing(Color.WHITE);
		moves = kingField.getAvailableMoves(board);
		
		assertTrue(moves.size() == 1);
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(5, 0, moves)); //basic king move
		
		board.fields[3][0].piece = null;
		board.fields[2][1].piece = new Rook(Color.BLACK);
		kingField = board.getKing(Color.WHITE);
		moves = kingField.getAvailableMoves(board);
		
		assertTrue(moves.size() == 2);
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(5, 0, moves)); //basic king move
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(3, 0, moves)); //basic king move
		
		board.fields[4][0].piece.setMoved();
		kingField = board.getKing(Color.WHITE);
		moves = kingField.getAvailableMoves(board);
		
		assertTrue(moves.size() == 2);
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(5, 0, moves)); //basic king move
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(3, 0, moves)); //basic king move
		
		board.fields[4][0].piece.resetMoved();
		board.fields[4][1].piece = new Rook(Color.BLACK);
		kingField = board.getKing(Color.WHITE);
		moves = kingField.getAvailableMoves(board);
		
		assertTrue(moves.size() == 3);
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(5, 0, moves)); //basic king move
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(3, 0, moves)); //basic king move
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(4, 1, moves)); //capture
	}
	
	@Test
	void rookAvailableMoves()
	{
		UnitTestBoard board = new UnitTestBoard();
		Field rookField = board.fields[0][7];
		List<Move> moves = rookField.getAvailableMoves(board);
		assertTrue(moves.isEmpty());
		
		board.applyMove(0, 6, 0, 4);
		moves = rookField.getAvailableMoves(board);
		assertTrue(moves.size() == 2);
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(0, 6, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(0, 5, moves));
		
		board.applyMove(0, 7, 0, 5);
		rookField = board.fields[0][5];
		moves = rookField.getAvailableMoves(board);
		
		assertTrue(moves.size() == 9);
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(0, 6, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(0, 7, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(1, 5, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(2, 5, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(3, 5, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(4, 5, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(5, 5, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(6, 5, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(7, 5, moves));
		
		board.applyMove(4, 7, 0, 7);
		board.applyMove(3, 0, 0, 4);
		moves = rookField.getAvailableMoves(board);
		
		assertTrue(moves.size() == 2);
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(0, 4, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(0, 6, moves));
	}
	
	@Test
	void queenAvailableMoves()
	{
		UnitTestBoard board = new UnitTestBoard();
		Field queenField = board.fields[3][0];
		List<Move> moves = queenField.getAvailableMoves(board);
		
		assertTrue(moves.isEmpty());
		
		board.applyMove(3, 0, 3, 3);
		queenField = board.fields[3][3];
		moves = queenField.getAvailableMoves(board);
		
		assertTrue(moves.size() == 19);
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(3, 2, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(3, 4, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(3, 5, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(3, 6, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(2, 3, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(1, 3, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(0, 3, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(4, 3, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(5, 3, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(6, 3, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(7, 3, moves));
		
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(2, 2, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(4, 2, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(4, 4, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(5, 5, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(6, 6, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(2, 4, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(1, 5, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(0, 6, moves));
		
	}
	
	@Test
	void pawnAvailableMoves()
	{
		UnitTestBoard board = new UnitTestBoard();
		Field pawnField = board.fields[4][1];
		
		//Double and simple moves available
		List<Move> moves = pawnField.getAvailableMoves(board);
		assertTrue(moves.size() == 2);
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(4, 2, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(4, 3, moves));
		
		//Only single move available
		board.applyMove(4, 1, 4, 3);
		pawnField = board.fields[4][3];
		moves = pawnField.getAvailableMoves(board);
		assertTrue(moves.size() == 1);
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(4, 4, moves));
		
		//En passent capture
		board.applyMove(4, 3, 4, 4);
		board.applyMove(3, 6, 3, 4);
		pawnField = board.fields[4][4];
		moves = pawnField.getAvailableMoves(board);
		assertTrue(moves.size() == 2);
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(3, 5, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(4, 5, moves));
		
		//Simple capture right side
		board = new UnitTestBoard();
		board.applyMove(3, 6, 3, 4);
		board.applyMove(2, 1, 2, 2);
		board.applyMove(3, 4, 3, 3);
		pawnField = board.fields[2][2];
		moves = pawnField.getAvailableMoves(board);
		
		assertTrue(moves.size() == 2);
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(2, 3, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(3, 3, moves));
		
		//Simple capture left side
		board = new UnitTestBoard();
		board.applyMove(1, 6, 1, 4);
		board.applyMove(2, 1, 2, 2);
		board.applyMove(1, 4, 1, 3);
		pawnField = board.fields[2][2];
		moves = pawnField.getAvailableMoves(board);
		
		assertTrue(moves.size() == 2);
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(2, 3, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(1, 3, moves));
		
		//Simple capture
		board = new UnitTestBoard();
		board.applyMove(3, 6, 3, 4);
		board.applyMove(2, 1, 2, 2);
		board.applyMove(3, 4, 3, 3);
		pawnField = board.fields[2][2];
		moves = pawnField.getAvailableMoves(board);
		
		assertTrue(moves.size() == 2);
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(2, 3, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(3, 3, moves));
		
		//Promotion
		board.resetWithKingOnly();
		board.fields[1][6].piece = new Pawn(Color.WHITE);
		pawnField = board.fields[1][6];
		moves = pawnField.getAvailableMoves(board);
		
		assertTrue(moves.size() == 4);
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(1, 7, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMovePromotion(1, 7, Bishop.class, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMovePromotion(1, 7, Rook.class, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMovePromotion(1, 7, Queen.class, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMovePromotion(1, 7, Knight.class, moves));
		
		//Promotion with capture right side
		board.resetWithKingOnly();
		board.fields[1][6].piece = new Pawn(Color.WHITE);
		board.fields[2][7].piece = new Pawn(Color.BLACK);
		pawnField = board.fields[1][6];
		moves = pawnField.getAvailableMoves(board);
		
		assertTrue(moves.size() == 8);
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(1, 7, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMovePromotion(1, 7, Bishop.class, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMovePromotion(1, 7, Rook.class, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMovePromotion(1, 7, Queen.class, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMovePromotion(1, 7, Knight.class, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(2, 7, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMovePromotion(2, 7, Bishop.class, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMovePromotion(2, 7, Rook.class, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMovePromotion(2, 7, Queen.class, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMovePromotion(2, 7, Knight.class, moves));
		
		//Promotion with capture left side
		board.resetWithKingOnly();
		board.fields[1][6].piece = new Pawn(Color.WHITE);
		board.fields[0][7].piece = new Pawn(Color.BLACK);
		pawnField = board.fields[1][6];
		moves = pawnField.getAvailableMoves(board);
		
		assertTrue(moves.size() == 8);
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(1, 7, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMovePromotion(1, 7, Bishop.class, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMovePromotion(1, 7, Rook.class, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMovePromotion(1, 7, Queen.class, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMovePromotion(1, 7, Knight.class, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(0, 7, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMovePromotion(0, 7, Bishop.class, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMovePromotion(0, 7, Rook.class, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMovePromotion(0, 7, Queen.class, moves));
		assertTrue(UnitTestBoard.containsTargetCoordinateMovePromotion(0, 7, Knight.class, moves));
		
		//Simple move blocked
		board = new UnitTestBoard();
		board.applyMove(3, 1, 3, 3);
		board.applyMove(3, 6, 3, 4);
		board.applyMove(2, 0, 7, 5);
		pawnField = board.fields[7][6];
		moves = pawnField.getAvailableMoves(board);
		
		assertTrue(moves.size() == 0);
		
		//Simple move allowed, double move blocked
		board = new UnitTestBoard();
		board.applyMove(3, 1, 3, 3);
		board.applyMove(3, 6, 3, 4);
		board.applyMove(2, 0, 6, 4);
		pawnField = board.fields[6][6];
		moves = pawnField.getAvailableMoves(board);
		
		assertTrue(moves.size() == 1);
		assertTrue(UnitTestBoard.containsTargetCoordinateMove(6, 5, moves));
		
		//TODO NPE @ Pawn:29 verhindern
	}
}
