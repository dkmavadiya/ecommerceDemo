package com.ecw.srv;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ecw.beans.UserBean;
import com.ecw.dao.UserDaoImpl;

/**
 * Servlet implementation class LoginSrv
 */
@WebServlet("/LoginSrv")
public class LoginSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginSrv() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		String userType = request.getParameter("usertype");

		PrintWriter pw = response.getWriter();
		response.setContentType("text/html");
		String status = "Login Denied! Invalid Username or password.";

		UserDaoImpl udao = new UserDaoImpl();
		status = udao.isValidCredential(userName, password,userType);

		if (status.equalsIgnoreCase("valid")) {
			// valid user
			UserBean user = udao.getUserDetails(userName, password);
			HttpSession session = request.getSession();
			session.setAttribute("userdata", user);
			session.setAttribute("username", userName);
			session.setAttribute("password", password);
			session.setAttribute("usertype", userType);
			session.setMaxInactiveInterval(30 * 60);

			if (userType.equals("Admin") || userType.equals("Seller")) {
				RequestDispatcher rd = request.getRequestDispatcher("adminViewProduct.jsp");
				rd.forward(request, response);

			} else {
				RequestDispatcher rd = request.getRequestDispatcher("userHome.jsp");
				rd.forward(request, response);
			}

		} else {
			// invalid user;
			RequestDispatcher rd = request.getRequestDispatcher("login.html");
			rd.include(request, response);
			pw.println("<script>document.getElementById('message').innerHTML='" + status + "'</script>");
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
