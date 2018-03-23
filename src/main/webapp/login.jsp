<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>
</head>
<body>
	<a href="registration.jsp">Registration</a>|

	<%
		String state = request.getParameter("state");
	%>
	<%=state%>
	<%
		String client_id = request.getParameter("client_id");
	%>
	<%=client_id%>
	<%
		String response_type = request.getParameter("response_type");
	%>
	<%=response_type%>
	<%
		String scope = request.getParameter("scope ");
	%>
	<%=scope%>


</body>

<h3>Login Form</h3>
<form action="redirect" method="post">
	Email:<input type="text" name="email" /><br /> <br /> Password:<input
		type="password" name="password" /><br /> <br /> <input
		type="submit" value="login" />
</form>


</html>