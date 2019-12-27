<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="de.buschbaum.chess.engine.game.*, 
		de.buschbaum.chess.engine.rules.*, 
		de.buschbaum.chess.engine.rules.piece.*, 
		de.buschbaum.chess.engine.calculation.*"
    pageEncoding="UTF-8"%>
<%
	Game game = (Game) session.getAttribute("game");
	Board board = game.board;
%>
<div class="board">
<%
	int direction = game.playerWhite.equals(PlayerType.HUMAN) ? -1 : 1;
	int startY = game.playerWhite.equals(PlayerType.HUMAN) ? 7 : 0;
	int endY = game.playerWhite.equals(PlayerType.HUMAN) ? 0 : 7;
	for (int y = startY; y >= endY; y += direction)
	{
		for (int x = 0; x <= 7; x++)
		{
			StringBuilder fieldClassesBuilder = new StringBuilder();
			StringBuilder pieceClassesBuilder = new StringBuilder();
			Field field = board.fields[x][y];
			fieldClassesBuilder.append(field.coordinate.color.equals(Color.WHITE) ? "whiteField" : "blackField")
				.append(" ")
				.append("field")
				.append(" ")
				.append("no-gutters");
			
			Piece piece = field.piece;
			if (piece != null)
			{
				pieceClassesBuilder.append(" ")
					.append("piece")
					.append(" ")
					.append(piece.getColor().equals(Color.WHITE) ? "whitePiece" : "blackPiece")
					.append(" ")
					.append(piece.getClass().getSimpleName().toLowerCase())
					.append(" ")
					.append("no-gutters");
			}
			%>
				<div class="<%= fieldClassesBuilder.toString() %>">
					<%
					if (piece != null)
					{
					%>
					<div class="<%= pieceClassesBuilder %>"></div>
					<%
					}
					%>
				</div>
			<%
		}	
	}

%>
</div>