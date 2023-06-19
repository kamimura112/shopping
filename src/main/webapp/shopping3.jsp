<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ page import="java.util.*" %>
<%@ page import="bean.*" %>
<jsp:useBean id="obj" class="bean.BeanCartDB"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>atoz</title>
</head>
<body>
<%
String stringBuy = request.getParameter("selectBuy"); 
String toId = request.getParameter("nameId");
String value = request.getParameter("clickValue");
List<BeanCartDB> list = obj.DBtoList(toId, stringBuy, value);
%>
<h4>現在のカートの中を表示しています。</h4>

<%
int count = 0;
int sum = 0;
for(int i=0; i<list.size();i++) {
	obj=list.get(i);
if(obj.getAfter() == null){
%>

<%=obj.getId()%>.
<%=obj.getName()%>、
<%=obj.getComment()%>。
\<%=obj.getPrice()%>、
購入数:<%=obj.getBuy()%>
<img src="images/<%=obj.getPhoto()%>" width = "100" height = "100" />

<button onclick="location.href='shopping3.jsp?clickValue=delete&nameId=<%=obj.getId()%>'">削除</button>
<button onclick="location.href='shopping3.jsp?clickValue=later&nameId=<%=obj.getId()%>'">後で買うリストへ</button>
<br>


<%count += obj.getBuy();
sum += obj.getPrice() * obj.getBuy();
}
}%>
<%if(count == 0) {%>
	<p>カートの中には何も入っていません。</p>
<%}else{%>
購入数の合計：<%=count%>点、\<%=sum%>
<%} %>


<h4>◆◇◆◇◆◇◆ここから下は、後で買うリストです。◆◇◆◇◆◇◆</h4>
<%
int countLater= 0;
for(int i=0; i<list.size();i++) {
	obj=list.get(i);
if(obj.getAfter() != null){
%>

<%=obj.getId()%>.
<%=obj.getName()%>、
<%=obj.getComment()%>。
\<%=obj.getPrice()%>、
購入数:<%=obj.getBuy()%>
<img src="images/<%=obj.getPhoto()%>" width="100" height="100" />

<button onclick="location.href='shopping3.jsp?clickValue=delete&nameId=<%=obj.getId()%>'">削除</button>
<button onclick="location.href='shopping3.jsp?clickValue=cart&nameId=<%=obj.getId()%>'">カートに戻す</button>
<br>
<%countLater += obj.getBuy();
}
}%>
<%if(countLater == 0) {%>
	<p>後で買うリストの中には何も入っていません。</p>
<%}else{%>
後で買うリストの合計：<%=countLater%>点
<%} %>
<br>
<a href ="shopping1.jsp">買い物を続ける</a><a href ="shopping4.jsp">レジに進む</a>
</body>
</html>