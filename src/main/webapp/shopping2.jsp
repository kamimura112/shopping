<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>atoz</title>
</head>
<body>
<%
String id = request.getParameter("toId");
String stringPrice = request.getParameter("toPrice");
int price = Integer.parseInt(stringPrice);
String name = request.getParameter("toName");
String stringStock = request.getParameter("toStock");
int stock = Integer.parseInt(stringStock);
String photo = request.getParameter("toPhoto");
%>

選んだ商品は、<%=id%>,<%=price%>, <%=name%>です。<img src = "images/<%=photo%>" width="70" height="70">
<br>
購入個数の選択
<form action ="shopping3.jsp" method="post">
<select name = "selectBuy">
<%for(int i=1;i<=stock; i++){ %>
<option value="<%=i%>"><%=i%></option>
<%} %>
</select>
<input type = hidden name ="nameId" value = <%=id%>>
<button>カートに入れる</button>
</form>
</body>
</html>