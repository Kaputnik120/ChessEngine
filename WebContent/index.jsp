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
		game = new Game(PlayerType.HUMAN, PlayerType.COMPUTER);
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
  	<div id="overlay" style="position: absolute; left: 50%; top: 50%; z-index: 1000; background-color: white;">
	  <div style="position: relative; left: -50%; top: -50%;">
	  </div>
	</div>
    <div class="container">
    	<div class="row">
    		<div class="col-12">
    			<img class="img-fluid" src="header.jpg">
    		</div>
    	</div>
    	<div class="row">
    		<div class="col-12 col-md pt-2">
    			<jsp:include page="board.jsp" />
    		</div>
    		<div class="col-12 col-md pt-2">
	    		<jsp:include page="menu.jsp" />
    		</div>
    	</div>
    </div>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.2.1.min.js" ></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="script.js"></script>
  </body>
  <script>
  		var contextPath = '<%= request.getContextPath() %>';
  </script>
</html>