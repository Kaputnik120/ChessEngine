package de.buschbaum.chess.engine.model;

import java.util.ArrayList;
import java.util.List;

import de.buschbaum.chess.engine.model.piece.Bishop;
import de.buschbaum.chess.engine.model.piece.King;
import de.buschbaum.chess.engine.model.piece.Knight;
import de.buschbaum.chess.engine.model.piece.Queen;
import de.buschbaum.chess.engine.model.piece.Rook;

public class Board {

	public final Field[][] fields;
	
	private List<Move> appliedMoves = new ArrayList<>();
	private List<Move> calculatedMoves = new ArrayList<>();
	
	public Board()
	{
		this.fields = new Field[7][7];
		
		for (short x = 0; x <= 7; x++)
		{
			for (short y = 0; y <= 7; y++)
			{
				if (x == 0 && y == 0 || x == 7 && y == 0)
				{
					Rook rook = new Rook(Color.WHITE);
					fields[x][y] = new Field(x, y, rook);
				}
				else if (x == 0 && y == 7 || x == 7 && y == 7)
				{
					Rook rook = new Rook(Color.BLACK);
					fields[x][y] = new Field(x, y, rook);
				}
				else if (x == 1 && y == 0 || x == 6 && y == 0)
				{
					Knight knight = new Knight(Color.WHITE);
					fields[x][y] = new Field(x, y, knight);
				}
				else if (x == 1 && y == 7 || x == 6 && y == 7)
				{
					Knight knight = new Knight(Color.BLACK);
					fields[x][y] = new Field(x, y, knight);
				}
				else if (x == 2 && y == 0 || x == 5 && y == 0)
				{
					Bishop bishop = new Bishop(Color.WHITE);
					fields[x][y] = new Field(x, y, bishop);
				}
				else if (x == 2 && y == 7 || x == 5 && y == 7)
				{
					Bishop bishop = new Bishop(Color.BLACK);
					fields[x][y] = new Field(x, y, bishop);
				}
				else if (x == 3 && y == 0)
				{
					Queen queen = new Queen(Color.WHITE);
					fields[x][y] = new Field(x, y, queen);
				}
				else if (x == 3 && y == 7)
				{
					Queen queen = new Queen(Color.BLACK);
					fields[x][y] = new Field(x, y, queen);
				}
				else if (x == 4 && y == 0)
				{
					King king = new King(Color.WHITE);
					fields[x][y] = new Field(x, y, king);
				}
				else if (x == 4 && y == 7)
				{
					King king = new King(Color.BLACK);
					fields[x][y] = new Field(x, y, king);
				}
				else
				{
					fields[x][y] = new Field(x, y, null);
				}
			}	
		}
	}
	
	public Board(Field[][] fields)
	{
		this.fields = fields;
	}
	
}
