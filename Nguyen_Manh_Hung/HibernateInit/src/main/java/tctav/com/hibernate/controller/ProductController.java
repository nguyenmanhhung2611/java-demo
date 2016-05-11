package tctav.com.hibernate.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

import tctav.com.hibernate.domain.Product;
import tctav.com.hibernate.domain.User;
import tctav.com.hibernate.model.ProductModel;
import tctav.com.hibernate.service.ProductService;
import tctav.com.hibernate.utils.Common;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ProductController {
	
	@Autowired 
	private ProductService productService;
	
	@RequestMapping(value = "/listproduct", method = RequestMethod.GET)
	public String listproduct(Model model, HttpSession session) {
		User user = (User) session.getAttribute("userSession");
		
		List<Product> products = productService.findAll();
		
		model.addAttribute("userName", user.getUser_name());
		model.addAttribute("products", products);
		
		return "listproduct";
	}
	
	@RequestMapping(value = "/cartdetail/{productId}", method = RequestMethod.GET)
	public String cartdetail(Model model, HttpSession session, @PathVariable int productId, HttpServletRequest req) {
		User user = (User) session.getAttribute("userSession");
		
		Product product = productService.findById(productId);
		List<ProductModel> products = new ArrayList<ProductModel>();
		
		if ( product != null ) {
			String quantity = req.getParameter("pro1_quantity"); 
			if ( Common.isInteger(quantity) ) {
				if ( product.getSale_price() < Integer.parseInt(quantity) ) {
					model.addAttribute("Error", "The number of " + product.getName() + " is not enough to sell.");
					
				} else {
					product.setStock_quantity(Integer.parseInt(quantity));
					String userId = String.valueOf(user.getId());
					if(session.getAttribute("products_" + userId) != null) {
						Type listType = new TypeToken<List<Product>>() {}.getType();
						products = new Gson().fromJson(session.getAttribute("products_" + userId).toString(), listType);
						
						products.add(new ProductModel(product));
						session.setAttribute("products_" + userId, new Gson().toJsonTree(products));
					
						//productService.cardDetail(user.getId(), product);
						model.addAttribute("products", products);
						
					} else {
						products.add(new ProductModel(product));
						session.setAttribute("products_" + userId, new Gson().toJsonTree(products));
					}
				}
			}
		}
		
		model.addAttribute("userName", user.getUser_name());
		model.addAttribute("products", products);
		
		return "cartdetail";
	}
	
	
}
