package b1.run;

import b1.model.Cart;
import b1.model.Catalog;
import b1.model.Product;
import b1.service.CartService;
import b1.service.CatalogService;
import b1.service.ProductService;

import java.util.Collections;
import java.util.Scanner;

public class BookManagement {
    private static final ProductService productService = new ProductService();
    private static final CatalogService catalogService = new CatalogService();
    private static final CartService cartService = new CartService();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("➢ ===== BASIC-MENU =====");
            System.out.println("1. Catalog Management");
            System.out.println("2. Product Management");
            System.out.println("3. User Management (user only)");
            System.out.println("4. Exit");
            System.out.println("Enter your choice: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    catalogMenu(sc);
                    break;
                case 2:
                    productMenu(sc);
                    break;
                case 3:
                    userMenu(sc);
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.err.println("Please choose between 1 -> 3");
            }
        } while (true);
    }

    public static void catalogMenu(Scanner sc) {
        do {
            System.out.println("➢ ===== CATALOG-MENU =====");
            System.out.println("1. Enter number of catalogs and information");
            System.out.println("2. Show all catalogs");
            System.out.println("3. Update name catalog by id");
            System.out.println("4. Delete catalog by id");
            System.out.println("5. Exit");
            System.out.println("Enter your choice: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    addCatalogs(sc);
                    break;
                case 2:
                    showAllCatalogs();
                    break;
                case 3:
                    updateCatalogById(sc);
                    break;
                case 4:
                    deleteCatalogById(sc);
                    break;
                case 5:
                    return;
                default:
                    System.err.println("Please choose between 1 -> 3");
            }
        } while (true);
    }

    public static void addCatalogs(Scanner sc) {
        System.out.println("Enter number of Catalogs: ");
        int n = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < n; i++) {
            Catalog catalogAdd = new Catalog();
            catalogAdd.inputData();
            catalogService.save(catalogAdd);
        }
    }

    public static void showAllCatalogs() {
        for (Catalog a : catalogService.getAll()) {
            System.out.println(a.toString());
        }
        System.out.println("Finish showing all catalogs");
    }

    public static void updateCatalogById(Scanner sc) {
        System.out.println("Enter Catalog ID: ");
        do {
            String input = sc.nextLine();
            try {
                Catalog catalogUpdate = catalogService.findById(Integer.parseInt(input));
                if (catalogUpdate != null) {
                    catalogUpdate.setCatalogName(catalogUpdate.inputCatalogName(sc));
                    System.out.println("Update name successfully");
                    return;
                } else {
                    System.err.println("Catalog not found");
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid Catalog ID. Must be a number. Try again!");
            }
        } while (true);
    }

    public static void deleteCatalogById(Scanner sc) {
        System.out.println("Enter Catalog ID: ");
        do {
            String input = sc.nextLine();
            try {
                catalogService.delete(Integer.parseInt(input));
                return;
            } catch (NumberFormatException e) {
                System.err.println("Invalid Catalog ID. Must be a number. Try again!");
            }
        } while (true);
    }

    public static void productMenu(Scanner sc) {
        do {
            System.out.println("➢ ===== PRODUCT-MENU =====");
            System.out.println("1. Enter number of products and information");
            System.out.println("2. Show all products");
            System.out.println("3. Sort products by price");
            System.out.println("4. Delete product by id");
            System.out.println("5. Search product by name");
            System.out.println("6. Update product by id");
            System.out.println("7. Exit");
            System.out.println("Enter your choice: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    addProducts(sc);
                    break;
                case 2:
                    showAllProducts();
                    break;
                case 3:
                    sortProductByPrice(sc);
                    break;
                case 4:
                    deleteProductById(sc);
                    break;
                case 5:
                    searchProductByName(sc);
                    return;
                case 6:
                    updateProductById(sc);
                    break;
                case 7:
                    return;
                default:
                    System.err.println("Please choose between 1 -> 7");
            }
        } while (true);
    }

    public static void addProducts(Scanner sc) {
        System.out.println("Enter number of Products: ");
        int n = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < n; i++) {
            Product productAdd = new Product();
            productAdd.inputData();
            productService.save(productAdd);
        }
    }

    public static void showAllProducts() {
        for (Product a : productService.getAll()) {
            System.out.println(a.toString());
        }
        System.out.println("Finish showing all products");
    }

    public static void sortProductByPrice(Scanner sc) {
        Collections.sort(ProductService.productList);
        for (Product a : ProductService.productList) {
            System.out.println(a.toString());
        }
        System.out.println("Finish sorting products by price");
    }

    public static void deleteProductById(Scanner sc) {
        System.out.println("Enter Product ID: ");
        String input = sc.nextLine();
        productService.delete(input);
    }

    public static void searchProductByName(Scanner sc) {
        System.out.println("Enter Product Name: ");
        String input = sc.nextLine();
        int count = 0;
        for (Product a : productService.getAll()) {
            if (a.getProductName().toLowerCase().contains(input.toLowerCase())) {
                System.out.println(a.toString());
                count++;
            }
        }
        System.out.println("There are " + count + " products with the name " + input);
    }

    public static void updateProductById(Scanner sc) {
        System.out.println("Enter Product ID: ");
        do {
            String input = sc.nextLine();
            Product productUpdate = productService.findById(input);
            if (productUpdate != null) {
                do {
                    System.out.println("=========UPDATE PRODUCT =========");
                    System.out.println("1. Update product name");
                    System.out.println("2. Update product price");
                    System.out.println("3. Update product description");
                    System.out.println("4. Update product stock");
                    System.out.println("5. Update product catalog");
                    System.out.println("6. Update product status");
                    System.out.println("7. Exit");
                    System.out.println("Enter your choice: ");
                    int choice = Integer.parseInt(sc.nextLine());
                    switch (choice) {
                        case 1:
                            productUpdate.setProductName(productUpdate.inputProductName(sc));
                            break;
                        case 2:
                            productUpdate.setProductPrice(productUpdate.inputProductPrice(sc));
                            break;
                        case 3:
                            productUpdate.setDescription(productUpdate.inputDescription(sc));
                            break;
                        case 4:
                            productUpdate.setStock(productUpdate.inputStock(sc));
                            break;
                        case 5:
                            productUpdate.setCatalog(productUpdate.inputCatalog(sc));
                            break;
                        case 6:
                            productUpdate.setStatus(!productUpdate.isStatus());
                            break;
                        case 7:
                            return;
                        default:
                            System.err.println("Invalid choice");
                    }
                } while (true);
            } else {
                System.err.println("Product not found");
            }
        } while (true);
    }

    public static void userMenu(Scanner sc) {
        do {
            System.out.println("➢ ===== MENU-USER =====");
            System.out.println("1. Show all products");
            System.out.println("2. Add in cart");
            System.out.println("3. Show all products in cart");
            System.out.println("4. Change quantity of product in carts");
            System.out.println("5. Delete products in carts");
            System.out.println("6. Delete all");
            System.out.println("7. Exit");
            System.out.println("Enter your choice: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    showAllProducts();
                    break;
                case 2:
                    addIntoCarts(sc);
                    break;
                case 3:
                    showCarts();
                    break;
                case 4:
                    updateQuantityProductInCart(sc);
                    break;
                case 5:
                    deleteOneCartById(sc);
                    break;
                case 6:
                    deleteAllProductInCart(sc);
                    break;
                case 7:
                    return;
                default:
                    System.err.println("Please choose between 1 -> 7");
            }
        } while (true);
    }

    public static void addIntoCarts(Scanner sc) {
        System.out.println("Enter number of Products you want to add to Cart: ");
        int n = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < n; i++) {
            Cart cartAdd = new Cart();
            cartAdd.inputData(sc);
            cartService.save(cartAdd);
        }
    }

    public static void showCarts() {
        System.out.printf("%-10s %-20s %-10s %-10s%n\n", "Id cart", "Product", "Quantity","Price");
        for (int i = 0; i < cartService.getAll().size(); i++) {
            if (CartService.cartList.get(i).getProduct()!=null){
                CartService.cartList.get(i).displayData();
            }else {
                CartService.cartList.remove(i);
                break;
            }
        }
        System.out.println("Finish showing all products in cart");
    }

    public static void updateQuantityProductInCart(Scanner sc) {
        System.out.println("Enter Cart ID to update: ");
        String input = sc.nextLine();
        try {
            int index = -1;
            for (int i = 0; i < cartService.getAll().size(); i++) {

                if (CartService.cartList.get(i).getCartItemId() == Integer.parseInt(input)) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                System.out.println("Can not find cart with ID " + input);
            } else {
                //set quantity
                System.out.println("Enter number of Products you want to add in cart");
                do {
                    String quantity = sc.nextLine();
                    try {
                        System.out.println("Enter number of Products you want to add in cart");
                        int quantityFormat = Integer.parseInt(quantity);
                        int totalQuantity = checkQuantityProductInCart(sc, CartService.cartList.get(index), quantityFormat);
                        if (totalQuantity > 0) {
                            CartService.cartList.get(index).setQuantity(totalQuantity);

                            //after increase quantity so change quantity of product in stock
                            changeQuantityProductInStock(CartService.cartList.get(index), quantityFormat);
                            return;
                        }else {
                            System.err.println("Out of stock. Try again");
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid quantity type. Try again");
                    }
                }while (true);
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid Cart ID type. Try again");
        }
    }

    public static void deleteOneCartById(Scanner sc) {
        System.out.println("Enter Cart ID to delete: ");
        do {
            try {
                String input = sc.nextLine();
                cartService.delete(Integer.parseInt(input));
                break;

            }catch (NumberFormatException e){
                System.err.println("Invalid Cart ID type. Try again");
            }
        }while (true);
    }

    public static void deleteAllProductInCart(Scanner sc) {
        cartService.deleteAllProductInCart(sc);
    }

    public static int checkQuantityProductInCart(Scanner sc, Cart cart, int quantity) {
        int productIndex = findProductById(cart.getProduct().getProductId());
        if (productIndex != -1) {
            //check if quantity of products in stocks >= new quantity + current quantity of product in cart
            if (ProductService.productList.get(productIndex).getStock() > cart.getQuantity() + quantity) {
                return cart.getQuantity() + quantity;
            }
        }
        return -1;
    }

    public static void changeQuantityProductInStock(Cart cart, int afterQuantity){
        int productIndex = findProductById(cart.getProduct().getProductId());
        if (productIndex != -1) {
            ProductService.productList.get(productIndex).setStock(ProductService.productList.get(productIndex).getStock()-afterQuantity);
        }else {
            System.err.println("Can not find cart with ID " + cart.getProduct().getProductId());
        }
    }

    public static int findProductById(String id) {
        for (int i = 0; i < productService.getAll().size(); i++) {
            if (productService.getAll().get(i).getProductId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}
