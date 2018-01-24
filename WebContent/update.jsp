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
	if (user == null) {
		response.sendRedirect("HomePage.jsp");
	}
	String pwd = user.pwd();
	String fullname = user.name();
	java.sql.Date birthdate = user.birthdate();
	String email = user.email();
	
%>
<form action="update" method="post">  
	<table align="center">   
    	<tr>  
	        <td>密码：</td>  
	        <td>  
	            <input type="text" name="pwd" value=<%=pwd %>>  
	        </td>  
    	</tr>  
    	<tr>  
	        <td>姓名：</td>  
	        <td>  
	            <input type="text" name="full_name" value=<%=fullname %>>  
	        </td>  
    	</tr>
    	<tr>  
	        <td>生日：</td>  
	        <td>  
	        	<input type="date" name="birthdate" value=<%=birthdate %>>
	        </td>  
    	</tr>
    	<tr>  
	        <td>Email：</td>  
	        <td>  
	            <input type="text" name="email" value=<%=email %>>  
	        </td>  
    	</tr>
    	<tr>  
	        <td colspan="2">  
	            <input type="submit" value="提交"/>  
	        </td>
    	</tr>  
	</table>  
</form>
<a href="HomePage.jsp"><button>首页</button></a>
</body>
</html>