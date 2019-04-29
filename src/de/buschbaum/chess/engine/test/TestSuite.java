package de.buschbaum.chess.engine.test;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({AvailabeMovesTest.class, BasicTest.class, IsOffendingTest.class})
public class TestSuite
{

}
