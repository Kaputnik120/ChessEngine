package de.buschbaum.chess.engine.model.piece;

import java.util.List;

import de.buschbaum.chess.engine.model.Board;
import de.buschbaum.chess.engine.model.Field;
import de.buschbaum.chess.engine.model.Move;

public class Pawn implements Piece {
	@Override
	public List<Move> getAvailableMoves(Board board, Field field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getScoringValue() {
		return 1;
	}

	@Override
	public boolean isOffending(Field field) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
