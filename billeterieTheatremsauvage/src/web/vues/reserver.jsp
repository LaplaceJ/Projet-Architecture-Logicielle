<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>Demande d'achat de billets</title>
</head>
<body>
	<h1>Demande d'achat de billets</h1>

	<ul>
		<li>Client : ${emailClient} </li>
		<li>Spectacle : ${nomSpec} (prix de base : ${prixDeBase})</li>
		
	</ul>
	
	
	<h2>Conditions tarifaires : </h2>
	
	<ul>
		<li>Un client standard paye le prix de base du spectacle plus la majoration de la catégorie de sa place </li>
		<li>Un client tarif réduit paye 90% du prix de base plus la majoration de la catégorie de sa place</li>
		<li>Un client abonné avec nba années de fidélité paye (80-max(nba,10))% du prix total (prix de base + majoration) </li>
	</ul>
	
	<h1>Catégories disponibles : </h1>
	
	<c:forEach items="${requestScope.lCatPlace}" var="cp" varStatus="i"> 
			
			<h2> Catégorie : ${cp.nomCat} (majoration: ${cp.majoration} ) </h2> 
			
			<p> Nbre de places demandées (max : ${lCatPlaceDispo[i.index]} ): 
			
				<FORM action="resultatReservation" method="get">
				 <SELECT name="nbPlace" size="1">
				<c:forEach begin="1" end="${lCatPlaceDispo[i.index]}" step="1" var="j"> 
					<OPTION> ${j}
				</c:forEach>
				
				</SELECT>
				
				
				<input type="submit" value="Acheter">
				<input type="hidden" name="client" value="${emailClient}" /> 
				<input type="hidden" name="spectacle" value="${nomSpec}" /> 
				<input type="hidden" name="nomCat" value="${cp.nomCat}" /> 
				</FORM>
			</p> 
		
		
		
		
		</c:forEach> 

</body> 
</html>

