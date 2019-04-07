package de.buschbaum.chess.engine.model.piece;

import java.util.List;

import de.buschbaum.chess.engine.model.Board;
import de.buschbaum.chess.engine.model.Color;
import de.buschbaum.chess.engine.model.Coordinate;
import de.buschbaum.chess.engine.model.Move;

public interface Piece {

	List<Move> getAvailableMoves(Board board, Coordinate coordinate);
	
	int getScoringValue();
	
	boolean isOffending(Board board, Coordinate fromCoordinate, List<Coordinate> toCoordinates);
	
	Color getColor();
}
