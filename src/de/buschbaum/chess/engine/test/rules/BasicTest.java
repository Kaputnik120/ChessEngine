package de.buschbaum.chess.engine.test.rules;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.buschbaum.chess.engine.model.Color;
import de.buschbaum.chess.engine.model.Field;
import de.buschbaum.chess.engine.model.Move;
import de.buschbaum.chess.engine.model.piece.Queen;

class BasicTest
{

	@Test
	void testFieldNames()
	{
		Queen queen = new Queen();
		
		Field a = new Field((short) 0,(short) 0, queen);
		assertEquals(a.getFieldName(), "A1");
		assertEquals(a.color, Color.BLACK);
		
		Field b = new Field((short) 1,(short) 1, queen);
		assertEquals(b.getFieldName(), "B2");
		assertEquals(b.color, Color.BLACK);
		
		Field c = new Field((short) 7,(short) 7, queen);
		assertEquals(c.getFieldName(), "H8");
		assertEquals(c.color, Color.BLACK);
		
		Field d = new Field((short) 3,(short) 4, queen);
		assertEquals(d.getFieldName(), "D5");
		assertEquals(d.color, Color.WHITE);
	}
}
