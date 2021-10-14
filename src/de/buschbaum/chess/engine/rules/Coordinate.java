package de.buschbaum.chess.engine.rules;

import java.util.Objects;

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
		if (x < 0 || x > 7 || y < 0 || y > 7) throw new RuntimeException("invalid coordinates " + x + "," + y);
		
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

	@Override
	public String toString()
	{
		return "[" + x + ", " + y + ", color=" + color + "]";
	}

	@Override
	public int hashCode() 
	{
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		return x == other.x && y == other.y;
	}
}
