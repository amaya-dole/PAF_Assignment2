$(document).on("click", "#btnSave", function(event)
{
		// Clear alerts---------------------
 		$("#alertSuccess").text(""); 
 		$("#alertSuccess").hide(); 
 		$("#alertError").text(""); 
 		$("#alertError").hide(); 
		// Form validation-------------------
		var status = validateCardForm(); 
		if (status != true) 
 		{ 
 			$("#alertError").text(status); 
 			$("#alertError").show(); 
 		return; 
 		} 
		// If valid------------------------
		var type = ($("#hidCardIDSave").val() == "") ? "POST" : "PUT"; 
 		$.ajax( 
 		{ 
			 url : "PaymentAPI", 
			 type : type, 
			 data : $("#formCard").serialize(), 
			 dataType : "text", 
 			 complete : function(response, status) 
 		{ 
		 onItemSaveComplete(response.responseText, status); 
		 } 
		 }); 
		 
});

function onCardSaveComplete(response, status)
{ 
if (status == "success") 
 { 
 var resultSet = JSON.parse(response); 
 if (resultSet.status.trim() == "success") 
 { 
 $("#alertSuccess").text("Successfully saved."); 
 $("#alertSuccess").show(); 
 $("#divCardGrid").html(resultSet.data); 
 } else if (resultSet.status.trim() == "error") 
 { 
 $("#alertError").text(resultSet.data); 
 $("#alertError").show(); 
 } 
 } else if (status == "error") 
 { 
 $("#alertError").text("Error while saving."); 
 $("#alertError").show(); 
 } else
 { 
 $("#alertError").text("Unknown error while saving.."); 
 $("#alertError").show(); 
 }
$("#hidItemIDSave").val(""); 
$("#formItem")[0].reset(); 
}


// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
		{ 
		$("#hidCardIDSave").val($(this).data("cardid")); 
		 $("#cardno").val($(this).closest("tr").find('td:eq(0)').text()); 
		 $("#name").val($(this).closest("tr").find('td:eq(1)').text()); 
		 $("#cvv").val($(this).closest("tr").find('td:eq(2)').text()); 
		 $("#exDate").val($(this).closest("tr").find('td:eq(3)').text()); 
		});




$(document).on("click", ".btnRemove", function(event)
		{ 
		 $.ajax( 
		 { 
		 url : "PaymentAPI", 
		 type : "DELETE", 
		 data : "cardID=" + $(this).data("cardid"),
		 dataType : "text", 
		 complete : function(response, status) 
		 { 
		 onItemDeleteComplete(response.responseText, status); 
		 } 
		 }); 
		});
		
function onItemDeleteComplete(response, status)
{ 
		if (status == "success") 
 		{ 
 			var resultSet = JSON.parse(response); 
 			if (resultSet.status.trim() == "success") 
 			{ 
 				$("#alertSuccess").text("Successfully deleted."); 
 				$("#alertSuccess").show(); 
 				$("#divCardGrid").html(resultSet.data); 
 			} else if (resultSet.status.trim() == "error") 
 			{ 
 				$("#alertError").text(resultSet.data); 
 				$("#alertError").show(); 
 			} 
 		} else if (status == "error") 
 		{ 
 			$("#alertError").text("Error while deleting."); 
 			$("#alertError").show(); 
 		} else
 		{ 
 			$("#alertError").text("Unknown error while deleting.."); 
 			$("#alertError").show(); 
 		} 
}


// CLIENT-MODEL================================================================
function validateCardForm()
{
	// CARD NO
	if ($("#cardno").val().trim() == "")
	{
	return "Insert Card Number.";
	}
	// NAME
	if ($("#name").val().trim() == "")
	{
	return "Insert Card Holders Name.";
}

// CVV-------------------------------
if ($("#cvv").val().trim() == ""){
	return "Insert CVV.";
}
		// is numerical value
		var tmpcvv = $("#cvv").val().trim();
		if (!$.isNumeric(tmpcvv))
	{
	return "Insert a numerical value for cvv.";
	}
		


// EXDATE------------------------
if ($("#exDate").val().trim() == ""){
	
	return "Insert Expiry Date.";
}
	return true;
}