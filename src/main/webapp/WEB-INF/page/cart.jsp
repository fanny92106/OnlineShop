<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cart</title>
<link rel="icon" type="image/x-icon"
	href="<c:url value="/resource/images/favicon.png"/>" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<script src="<c:url value="/resource/js/productController.js"/>"></script>
</head>
<body>
	<%@ include file="navbar.jsp"%>
	<div class="container"
		style="width: 1145px; margin-top: 20px; margin-bottom: 180px;">
		<div ng-app="myapp" ng-controller="myController"
			style="margin-bottom: 30px">
			<div ng-init="getCart(${cartId})">
				<br><h4>Products Purchased</h4>
				<div>
					<a href="/onlineShop/cart/removeAllItems/${cart.id}"
						class="btn btn-primary pull-left" ng-click="clearCart()"
						style="margin-top: 15px; margin-left: 0px"> <span
						class="glyphicon glyphicon-remove"></span> Clear Cart
					</a>
				</div>
				<div>
					<a href="/onlineShop/order/${cart.id}" class="btn btn-primary pull-left"
						style="margin-top: 15px; margin-left: 5px"> <span
						class="glyphicon glyphicon-shipping-cart"> </span>Check Out
					</a>
				</div>
				<c:set var="calculateTotal" value="${0}" />
				<table class="table table-hover" id="productList">
					<thead>
						<tr>
							<th>Product</th>
							<th>Product Name</th>
							<th>Quantity</th>
							<th>Price</th>
							<th>Total Price</th>
							<th></th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach items="${items}" var="item">
							<c:set var="calculateTotal" value="${calculateTotal + item.totalPrice}" />
							<tr>
							<td>
						<img
							src="<c:url value="/resource/images/products/${item.product.id}.jpg" />"
							style="width: 60px; height: 50px;"/>
							</td>
								<td>${item.product.productName}</td>
								<td>${item.quantity}</td>
								<td>${item.product.productPrice}</td>
								<td>${item.totalPrice}</td>
								<td><a href="/onlineShop/cart/removeCartItem/${item.id}" class="btn btn-danger"
									ng-click="removeFromCart(cart.id)" style="margin-top: 0px;"><span
										class="glyphicon glyphicon-trash"></span>remove</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<h4>Total Price: $${calculateTotal}</h4>
			</div>
			<c:url value="/getAllProducts" var="url"></c:url>
			<a href="${url}" class="btn btn-default" style="margin-left: 0px">Continue
				Shopping</a>
		</div>
	</div>

	<div id="footer">
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
</body>
</html>