package onlineShop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import onlineShop.model.Cart;
import onlineShop.model.CartItem;
import onlineShop.model.Customer;
import onlineShop.model.Product;
import onlineShop.service.CartItemService;
import onlineShop.service.CartService;
import onlineShop.service.CustomerService;
import onlineShop.service.ProductService;

@Controller
public class CartItemController {

	@Autowired
	private CartService cartService;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/cart/add/{productId}", method = RequestMethod.GET)
	public String addCartItem(@PathVariable(value = "productId") int productId) {
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();
		Customer customer = customerService.getCustomerByUserName(username);
		Cart cart = customer.getCart();
		List<CartItem> cartItems = cart.getCartItem();
		Product product = productService.getProductById(productId);

		// check if product is already added in cart
			for (int i = 0; i < cartItems.size(); i++) {
				CartItem cartItem = cartItems.get(i);
				if (product.getId() == cartItem.getProduct().getId()) {
					int prevQuantity = cartItem.getQuantity();
					double prodPrice = cartItem.getProduct().getProductPrice();
					cartItem.setQuantity(prevQuantity + 1);
					cartItem.setPrice(prodPrice);
					cartItem.setTotalPrice(prevQuantity + 1, prodPrice);
					cartItemService.addCartItem(cartItem);
					return "redirect:/getAllProducts";
				}
			}
		
			// no such product was added to cart before
			CartItem cartItem = new CartItem();
			cartItem.setProduct(product);
			cartItem.setQuantity(1);
			cartItem.setPrice(product.getProductPrice());
			cartItem.setCart(cart);
			cartItem.setTotalPrice(1, product.getProductPrice());
			cartItemService.addCartItem(cartItem);
		
		return "redirect:/getAllProducts";
	}

	@RequestMapping(value = "/cart/removeCartItem/{cartItemId}", method = RequestMethod.GET)
	public String removeCartItem(@PathVariable(value = "cartItemId") int cartItemId) {
		cartItemService.removeCartItem(cartItemId);
		return "redirect:/cart/getCartById";
	}

	@RequestMapping(value = "/cart/removeAllItems/{cartId}", method = RequestMethod.GET)
	public String removeAllCartItems(@PathVariable(value = "cartId") int cartId) {
		Cart cart = cartService.getCartById(cartId);
		cartItemService.removeAllCartItems(cart);
		return "redirect:/cart/getCartById";
	}

}
