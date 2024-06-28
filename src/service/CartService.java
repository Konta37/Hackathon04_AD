package service;

import model.Cart;
import model.Catalog;
import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CartService implements IGenericService<Cart, Integer> {
    public static List<Cart> cartList = new ArrayList<Cart>();


    @Override
    public List<Cart> getAll() {
        return cartList;
    }

    @Override
    public void save(Cart cart) {
        Cart cartCheck = findById(cart.getCartItemId());
        if (cartCheck == null) {
            cartList.add(cart);
            System.out.println("Product added to cart.");
        } else {
            cartList.set(cartList.size() - 1, cart);
            System.out.println("Cart updated");
        }
    }

    @Override
    public Cart findById(Integer integer) {
        for (Cart cart : cartList) {
            if (cart.getCartItemId() == integer) {
                return cart;
            }
        }
        return null;
    }


    //delete one product in cart
    @Override
    public void delete(Integer integer) {
        for (int i = 0; i < cartList.size(); i++) {
            if (cartList.get(i).getCartItemId() == integer) {
                cartList.remove(i);
                break;
            }
        }
        System.out.println("Finish deleting product from cart.");
    }

    public void deleteAllProductInCart(Scanner sc) {
        System.out.println("Do you want to delete all the products from cart? (yes/no)");
        do {
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("yes")) {
                cartList.clear();
                return;
            } else if(input.equalsIgnoreCase("no")){
                System.out.println("Exit delete All Product In Cart");
                return;
            }else {
                System.err.println("Invalid input (true/false)");
            }
        }while (true);

    }
}
