package de.buschbaum.chess.engine.model;

public enum Color {
	WHITE('w'), BLACK('b');
	
	public final char notation;
	
	private Color(char notation)
	{
		this.notation = notation;
	}
}
