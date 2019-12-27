<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="de.buschbaum.chess.engine.game.*, 
		de.buschbaum.chess.engine.rules.*, 
		de.buschbaum.chess.engine.rules.piece.*, 
		de.buschbaum.chess.engine.calculation.*"
    pageEncoding="UTF-8"%>
<%
	Game game = (Game) session.getAttribute("game");
	if (game == null)
	{
		game = new Game(PlayerType.HUMAN, PlayerType.COMPUTER, 0);
		session.setAttribute("game", game);
	}
%>
<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="style.css">

    <title>Stupid chess engine</title>
  </head>
  <body>
    <div class="container">
    	<div class="row">
    		<div class="col-12">
    			<img src="https://picsum.photos/1125/150">
    		</div>
    	</div>
    	<div class="row">
    		<div class="col-12">
	    		<jsp:include page="topMenu.jsp" />
    		</div>
    	</div>
    	<div class="row">
    		<div class="col-12">
    			<jsp:include page="board.jsp" />
    		</div>
    	</div>
    	<div class="row">
    		<div class="col-12">
    			<jsp:include page="info.jsp" />
    		</div>
    	</div>
    </div>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.2.1.min.js" ></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
  </body>
</html>