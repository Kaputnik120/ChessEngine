package de.buschbaum.chess.engine.model;

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
}
