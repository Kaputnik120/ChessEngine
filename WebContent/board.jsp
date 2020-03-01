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
	boolean whitePlayerHuman = game.playerWhite.equals(PlayerType.HUMAN);
	int direction = whitePlayerHuman ? -1 : 1;
	int startY = whitePlayerHuman ? 7 : 0;
	int endY = whitePlayerHuman ? 0 : 7;
	for (int y = startY; whitePlayerHuman ? y >= endY : y <= endY; y += direction)
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
					.append(piece.getColor().equals(Color.WHITE) && whitePlayerHuman || piece.getColor().equals(Color.BLACK) && !whitePlayerHuman ? "humanPiece" : "computerPiece")
					.append(" ")
					.append(piece.getClass().getSimpleName().toLowerCase())
					.append(" ")
					.append("no-gutters");
			}
			%>
				<div class="<%= fieldClassesBuilder.toString() %>" onclick="onFieldClick(event.target);">
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