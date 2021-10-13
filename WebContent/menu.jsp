<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="de.buschbaum.chess.engine.game.*, 
		de.buschbaum.chess.engine.rules.*, 
		de.buschbaum.chess.engine.rules.piece.*, 
		de.buschbaum.chess.engine.calculation.*"
    pageEncoding="UTF-8"%>
<div class="row">
	<div class="col-12">
		<fieldset class="bg-grey">
			<input type="radio" name="color" value="black">
			<label>White</label>
			<input type="radio" name="color" value="white">
			<label>Black</label>
		</fieldset>
	</div>
	<div class="col-12">
		<button type="button" class="btn btn-secondary">Start new game</button>
	</div>
</div>