<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="Login" method="post">  
	<table align="center">  
		<tr>
		<% String error = (String)session.getAttribute("error");
			if (error != null) { %>
			<%=error %>	
		<% } %>
		</tr>
		<tr>  
	        <td>用户名：</td>  
	        <td>  
	            <input type="text" name="name"/>  
	        </td>  
   	 	</tr>  
    	<tr>  
	        <td>密码：</td>  
	        <td>  
	            <input type="password" name="pwd"/>  
	        </td>  
    	</tr>  
    	<tr>  
	        <td colspan="2">  
	            <input type="submit" value="登录"/>
	        </td>
    	</tr>
	</table>  
</form>
<a href="SignUp.jsp"><button>注册</button></a> 
<a href="HomePage.jsp"><button>首页</button></a> 
</body>
</html>