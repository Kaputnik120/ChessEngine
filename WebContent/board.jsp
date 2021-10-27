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
<div class="row">
	<div class="col-12 float-left">
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
						.append("field");
					
					Piece piece = field.piece;
					if (piece != null)
					{
						pieceClassesBuilder.append(" ")
							.append("piece")
							.append(" ")
							.append(piece.getColor().equals(Color.WHITE) ? "whitePiece" : "blackPiece")
							.append(" ")
							.append(piece.getColor().equals(Color.WHITE) && whitePlayerHuman || piece.getColor().equals(Color.BLACK) && !whitePlayerHuman ? "humanPiece" : "computerPiece");
					}
					
					//In this part no whitespaces may be added to html:
					%><div class="<%= fieldClassesBuilder.toString() %> d-inline-block" onclick="onFieldClick(event.target);" data-x="<%= x %>" data-y="<%= y %>"><%
					if (piece != null)
					{
						String pieceName = piece.getClass().getSimpleName().toLowerCase();
					%><div class="<%= pieceClassesBuilder %> <%= pieceName %>"><img src="<%=piece.getColor().name().toLowerCase() + pieceName.substring(0, 1).toUpperCase() + pieceName.substring(1) %>.png"></div><%
					}
					%></div><%
				}	
			}
		%>
		</div>
	</div>
</div>