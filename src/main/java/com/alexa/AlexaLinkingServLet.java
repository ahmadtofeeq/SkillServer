package com.alexa;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/redirect")
public class AlexaLinkingServLet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doGet");
		String state = (String) request.getAttribute("state");
		String client_id=(String) request.getAttribute("client_id");
		String response_type=(String) request.getAttribute("response_type");
		System.out.println(client_id+"-"+state+"-"+response_type);
		response.setHeader("Content-Type", "application/x-www-form-urlencoded");
		HttpSession session = request.getSession();
		session.setAttribute("token_type ", "client_credentials");
		session.setAttribute("access_token ",
				"ya29.GluEBTVcBORyyjOvUoOE9p6OHKn8B6NyJL7bHdmmDY65KNwIDUIDO6XKfjWd86QwMPmkucieHyoBDl2ql8vZVqfwVXcexR3M7TyGvVwAhJRqAKYYJPofqN0gwsDv");
		session.setAttribute("state ", request.getAttribute("state"));
		response.setStatus(301);
		response.sendRedirect("https://layla.amazon.com/spa/skill/account-linking-status.html?vendorId=M1TL25RMCNLY4Z");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(request, response);
//		String state = (String) request.getAttribute("state");
//		String password=(String) request.getAttribute("password");
//		response.setHeader("Content-Type", "application/x-www-form-urlencoded");
//		HttpSession session = request.getSession();
//		session.setAttribute("token_type ", "client_credentials");
//		session.setAttribute("access_token ",
//				"ya29.GluEBTVcBORyyjOvUoOE9p6OHKn8B6NyJL7bHdmmDY65KNwIDUIDO6XKfjWd86QwMPmkucieHyoBDl2ql8vZVqfwVXcexR3M7TyGvVwAhJRqAKYYJPofqN0gwsDv");
//		session.setAttribute("state ", request.getAttribute("state"));
//		response.setStatus(301);
//		response.sendRedirect("https://layla.amazon.com/api/skill/link/M1TL25RMCNLY4Z");
		System.out.println("post");
		String state = (String) request.getAttribute("state");
		String client_id=(String) request.getAttribute("client_id");
		String response_type=(String) request.getAttribute("response_type");
		System.out.println(client_id+"-"+state+"-"+response_type);
		response.setHeader("Content-Type", "application/x-www-form-urlencoded");
		HttpSession session = request.getSession();
		session.setAttribute("token_type ", "client_credentials");
		session.setAttribute("access_token ",
				"ya29.GluEBTVcBORyyjOvUoOE9p6OHKn8B6NyJL7bHdmmDY65KNwIDUIDO6XKfjWd86QwMPmkucieHyoBDl2ql8vZVqfwVXcexR3M7TyGvVwAhJRqAKYYJPofqN0gwsDv");
		session.setAttribute("state ", request.getAttribute("state"));
		response.setStatus(301);
		response.sendRedirect("https://layla.amazon.com/spa/skill/account-linking-status.html?vendorId=M1TL25RMCNLY4Z");
	}
}
