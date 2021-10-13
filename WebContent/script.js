function onFieldClick(element)
{
	var $elem = $(element);
	
	var $clickedField;
	if (!$elem.hasClass("field"))
	{
		$clickedField = $elem.closest(".field");
	}
	else
	{
		$clickedField = $elem;
	}
	
	var $clickedFieldPiece = $clickedField.find("div.piece.humanPiece");
	var clickedFieldHasHumanPiece = $clickedFieldPiece.length == 0;
	
	var $selectedFieldsBefore = $("div.board div.field.selected");
	var selectedFieldsBeforeEmpty = $selectedFieldsBefore.length == 0;
	if (clickedFieldHasHumanPiece && selectedFieldsBeforeEmpty)
	{
		//Nothing selected yet and selected field is empty -> do nothing
		return;
	}
	
	if (!clickedFieldHasHumanPiece && selectedFieldsBeforeEmpty)
	{
		//Nothing selected and selected field has human piece on it -> select this field
		$clickedField.addClass("selected");
		return;
	}
	
	if (!clickedFieldHasHumanPiece && !selectedFieldsBeforeEmpty)
	{
		//Somethig selected and selected field has human piece on it -> deselect other fields and select this field
		$selectedFieldsBefore.removeClass("selected");
		$clickedField.addClass("selected");
		return;
	}
	
	if (clickedFieldHasHumanPiece && !selectedFieldsBeforeEmpty)
	{
		//Something selected and selected field is empty -> send ajax request for move and reload board
		//TODO
		alert('doingMove');
		return;
	}
}