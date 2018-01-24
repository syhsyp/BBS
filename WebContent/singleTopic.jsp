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
<title>Insert title here</title>
</head>
<body>
<% 
	users user = (users)session.getAttribute("user");
	String user_id = null;
	if(user != null) {
		String name = user.name();
		String last = user.last();
		user_id = user.id(); 
%>
		<div>Welcome <%=name %> Last login date is: <%=last%></div>
		<div id="profile"><a href="profile.jsp">Profile</a></div>
		<div id="logout"><a href="Logout">Log Out</a></div>
	<% } else { %>
		<div id="login"><a href="login.jsp">Log In</a></div>
		<div id="regist"><a href="SignUp.jsp">Sign Up</a></div>
	<% } %>
<%
String id = request.getParameter("id");
%>
<c:set var="tid" value="<%=id%>"/>
<c:set var="uid" value="<%=user_id%>"/>
<sql:setDataSource var="clients" driver="com.mysql.jdbc.Driver"
	url="jdbc:mysql://localhost:3306/clients"
    user="root"  password="syh123456"/>
<sql:query dataSource="${clients}" var="results">
SELECT * FROM topics WHERE id = "${tid}";
</sql:query>
Title：<c:out value="${results.rows[0].title}"/><br />
<sql:query dataSource="${clients}" var="temp">
SELECT * FROM users WHERE id = "${results.rows[0].creater_id}";
</sql:query>
Creator：<c:out value="${temp.rows[0].full_name}"/><br />
Content：<c:out value="${results.rows[0].content}"/><br />
Date Created：<c:out value="${results.rows[0].created_date}"/><br />
<br />
<sql:query dataSource="${clients}" var="results_r">
SELECT * FROM reply WHERE parent_id = "${tid}" ORDER BY created_date;
</sql:query>
<table>
	<tr>
		<td>
			Replier
		</td>
		<td>
			Reply
		</td>
	</tr>
	<c:forEach var="reply" items="${results_r.rows}">
	
	<tr>
		<td>
			<sql:query dataSource="${clients}" var="temp1">
			SELECT * FROM users WHERE id = "${reply.replier_id}";
			</sql:query>
			${temp1.rows[0].full_name}
		</td>
		<td>
			${reply.content}
		</td>
		<c:if test="${reply.replier_id == uid }">
			<td><a href="DeleteReply?id=<%=id%>&reply_id=${reply.id}"><button>删除</button></a></td>
		</c:if>
	</tr>
	</c:forEach>
</table>

<br />
<br />
<form action="Reply?id=<%=id%>" method="post">  
	<table>  
		<tr>  
	        <td>Reply:</td>  
	        <td>  
	            <input type="text" name="content">  
	        </td>  
   	 	</tr>  
    	<tr>  
	        <td colspan="2">  
	            <input type="submit" value="reply">  
	        </td>
    	</tr>  
	</table>  
</form>
</body>
</html>