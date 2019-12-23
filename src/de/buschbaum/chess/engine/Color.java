package de.buschbaum.chess.engine;

/**
 * @author Uli
 *
 */
public enum Color {
	WHITE('w'), BLACK('b');
	
	public final char notation;
	
	private Color(char notation)
	{
		this.notation = notation;
	}
	
	public boolean isOpposite(Color color)
	{
		return this.equals(Color.getOpposite(color));
	}
	
	public static Color getOpposite(Color color)
	{
		if (color.equals(WHITE)) return BLACK;
		return WHITE;
	}
}
