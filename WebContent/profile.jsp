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
<title>Profile</title>
</head>
<body>
<%
	users user = (users)session.getAttribute("user");
	if (user==null) {
		response.sendRedirect("HomePage.jsp");
	}
%>
<c:out value="${user.name()}"/><br />
<c:out value="${user.birthdate()}"/><br />
<c:out value="${user.email()}"/><br />
<c:out value="${user.last()}"/><br />
<a href="AllTopics.jsp"><button>所有帖子</button></a>
<a href="AllReplies.jsp"><button>所有回复</button></a>
<a href="update.jsp"><button>更改个人信息</button></a>
<a href="HomePage.jsp"><button>首页</button></a>
</body>
</html>