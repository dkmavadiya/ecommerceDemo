<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.ecw.dao.*,com.ecw.beans.*,java.util.*,javax.servlet.ServletOutputStream,java.io.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Divyesh shop</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
   <link rel="stylesheet" href="css/changes.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
     <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<body>

	<%
	/* Checking the user credentials */
		String userName = (String)session.getAttribute("username");
		String password = (String)session.getAttribute("password");
	
		if(userName == null || password==null){
	
			response.sendRedirect("loginFirst.jsp");
		}	
		
		
	%>



<%@ include file="userHeader.jsp" %>

<!-- Start of Product Items List -->
<div class="products" style="background-color: #E6F9E6;">
<div class="row text-center">

  <%
  
  	ProductDaoImpl prodDao = new ProductDaoImpl(); 
  	
  	List<ProductBean> products = new ArrayList<ProductBean>();
  	
  	products = prodDao.getAllProducts();
  	
  	for(ProductBean product : products){
  	
  		
  %>
  
  <div class="col-sm-4">
    <div class="thumbnail">
      <img src="./ShowImage?pid=<%=product.getProdId() %>" alt="Product" style="height:200px; max-width:200px">
      <p class="productname"><%=product.getProdName() %> ( <%=product.getProdId() %> ) </p>
      <p class="productinfo"><%=product.getProdInfo() %></p>
      <p class="price">Rs <%=product.getProdPrice() %> </p>
      <form method="post">
      	<button type="submit" formaction="./AddtoCart?uid=<%=userName %>&pid=<%=product.getProdId() %>&pqty=1">Add to Cart</button>&nbsp;&nbsp;&nbsp;
      	<button type="submit" formaction="./AddtoCart?uid=<%=userName %>&pid=<%=product.getProdId() %>&pqty=1">Buy Now</button>
      </form>
    </div>
  </div>
  
  <%
  
  	}
  
  %>
  
</div>
</div>
<!-- ENd of Product Items List -->


<%@ include file="footer.html" %>

</body>
</html>