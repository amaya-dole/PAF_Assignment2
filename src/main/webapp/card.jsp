<%@page import="com.Card"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" 
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Card Details</title>
		<link rel="stylesheet" href="Views/bootstrap.min.css">
		<script src="Components/jquery-3.6.0.min.js"></script>
		<script src="Components/payment.js"></script>
	</head>
	
	<body> 
		<div class="container"><div class="row"><div class="col-6"> 
		
		<h1>Card Details</h1>
		
			<form id="formCard" name="formCard" method="post" action="card.jsp">
 					Card Number: 
 					<input id="cardno" name="cardno" type="text" class="form-control form-control-sm">
 					<br> Name of the card holder: 
 					<input id="name" name="name" type="text" class="form-control form-control-sm">
 					<br> CVV: 
 					<input id="cvv" name="cvv" type="text" class="form-control form-control-sm">
 					<br> Expiry Date: 
 					<input id="exDate" name="exDate" type="text" class="form-control form-control-sm">
 					<br>
 					<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
 					<input type="hidden" id="hidCardIDSave" name="hidCardIDSave" value=""> <br><br>
			</form>
			
			
			<div id="alertSuccess" class="alert alert-success"></div>
			<div id="alertError" class="alert alert-danger"></div>
			
			<br>
			
			<div id="divCardGrid"> 
			 <%
			 Card cardObj = new Card(); 
			 out.print(cardObj.ReadCard()); 
			 %>
			</div>
			
		</div> </div> </div> 
	</body>
</html>