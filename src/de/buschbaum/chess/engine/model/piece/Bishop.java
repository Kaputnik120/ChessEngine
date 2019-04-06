package de.buschbaum.chess.engine.model.piece;

import java.util.List;

import de.buschbaum.chess.engine.model.Board;
import de.buschbaum.chess.engine.model.Field;
import de.buschbaum.chess.engine.model.Move;
import de.buschbaum.chess.engine.model.PieceType;

public class Bishop implements Piece 
{

	@Override
	public List<Move> getAvailableMoves(Board board) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getScoringValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isOffending(Field field) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PieceType getPieceType() {
		return PieceType.BISHOP;
	}

}
