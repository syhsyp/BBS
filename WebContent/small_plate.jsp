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
String id = request.getParameter("id");
String name = request.getParameter("name");
users user = (users)session.getAttribute("user");
if (user != null) { %>
	<a href="newTopic.jsp"><button>发帖</button></a>
<%}
%>
<c:set var="pid" value="<%=id%>"/>
<sql:setDataSource var="clients" driver="com.mysql.jdbc.Driver"
	url="jdbc:mysql://localhost:3306/clients"
    user="root"  password="syh123456"/>
<sql:query dataSource="${clients}" var="results">
SELECT * FROM topics WHERE parent_id = "${pid}";
</sql:query>
<h1><%=name%></h1>
<table>
	<tr>
		<td>
			标题
		</td>
		<td>
			创建者
		</td>
		<td>
			最近更新日期
		</td>
	</tr>
	<c:forEach var="topic" items="${results.rows}">
	
	<tr>
		<td>
			<a href="singleTopic.jsp?id=${topic.id}">${topic.title}</a>
		</td>
		<td>
			<sql:query dataSource="${clients}" var="temp">
			SELECT * FROM users WHERE id = "${topic.creater_id}";
			</sql:query>
			${temp.rows[0].full_name}
		</td>
		<td>
			${topic.last_modified_date}
		</td>
	</tr>
	</c:forEach>
</table>
</body>
</html>