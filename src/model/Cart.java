package model;

import service.CartService;
import service.CatalogService;
import service.ICart;
import service.ProductService;

import java.util.Scanner;

public class Cart implements ICart {
    private boolean isAdd =false;
    private int indexId=-1;
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
        this.quantity = inputQuantity(sc);
        this.price = inputPrice(sc);
    }

    @Override
    public void displayData() {
        System.out.printf("%-10s %-20s %-10s %-10s%n\n",this.cartItemId,this.product.getProductName(),this.quantity,this.price);
    }

    public int inputCartItemId(Scanner sc) {
        System.out.println("Enter cart item id: ");
        do {
            String input = sc.nextLine();
            try {
                if (Integer.parseInt(input) >= 0 ){
                    return Integer.parseInt(input);
                }else {
                    System.err.println("Invalid cart item id! Must > 0! Try again.");
                }
            }catch (NumberFormatException e) {
                System.err.println("Invalid cart item id type! Try again.");
            }
        }while (true);
    }

    public Product inputProduct(Scanner sc) {
        System.out.println("Enter product id to add to cart: ");
        do {
            String input = sc.nextLine();
            int index = -1;
            for (int i = 0; i < CartService.cartList.size(); i++) {
                if (CartService.cartList.get(i).product.getProductId().equals(input)){
                    indexId = i;
                    isAdd = true;
                    break;
                }
            }
            if (indexId != -1){
                System.out.println("You cant add another product to your cart. It's already exist. So change quantity");
                int newQuantity = inputNewQuantity(sc,indexId);
                CartService.cartList.get(indexId).setQuantity(newQuantity);
                this.quantity = newQuantity;
                System.out.println("Finish adding more quantity of product to your cart.");
            }else {
                isAdd=false;
            }
            for (int i = 0; i < ProductService.productList.size(); i++){
                if (ProductService.productList.get(i).getProductId().equals(input)){
                    index = i;
                    break;
                }
            }
            if (index == -1){
                System.err.println("Cannot find product with id: " + input);
            }else {
                return ProductService.productList.get(index);
            }
        }while (true);
    }

    //doesnt need to enter from user. Auto calculate
    public double inputPrice(Scanner sc) {
        return this.quantity * this.product.getProductPrice();
    }

    public int inputQuantity(Scanner sc) {
        System.out.println("Enter quantity product to add to cart: ");
        do {
            String input = sc.nextLine();
            try {
                if (Integer.parseInt(input) > 0  && Integer.parseInt(input) <= this.product.getStock()){
                    int indexProduct = findIndexProductByID(this.product.getProductId());
                    ProductService.productList.get(indexProduct).setStock( ProductService.productList.get(indexProduct).getStock()-Integer.parseInt(input));
                    return Integer.parseInt(input);
                }else {
                    System.err.println("Invalid quantity product id! Must > 0 and <= " + this.product.getStock() + "! Try again.");
                }
            }catch (NumberFormatException e){
                System.err.println("Invalid quantity product type! Try again.");
            }
        }while (true);
    }

    public int inputNewQuantity(Scanner sc,int index) {
        System.out.println("Enter quantity product to add to cart: ");
        do {
            String input = sc.nextLine();
            try {
                if (Integer.parseInt(input) > 0  && Integer.parseInt(input) <= CartService.cartList.get(index).getQuantity() + CartService.cartList.get(index).getProduct().getStock()){
                    int indexProduct = findIndexProductByID(CartService.cartList.get(index).getProduct().getProductId());
                    ProductService.productList.get(indexProduct).setStock(ProductService.productList.get(indexProduct).getStock() - Integer.parseInt(input));
                    return Integer.parseInt(input) +CartService.cartList.get(index).getQuantity();
                }else {
                    System.err.println("Invalid quantity product id! Must > 0 and <= " + (CartService.cartList.get(index).getQuantity() +CartService.cartList.get(index).getProduct().getStock()) + "! Try again.");
                }
            }catch (NumberFormatException e){
                System.err.println("Invalid quantity product type! Try again.");
            }
        }while (true);
    }

    public int findIndexProductByID(String productId) {
        for (int i = 0; i < ProductService.productList.size(); i++) {
            if (ProductService.productList.get(i).getProductId().equals(productId)){
                return i;
            }
        }return -1;
    }

}
