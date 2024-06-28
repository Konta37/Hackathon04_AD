package b1.model;

import b1.service.CartService;
import b1.service.ICart;
import b1.service.ProductService;

import java.util.Scanner;

public class Cart implements ICart {
    private int indexId = -1;
    private int cartItemId;
    private Product product;
    private double price;
    private int quantity;

    public Cart() {
    }

    public Cart(int cartItemId, Product product, double price, int quantity) {
        this.cartItemId = cartItemId;
        this.product = product;
        this.price = price;
        this.quantity = quantity;
    }

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    @Override
    public void inputData(Scanner sc) {
        this.cartItemId = inputCartItemId(sc);
        this.product = inputProduct(sc);
        if (this.product != null) {
            this.quantity = inputQuantity(sc);
            this.price = inputPrice(sc);
        }
    }

    @Override
    public void displayData() {
        if (this.product != null) {
            System.out.printf("%-10d %-20s %-10d %-10.2f%n",
                    this.cartItemId, this.product.getProductName(), this.quantity, this.price);
        }

    }

    public int inputCartItemId(Scanner sc) {
        int maxId = -1;
        if (CartService.cartList.isEmpty()){
            return 0;
        }
        for (int i = 0; i < CartService.cartList.size(); i++) {
            if (maxId <= CartService.cartList.get(i).getCartItemId()) {
                maxId = CartService.cartList.get(i).getCartItemId();
            }
        }
        return maxId +1;
    }

    public Product inputProduct(Scanner sc) {
        System.out.println("Enter product id to add to cart: ");
        do {
            String input = sc.nextLine();

            Product existingProduct = findProductInCart(input);

            if (existingProduct != null) {
                System.out.println("You can't add another product to your cart. It's already exist. So change quantity.");
                int newQuantity = inputNewQuantity(sc, indexId);
                CartService.cartList.get(indexId).setQuantity(newQuantity);
                this.quantity = newQuantity;
                System.out.println("Finished adding more quantity of product to your cart.");
                //return null to delete later
                return null;
            }

            Product product = findProductInStore(input);
            if (product == null) {
                System.err.println("Cannot find product with id: " + input);
            } else {
                return product;
            }
        } while (true);
    }


    //doesnt need to enter from user. Auto calculate
    public double inputPrice(Scanner sc) {
        return this.quantity * this.product.getProductPrice();
    }

    public int inputQuantity(Scanner sc) {
        System.out.println("Enter quantity of product to add to cart: ");
        while (true) {
            String input = sc.nextLine();
            try {
                int quantity = Integer.parseInt(input);
                if (quantity > 0 && quantity <= this.product.getStock()) {
                    updateProductStock(this.product.getProductId(), quantity);
                    return quantity;
                } else {
                    System.err.println("Invalid quantity! Must be > 0 and <= " + this.product.getStock() + ". Try again.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid quantity type! Try again.");
            }
        }
    }
    public int inputNewQuantity(Scanner sc, int index) {
        System.out.println("Enter quantity of product to add to cart: ");
        while (true) {
            String input = sc.nextLine();
            try {
                int quantity = Integer.parseInt(input);
                int maxQuantity = CartService.cartList.get(index).getQuantity() + CartService.cartList.get(index).getProduct().getStock();
                if (quantity > 0 && quantity <= maxQuantity) {
                    updateProductStock(CartService.cartList.get(index).getProduct().getProductId(), quantity);
                    return quantity + CartService.cartList.get(index).getQuantity();
                } else {
                    System.err.println("Invalid quantity! Must be > 0 and <= " + maxQuantity + ". Try again.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid quantity type! Try again.");
            }
        }
    }

    private void updateProductStock(String productId, int quantity) {
        int indexProduct = findIndexProductByID(productId);
        ProductService.productList.get(indexProduct).setStock(ProductService.productList.get(indexProduct).getStock() - quantity);
    }

    private Product findProductInCart(String productId) {
        for (int i = 0; i < CartService.cartList.size(); i++) {
            if (CartService.cartList.get(i).product.getProductId().equals(productId)) {
                indexId = i;

                return CartService.cartList.get(i).product;
            }
        }

        return null;
    }

    private Product findProductInStore(String productId) {
        for (int i = 0; i < ProductService.productList.size(); i++) {
            if (ProductService.productList.get(i).getProductId().equals(productId)) {
                return ProductService.productList.get(i);
            }
        }
        return null;
    }

    public int findIndexProductByID(String productId) {
        for (int i = 0; i < ProductService.productList.size(); i++) {
            if (ProductService.productList.get(i).getProductId().equals(productId)) {
                return i;
            }
        }
        return -1;
    }

}
