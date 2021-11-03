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
	
	var $clickedFieldHumanPiece = $clickedField.find("div.piece.humanPiece");
	var clickedFieldHasHumanPiece = $clickedFieldHumanPiece.length > 0;
	
	var $clickedFieldComputerPiece = $clickedField.find("div.piece.computerPiece");
	var clickedFieldHasComputerPiece = $clickedFieldComputerPiece.length > 0;
	
	var $selectedFieldsBefore = $("div.board div.field.selected");
	var selectedFieldsBeforeEmpty = $selectedFieldsBefore.length == 0;
	if (!clickedFieldHasHumanPiece && selectedFieldsBeforeEmpty)
	{
		//Nothing selected yet and selected field is empty or computer field -> do nothing
		return;
	}
	
	if (clickedFieldHasHumanPiece && selectedFieldsBeforeEmpty)
	{
		//Nothing selected and selected field has human piece on it -> select this field
		$clickedField.addClass("selected");
		return;
	}
	
	if (clickedFieldHasHumanPiece && !selectedFieldsBeforeEmpty)
	{
		//Somethig selected and selected field has human piece on it -> deselect other fields and select this field
		$selectedFieldsBefore.removeClass("selected");
		$clickedField.addClass("selected");
		return;
	}
	
	if ((clickedFieldHasComputerPiece || (!clickedFieldHasHumanPiece && !clickedFieldHasComputerPiece)) && !selectedFieldsBeforeEmpty)
	{
		//Human piece selected and target is computer field or empty
		
		//Promotion field?`-> show promotion dialog`
		var promotion = '';
		var toY = $clickedField.data('y');
		var $selectedPieceBefore = $selectedFieldsBefore.find('div');
		if ($selectedPieceBefore.hasClass('pawn') && 
			(($selectedPieceBefore.hasClass('whitePiece') && toY == 7) || ($selectedPieceBefore.hasClass('blackPiece') && toY == 0)))
		{
			showPromotionDialog();
		}
		else
		{
			//no promotion -> send ajax request for move and reload board
			$.post(contextPath + '/ajax.jsp', {
				todo : 'move',
				fromX : $selectedFieldsBefore.data('x'),
				fromY : $selectedFieldsBefore.data('y'),
				toX : $clickedField.data('x'),
				toY : toY,
				promotion: promotion
			}, function(response)
			{
				if (response.result)
				{
					//update board
					window.location.href = window.location.href;
					
					//setting loading screen
					var $overlay = $('#overlay');
					$overlay.show();
					$overlay.html("Waiting for computer move");
					
					//requesting computer move
					$.post(contextPath + '/ajax.jsp', {
						todo : 'getComputerMove'
					}, function(response)
					{
						if (response.result)
						{
							//Removing loading screen
							$overlay.hide();
							
							//applying computer move
							window.location.href = window.location.href;
						}
						else
						{
							alert(response.message);
						}
					});
				}
				else
				{
					alert(response.message);
				}
			});
		}
	}
}

function showPromotionDialog()
{
	var $overlay = $('#overlay');
	$overlay.show();
	$overlay.html
	(
		'<div class="d-inline-block" onclick="onPromotionFieldClick(event.target);">' +
		 	'<div class="whiteBishop">' +
				'<img src="whiteBishop.png">' +
		 	'</div>' +
		'</div>' +
		'<div class="d-inline-block"  onclick="onPromotionFieldClick(event.target);">' +
		 	'<div class="whiteKnight">' +
				'<img src="whiteKnight.png">' +
		 	'</div>' +
		'</div>' +
		'<div class="d-inline-block"  onclick="onPromotionFieldClick(event.target);">' +
		 	'<div class="whiteRook">' +
				'<img src="whiteRook.png">' +
		 	'</div>' +
		'</div>' +
		'<div class="d-inline-block"  onclick="onPromotionFieldClick(event.target);">' +
		 	'<div class="whiteQueen">' +
				'<img src="whiteQueen.png">' +
		 	'</div>' +
		'</div>'
	);
}

function onNewGameClick()
{
	$.post(contextPath + '/ajax.jsp', {
			todo : 'newGame',
			color : $('input[name=color]:checked').val()
		}, function(response)
		{
			if (response.result)
			{
				window.location.href = window.location.href;  
			}
			else
			{
				alert(response.message);
			}
		});
}