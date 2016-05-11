<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>TTV - List of product</title>
  <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css" />" />
  <script type="text/javascript" src="<c:url value="/resources/js/jquery.js" />"></script>
  <script type="text/javascript">
  
  	function checkQuantity(target, quantity, productName) {
  		var quantityInput = $(target).parent().find(".w-input").val();
  		
  		if ( quantityInput == "" || isNaN(quantityInput)) return;
  		if ( parseInt(quantity) < parseInt(quantityInput) ) {
  			alert("The number of " + productName + " is not enough to sell.");
  		} else {
  			$(target).parent().submit();
  		}
  		
  	}
  
  </script>
  <c:set var="projectName" value="/hibernate" />
</head>
<body>
  <section class="container">
      <div class="w-container">
         <div class="w-row">
            <div class="w-col w-col-9">
               <h1>List of product</h1>
            </div>
            <div class="w-col w-col-3">
               <div>User ${userName }, <a href="${projectName}/logout">logout</a></div>
            </div>
         </div>
         <div class="w-row">
            <div class="w-col w-col-1">
               <div><strong>No</strong></div>
            </div>
            <div class="w-col w-col-5">
               <div><strong>Product</strong></div>
            </div>
            <div class="w-col w-col-3">
               <div><strong>Price</strong></div>
            </div>
            <div class="w-col w-col-3">
               <div><strong>Quantity</strong></div>
            </div>
         </div>
         <c:forEach items="${products}" var="product">
         	<div class="w-row">
	            <div class="w-col w-col-1">
	               <div>${product.id }</div>
	            </div>
	            <div class="w-col w-col-5">
	               <div>${product.name }</div>
	            </div>
	            <div class="w-col w-col-3">
	               <div>${product.salePriceString }</div>
	            </div>
	            <div class="w-col w-col-3">
	               <div class="w-form">
	                  <form data-name="Add Cart Form" action="${projectName}/cartdetail/${product.id }" method="get">
	                  	<input class="w-input" id="pro1_quantityuantity" type="text" placeholder="Enter product's quantity" name="pro1_quantity" data-name="pro1_quantity" style="width: 180px;"/>
	                  	<input class="w-button" onclick="checkQuantity(this,'${product.stock_quantity }', '${product.name }');" value="Add to Cart" data-wait="Please wait..."/>
	                  </form>
	                  <div class="w-form-done">
	                     <p>Thank you! Your submission has been received!</p>
	                  </div>
	                  <div class="w-form-fail">
	                     <p>Oops! Something went wrong while submitting the form :(</p>
	                  </div>
	               </div>
	            </div>
	         </div>
         </c:forEach>
         <!-- <div class="w-row">
            <div class="w-col w-col-1">
               <div>1</div>
            </div>
            <div class="w-col w-col-5">
               <div>Pro name 1</div>
            </div>
            <div class="w-col w-col-3">
               <div>303$</div>
            </div>
            <div class="w-col w-col-3">
               <div class="w-form">
                  <form id="add-cart-form" name="add-cart-form" data-name="Add Cart Form" action="cartdetail.html"><input class="w-input" id="pro1_quantityuantity" type="text" placeholder="Enter product's quantity" name="pro1_quantity" data-name="pro1_quantity" style="width: 180px;"><input class="w-button" type="submit" value="Add to Cart" data-wait="Please wait..."></form>
                  <div class="w-form-done">
                     <p>Thank you! Your submission has been received!</p>
                  </div>
                  <div class="w-form-fail">
                     <p>Oops! Something went wrong while submitting the form :(</p>
                  </div>
               </div>
            </div>
         </div>
         <div class="w-row">
            <div class="w-col w-col-1">
               <div>2</div>
            </div>
            <div class="w-col w-col-5">
               <div>Pro name 2</div>
            </div>
            <div class="w-col w-col-3">
               <div>303$</div>
            </div>
            <div class="w-col w-col-3">
               <div class="w-form">
                  <form id="add-cart-form" name="add-cart-form" data-name="Add Cart Form" action="cartdetail.html"><input class="w-input" id="pro1_quantityuantity" type="text" placeholder="Enter product's quantity" name="pro1_quantity" data-name="pro1_quantity" style="width: 180px;"><input class="w-button" type="submit" value="Add to Cart" data-wait="Please wait..."></form>
                  <div class="w-form-done">
                     <p>Thank you! Your submission has been received!</p>
                  </div>
                  <div class="w-form-fail">
                     <p>Oops! Something went wrong while submitting the form :(</p>
                  </div>
               </div>
            </div>
         </div>
         <div class="w-row">
            <div class="w-col w-col-1">
               <div>3</div>
            </div>
            <div class="w-col w-col-5">
               <div>Pro name 3</div>
            </div>
            <div class="w-col w-col-3">
               <div>303$</div>
            </div>
            <div class="w-col w-col-3">
               <div class="w-form">
                  <form id="add-cart-form" name="add-cart-form" data-name="Add Cart Form" action="cartdetail.html"><input class="w-input" id="pro1_quantityuantity" type="text" placeholder="Enter product's quantity" name="pro1_quantity" data-name="pro1_quantity" style="width: 180px;"><input class="w-button" type="submit" value="Add to Cart" data-wait="Please wait..."></form>
                  <div class="w-form-done">
                     <p>Thank you! Your submission has been received!</p>
                  </div>
                  <div class="w-form-fail">
                     <p>Oops! Something went wrong while submitting the form :(</p>
                  </div>
               </div>
            </div>
         </div> -->
         <div class="w-row">
            <div class="w-col w-col-9"></div>
            <div class="w-col w-col-3">
               <div class="w-form">
                  <form id="email-form" name="email-form" data-name="Email Form" action="cartdetail.html"><input class="w-button" type="submit" value="Checkout" data-wait="Please wait..." style="background-color:blue"></form>
                  <div class="w-form-done">
                     <p>Thank you! Your submission has been received!</p>
                  </div>
                  <div class="w-form-fail">
                     <p>Oops! Something went wrong while submitting the form :(</p>
                  </div>
               </div>
            </div>
         </div>
      </div>

  </section>
</body>
</html>