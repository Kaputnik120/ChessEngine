package de.buschbaum.chess.engine.model;

public class Coordinate
{
	public final short x;
	public final short y;
	public final Color color;
	
	/**
	 * Creates a new Coordinate.
	 * x and y are 0-based.
	 */
	public Coordinate(short x, short y)
	{
		this.x = x;
		this.y = y;
		this.color = (x % 2 == 1) && (y % 2 == 0) ? Color.WHITE : Color.BLACK;
	}
	
	public String getFieldName()
	{
		char[] chars = new char[2];
		chars[0] = (char) (x + 65);
		chars[1] = Character.forDigit(y + 1, 10);
		return new String(chars);
	}
}
