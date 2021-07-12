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

import com.ecw.beans.ProductBean;
import com.ecw.dao.ProductDaoImpl;

/**
 * Servlet implementation class UpdateProductSrv
 */
@WebServlet("/UpdateProductSrv")
public class UpdateProductSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdateProductSrv() {
		super();

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userType = (String) session.getAttribute("usertype");
		String userName = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");

		if (userType == null || !userType.equals("Admin")) {

			response.sendRedirect("loginFirst.jsp");

		}

		else if (userName == null || password == null) {
			response.sendRedirect("loginFirst.jsp");
		}

		String requestURI = request.getRequestURL().toString();

		String prodId = request.getParameter("prodid");

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Login success
		PrintWriter pw = response.getWriter();
		response.setContentType("text/html");

		HttpSession session = request.getSession();
		String userType = (String) session.getAttribute("usertype");
		String userName = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		
		if (userType == null || !userType.equals("Admin")) {
			response.sendRedirect("loginFirst.jsp");
		} else if (userName == null || password == null) {
			response.sendRedirect("loginFirst.jsp");
		}
		String prodId1 = request.getParameter("prodid");
		if (prodId1 != null && !prodId1.trim().isEmpty()) {

			RequestDispatcher rd = request.getRequestDispatcher("updateProduct.jsp?prodid=" + prodId1 + "");
			rd.include(request, response);
		} else {
			String prodId = request.getParameter("pid");
			String prodName = request.getParameter("name");
			String prodType = request.getParameter("type");
			String prodInfo = request.getParameter("info");
			double prodPrice = Double.parseDouble(request.getParameter("price"));
			int prodQuantity = Integer.parseInt(request.getParameter("quantity"));

			ProductBean product = new ProductBean();
			product.setProdId(prodId);
			product.setProdName(prodName);
			product.setProdInfo(prodInfo);
			product.setProdPrice(prodPrice);
			product.setProdQuantity(prodQuantity);
			product.setProdType(prodType);

			ProductDaoImpl dao = new ProductDaoImpl();

			String status = dao.updateProductWithoutImage(prodId, product);

			RequestDispatcher rd = request.getRequestDispatcher("updateProduct.jsp?prodid=" + prodId + "");

			rd.include(request, response);

			pw.println("<script>document.getElementById('message').innerHTML='" + status + "'</script>");
		}

	}

}
