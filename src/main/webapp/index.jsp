<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="com.multi.oauth2client.*"%>
<%@ page import="java.util.*"%>
<%
	HashMap<String, String> map = new HashMap<String, String>();
	map.put("client_id", Settings.CLIENT_ID);
	map.put("redirect_uri", Settings.REDIRECT_URI);
	map.put("state", "code");
	map.put("scope", Settings.SCOPE);
	map.put("response_type ", "code");
	session.setAttribute("state", map.get("state"));

	String url = Settings.AUTHORIZE_URL + "?" + Settings.getParamString(map, false);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>oauth2provider test</title>
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
</head>
<body>
	Client ID : 9980228a-1fd8-4501-be77-ce8e98eed18c
	<br> Client Secret : 8117a5d75e9909eb7858b5638803d72c707fb744
	<br> Client Name : TestApp1
	<br> Description : TestApp1 입니다.
	<br> Client Type : W
	<br> Client URL : http://localhost:8080
	<br> Redirect URI :
	http://localhost:8080/oauth2client/callback.jsp
	<br> Scope : reademail,sendemail,readboard,personalinfo,calendar
	<br> 
	<% String success = request.getParameter("state");%>
	<%=success%>
	<%-- <c:out value="${success}"/>;
	if(success!=null) {
	<c:out value="${success}"/>;
	} --%>
	<a href="<%=url%>">Authorize!!</a>
</body>
</html>