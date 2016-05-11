<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>TTV - Checkout</title>
  <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css" />" />
  <script type="text/javascript" src="<c:url value="/resources/js/jquery.js" />"></script>
  <c:set var="projectName" value="/hibernate" />
</head>
<body>

  <section class="container">
      <div class="w-container">
         <div class="w-row">
            <div class="w-col w-col-9">
               <h1>Checkout</h1>
            </div>
            <div class="w-col w-col-3">
               <div>User ten1, <a href="${projectName}/logout">logout</a></div>
            </div>
         </div>	  
         <div class="w-row">
            <div class="w-col w-col-1">
               <div><strong>No</strong></div>
            </div>
            <div class="w-col w-col-5">
               <div><strong>Product</strong></div>
            </div>
            <div class="w-col w-col-2">
               <div><strong>Price</strong></div>
            </div>
            <div class="w-col w-col-2">
               <div><strong>Quantity<br></strong></div>
            </div>
            <div class="w-col w-col-2">
               <div><strong>Total Price</strong></div>
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
	            <div class="w-col w-col-2">
	               <div>${product.salePriceString }</div>
	            </div>
	            <div class="w-col w-col-2">
	               <div>${product.stock_quantity }</div>
	            </div>
	            <div class="w-col w-col-2">
	               <div>100$</div>
	            </div>
	         </div>
         
         </c:forEach>
         <!-- <div class="w-row">
            <div class="w-col w-col-1">
               <div>1</div>
            </div>
            <div class="w-col w-col-5">
               <div>Pro name 3</div>
            </div>
            <div class="w-col w-col-2">
               <div>10$</div>
            </div>
            <div class="w-col w-col-2">
               <div>3</div>
            </div>
            <div class="w-col w-col-2">
               <div>30$</div>
            </div>
         </div>
         <div class="w-row">
            <div class="w-col w-col-1">
               <div>2</div>
            </div>
            <div class="w-col w-col-5">
               <div>Pro name 1</div>
            </div>
            <div class="w-col w-col-2">
               <div>10$</div>
            </div>
            <div class="w-col w-col-2">
               <div>3</div>
            </div>
            <div class="w-col w-col-2">
               <div>30$</div>
            </div>
         </div>
         <div class="w-row">
            <div class="w-col w-col-1">
               <div>3</div>
            </div>
            <div class="w-col w-col-5">
               <div>Pro name 3</div>
            </div>
            <div class="w-col w-col-2">
               <div>10$</div>
            </div>
            <div class="w-col w-col-2">
               <div>3</div>
            </div>
            <div class="w-col w-col-2">
               <div>30$</div>
            </div>
         </div> -->
         <div class="w-row">
            <div class="w-col w-col-1"></div>
            <div class="w-col w-col-5"></div>
            <div class="w-col w-col-2"></div>
            <div class="w-col w-col-2">
               <div><strong>Total Price:</strong></div>
            </div>
            <div class="w-col w-col-2">
               <div><strong>30$</strong></div>
            </div>
         </div>
         <div class="w-row">
            <div class="w-col w-col-9">
               <div class="w-form">
                  <form id="email-form" name="email-form" data-name="Email Form">
                     <div>Please enter coupon if needed</div>
                     <input class="w-input" id="coupon-2" type="text" placeholder="Coupon code" name="coupon-2" data-name="Coupon 2">
					 <input class="w-button" type="submit" value="Check Coupon" data-wait="Please wait...">
                  </form>
                  <div class="w-form-done">
                     <p>Thank you! Your submission has been received!</p>
                  </div>
                  <div class="w-form-fail">
                     <p>Oops! Something went wrong while submitting the form :(</p>
                  </div>
               </div>
            </div>
            <div class="w-col w-col-3" vertical-align="true">
			   
			</div>
         </div>
         <div class="w-row">
            <div class="w-col w-col-9">
               
            </div>
            <div class="w-col w-col-3" vertical-align="true">
			   
			</div>
         </div>		 
         <div class="w-row">
            <div class="w-col w-col-9">
               <div class="w-form">
                  <form id="email-form" name="email-form" data-name="Email Form">
                     <div>Enter address information&nbsp;(*)</div>
                     <textarea class="w-input" id="field" placeholder="Enter your address" name="field"></textarea>
                  </form>
                  <div class="w-form-done">
                     <p>Thank you! Your submission has been received!</p>
                  </div>
                  <div class="w-form-fail">
                     <p>Oops! Something went wrong while submitting the form :(</p>
                  </div>
               </div>
            </div>
            <div class="w-col w-col-3">
               
            </div>
         </div>
		 
         <div class="w-row">
            <div class="w-col w-col-9">
               
            </div>
            <div class="w-col w-col-3">
                <div class="w-form">
                  <form id="email-form" name="email-form" data-name="Email Form" action="${projectName }/listproduct">
                     <div class="w-row">
                        <div class="w-col w-col-8"><input class="w-button" type="submit" value="Continue Shopping" data-wait="Please wait..."></div>
                        <div class="w-col w-col-4"><input class="w-button" type="button" onclick="done()" value="Register Order" data-wait="Please wait..." style="background-color:blue"></div>
                     </div>
                  </form>
                  <div id="done" class="w-form-done">
                     <p>Your order id is 1 has been received and is currently in verification process.</p>
                  </div>
                  <div id="fail" class="w-form-fail">
                     <p>Oops! Something went wrong while submitting the form :(</p>
                  </div>
               </div>
			</div>
         </div>	
		 
      </div>

  </section>
  
</body>
</html>
<script type="text/javascript">
function done() {
    alert('Your order id is 1 has been received and is currently in verification process');
    
}
</script>