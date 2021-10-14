<%@ page language="java" contentType="application/json; charset=UTF-8"
	import="de.buschbaum.chess.engine.game.*, 
		de.buschbaum.chess.engine.rules.*, 
		de.buschbaum.chess.engine.rules.piece.*, 
		de.buschbaum.chess.engine.calculation.*,
		com.google.gson.*,
		de.buschbaum.chess.ui.*"
    pageEncoding="UTF-8"%>
<%
	Result result = new Result();
	
	try
	{
		String todo = request.getParameter("todo");
		if (todo == null)
		{
			result.setResult(false);
			result.setMessage("No todo supplied");
			out.write(new Gson().toJson(result));
			return;
		}
		
		
		switch (todo)
		{
			case "move" :
			{
				int fromX = Integer.parseInt(request.getParameter("fromX"));
				int fromY = Integer.parseInt(request.getParameter("fromY"));
				int toX = Integer.parseInt(request.getParameter("toX"));
				int toY = Integer.parseInt(request.getParameter("toY"));
				
				Game game = (Game) session.getAttribute("game");
				Board board = game.board;
				
				Field fromField = board.fields[fromX][fromY];
				Field toField = board.fields[toX][toY];
				Move move = new Move(fromField, toField);
				
				Color nextTurnColor = board.getNextTurnColor();
				
	 			if (board.getAvailableMoves(nextTurnColor).contains(move))
	 			{
	 				board.applyMove(move, true);
	 				result.setResult(true);
	 			}
	 			else
	 			{
	 				result.setResult(false);
	 				result.setMessage("Illegal move");
	 			}
				
	 			out.write(new Gson().toJson(result));
	 			
				return;
			}
			case "newGame" :
			{
				Color color = Color.valueOf(request.getParameter("color"));
				
				Game game;
				if (Color.WHITE.equals(color))
				{
					game = new Game(PlayerType.HUMAN, PlayerType.COMPUTER);
				}
				else
				{
					game = new Game(PlayerType.COMPUTER, PlayerType.HUMAN);
				}
				session.setAttribute("game", game);
				
				result.setResult(true);
				out.write(new Gson().toJson(result));
				
				return;
			}
			default :
			{
				result.setResult(false);
				result.setMessage("No matching todo supplied");
				out.write(new Gson().toJson(result));
				return;
			}
		}
	}
	catch (Exception e)
	{
		e.printStackTrace();
		
		result.setResult(false);
		result.setMessage("Error occurred");
		out.write(new Gson().toJson(result));
		return;
	}
	
%>