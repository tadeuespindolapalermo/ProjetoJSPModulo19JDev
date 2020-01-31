<jsp:useBean id="calcula" class="br.com.projetojsp.bean.UsuarioBean" type="br.com.projetojsp.bean.UsuarioBean" scope="page"/>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib prefix="myprefix" uri="WEB-INF/testetag.tld" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Página de Login</title>
		<link rel="stylesheet" href="resources/css/estilo.css">
	</head>
	<body>		
		<div class="login-page">
			<h3 style="text-align: center;">Projeto Didático</h3>
			<h1 style="text-align: center;">JSP - Servlet - JDBC</h1>
			<div style="text-align: center;">
				<span><b>USUÁRIO:</b> admin<br/><b>SENHA:</b> admin</span>
			</div><br>
  			<div class="form">
				<form action="LoginServlet" method="post" class="login-form">				
					Login:
					<input type="text" id="login" name="login" /><br>
					
					Senha:
					<input type="text" id="senha" name="senha"  /><br>
					
					<button type="submit">Logar</button>		
				</form>		
			</div>
			<h3 style="text-align: center;">
				<a style="text-decoration: none;" href="https://www.jdevtreinamento.com.br/formacao-java-web-profissional">Formação Java WEB</a>
			</h3>
		</div>
	</body>
</html>