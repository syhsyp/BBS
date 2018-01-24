<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ page import="java.io.*,java.util.*,java.sql.*,com.bruce.service.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>BBS</title>
</head>
<body>
<% 
	users user = (users)session.getAttribute("user");
	session.removeAttribute("error");
	if(user != null) {
		String name = user.name();
		String last = user.last();
%>
		<div>Welcome <%=name %> Last login date is: <%=last%></div>
		<div id="profile"><a href="profile.jsp">Profile</a></div>
		<div id="logout"><a href="Logout">Log Out</a></div>
	<% } else { %>
		<div id="login"><a href="login.jsp">Log In</a></div>
		<div id="regist"><a href="SignUp.jsp">Sign Up</a></div>
	<% } %>


<sql:setDataSource var="clients" driver="com.mysql.jdbc.Driver"
	url="jdbc:mysql://localhost:3306/clients"
    user="root"  password="syh123456"/>
<sql:query dataSource="${clients}" var="result_large">
SELECT * FROM plates_large;
</sql:query>

	<c:forEach var="large" items="${result_large.rows}">
	<div id="plates">
		<c:out value="${large.name}"/>
		<ul>
		<sql:query dataSource="${clients}" var="result_small">
		SELECT * FROM plates_small WHERE parent_id = ?;
		<sql:param value="${large.id}" />
		</sql:query>
		<c:forEach var="small" items="${result_small.rows}">
		<li id="plates_small">
			<a href="<c:url value="small_plate.jsp?id=${small.id}&name=${small.name}"/>"><c:out value="${small.name}"/></a>
		</li></c:forEach>
		</ul>
	</div>
	</c:forEach>
</body>
</html>