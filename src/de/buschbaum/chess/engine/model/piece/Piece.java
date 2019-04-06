package de.buschbaum.chess.engine.model.piece;

import java.util.List;

import de.buschbaum.chess.engine.model.Board;
import de.buschbaum.chess.engine.model.Field;
import de.buschbaum.chess.engine.model.Move;
import de.buschbaum.chess.engine.model.PieceType;

public interface Piece {

	List<Move> getAvailableMoves(Board board, Field field);
	
	int getScoringValue();
	
	boolean isOffending(Field field);
}
