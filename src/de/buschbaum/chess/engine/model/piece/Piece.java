package de.buschbaum.chess.engine.model.piece;

import java.util.List;

import de.buschbaum.chess.engine.model.Board;
import de.buschbaum.chess.engine.model.Color;
import de.buschbaum.chess.engine.model.Coordinate;
import de.buschbaum.chess.engine.model.Move;

public interface Piece {
	
	/**
	 * Lists all possible moves for the piece at this coordinate.
	 * All rules are checked. 
	 */
	List<Move> getAvailableMoves(Board board, Coordinate coordinate);
	
	int getScoringValue();
	
	/**
	 * Checks if to coordinate can be reached by the piece at the from coordinate.
	 * The following rules are checked: en passent, basic movement rules
	 * The following rules aren't checked: check, checkmate, stalemate, rochade
	 * The field on the board relating to the from coordinate must contain the piece the method is called on.
	 */
	boolean isOffending(Board board, Coordinate from, Coordinate to);
	
	/**
	 * @see Piece#isOffending(Board, Coordinate, Coordinate)
	 */
	boolean isOffending(Board board, int fromX, int fromY, int toX, int toY);
	
	Color getColor();
	
	void setMoved();
	
	String getNotation();
}
