package de.buschbaum.chess.engine.model;

public class Coordinate
{
	public final int x;
	public final int y;
	public final Color color;
	
	/**
	 * Creates a new Coordinate.
	 * x and y are 0-based.
	 */
	public Coordinate(int x, int y)
	{
		this.x = x;
		this.y = y;
		boolean isEvenX = x % 2 == 0;
		boolean isEvenY = y % 2 == 0;
		this.color = isEvenY && isEvenX || !isEvenY && !isEvenX ? Color.BLACK : Color.WHITE;
	}
	
	public String getFieldName()
	{
		char[] chars = new char[2];
		chars[0] = (char) (x + 65);
		chars[1] = Character.forDigit(y + 1, 10);
		return new String(chars);
	}
}
