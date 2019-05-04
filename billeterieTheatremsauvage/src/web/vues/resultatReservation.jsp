<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>Recapitulatif achat de billet</title>
</head>
<body>
	<h1>Votre dernière demande d'achat de billets</h1>

	<ul>
		<li>client : ${emailClient} </li>
		<li>Spectacle : ${nomSpec} (prix de base : ${prixDeBase})</li>
		<li>demande de ${nbPlace} place(s) dans la catégorie ${nomCat})</li>
		<li>Bilan commande  : ${bool}</li>
		
	</ul>
	
	
	<h2>Résumé de tous les billets du client ${emailClient} pour le spectacle ${nomSpec} </h2>
	
		<ul>
	<c:forEach items="${requestScope.lbillets}" var="billet"> 

			<li>Billet no : ${billet.idBillet}, place : ${billet.placeBillet.idPlace}, prix:${billet.prixBillet}</li>
	</c:forEach> 
		</ul>
</body> 
</html>

