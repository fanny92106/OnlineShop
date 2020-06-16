package onlineShop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import onlineShop.model.Cart;
import onlineShop.model.CartItem;
import onlineShop.model.Customer;
import onlineShop.service.CartService;
import onlineShop.service.CustomerService;

@Controller
public class CartController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CartService cartService;
	
	@RequestMapping(value="/cart/getCartById", method=RequestMethod.GET)
	public ModelAndView getCartId() {
		
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();
		Customer customer = customerService.getCustomerByUserName(username);
		Cart cart = customer.getCart();
		List<CartItem> items = cart.getCartItem();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("cart");
		modelAndView.addObject("cart", cart);
		modelAndView.addObject("items", items);
		
		return modelAndView;
	}
	
	@RequestMapping(value="/cart/getCart/{cartId}", method=RequestMethod.GET)
	@ResponseBody
	public Cart getCartItems(@PathVariable(value="cartId")int cartId) {
		Cart cart = cartService.getCartById(cartId);
		return cart;
	}
}
