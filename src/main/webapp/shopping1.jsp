<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ page import="java.util.*" %>
<%@ page import="bean.*" %>
<jsp:useBean id="obj" class="bean.BeanShopDB"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">  
<title>atoz</title>
<style>

</style>
</head>
<body>




<button onclick ="location.href='shopping1.jsp?toOrder=asc'">価格の安い順</button>
<button onclick ="location.href='shopping1.jsp?toOrder=desc'">価格の高い順</button>  
<form action = "shopping1.jsp" method = "post">
<input type = "text" name = "keyWords">
<button>検索</button>
</form>
<br>




<%
String text = request.getParameter("keyWords");
String order = request.getParameter("toOrder");
List<BeanShopDB> list = obj.DBtoList(text, order);
for(int i=0;i<list.size();i++){ 
obj = list.get(i);%>
<%=obj.getId()%>、
<a href = "shopping2.jsp?toId=<%=obj.getId()%>&toName=<%=obj.getName()%>&toComment=<%=obj.getComment()%>&toPrice=<%=obj.getPrice()%>&toStock=<%=obj.getStock()%>&toPhoto=<%=obj.getPhoto()%>"><%=obj.getName()%></a>、

\<%=obj.getPrice()%>
<a href = "shopping2.jsp?toId=<%=obj.getId()%>&toName=<%=obj.getName()%>&toComment=<%=obj.getComment()%>&toPrice=<%=obj.getPrice()%>&toStock=<%=obj.getStock()%>&toPhoto=<%=obj.getPhoto()%>"><img src="images/<%=obj.getPhoto()%>" width = "100" height = "100" /></a><br>
<%}
%>
</body>
</html>