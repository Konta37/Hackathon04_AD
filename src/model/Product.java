package model;

import run.BookManagement;
import service.CatalogService;
import service.ProductService;

import java.util.Scanner;

public class Product implements Comparable<Product>{
    private String productId; //Pxxxx
    private String productName;
    private double productPrice;
    private String description;
    private int stock; //at least 10
    private Catalog catalog; //cant null
    private boolean status;

    public Product() {
    }

    public Product(String productId, String productName, double productPrice, String description, int stock, Catalog catalog, boolean status) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.description = description;
        this.stock = stock;
        this.catalog = catalog;
        this.status = status;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void inputData(){
        Scanner sc = new Scanner(System.in);
        this.productId = inputProductId(sc);
        this.productName = inputProductName(sc);
        this.productPrice = inputProductPrice(sc);
        this.description = inputDescription(sc);
        this.stock = inputStock(sc);
        this.catalog = inputCatalog(sc);
        this.status = inputStatus(sc);
    }

    public String toString() {
//        return System.out.printf("%-10s %-20s %-15s %-20s %-10s %-15s %-10s%n\n",
//                this.productId,this.productName,this.productPrice,this.description,this.stock,this.catalog.getCatalogName(),this.status);
        return "id: " + this.productId + " name: " +productName +" price: " + productPrice + " description: " + description + " stock: " + stock + " catalog: " + catalog.getCatalogName() + " status: " + status +"\n";
    }

    public String inputProductId(Scanner sc) {
        System.out.println("Enter product ID: ");
        do {
            String regex = "P\\d{4}";
            String input = sc.nextLine();
            if (input.matches(regex)) {
                boolean isExist = false;
                for (int i = 0; i < ProductService.productList.size(); i++) {
                    if (ProductService.productList.get(i).getProductId().equals(input)) {
                        isExist = true;
                        break;
                    }
                }
                if (!isExist) {
                    return input;
                } else {
                    System.err.println("The product ID " + input + " already exists! Try again!");
                }
            } else {
                System.out.println("Invalid product ID (Pxxxx). Try again.");
            }
        } while (true);
    }

    public String inputProductName(Scanner sc) {
        System.out.println("Enter product name: ");
        do {
            String input = sc.nextLine();
            if (!input.isEmpty()) {
                return input;
            } else {
                System.err.println("Invalid product name! Must not be empty. Try again!");
            }
        } while (true);
    }

    public double inputProductPrice(Scanner sc) {
        System.out.println("Enter product price: ");
        do {
            String input = sc.nextLine();
            try {
                if (Double.parseDouble(input) > 0) {
                    return Double.parseDouble(input);
                } else {
                    System.err.println("Invalid product price! Must > 0. Try again!");
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid product price type! Try again!");
            }
        } while (true);
    }

    public String inputDescription(Scanner sc) {
        System.out.println("Enter product description: ");
        do {
            String input = sc.nextLine();
            if (!input.isEmpty()) {
                return input;
            } else {
                System.err.println("Invalid product description! Must not be empty. Try again!");
            }
        } while (true);
    }

    public int inputStock(Scanner sc) {
        System.out.println("Enter product stock: ");
        do {
            String input = sc.nextLine();
            try {
                if (Integer.parseInt(input) >= 10) {
                    return Integer.parseInt(input);
                } else {
                    System.err.println("Invalid product stock! Must >= 10. Try again!");
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid product stock type! Try again!");
            }
        } while (true);
    }

    public Catalog inputCatalog(Scanner sc) {
        System.out.println("Enter product catalog id: ");
        do {
            String cataId = sc.nextLine();
            try {
                if (Integer.parseInt(cataId) >= 0) {
                    int cateIndex = -1;
                    for (int i = 0; i < CatalogService.catalogList.size(); i++) {
                        if (CatalogService.catalogList.get(i).getCatalogId() == Integer.parseInt(cataId)) {
                            cateIndex = i;
                            break;
                        }
                    }
                    if (cateIndex >= 0) {
                        return CatalogService.catalogList.get(cateIndex);
                    } else {
                        System.err.println("There is no such catalog id. Try again!");
                    }
                } else {
                    System.err.println("Invalid catalog Id value. Must enter number > 0. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid catalog Id value. Try again.");
            }
        } while (true);
    }
    public boolean inputStatus(Scanner sc) {
        return true;
    }

    @Override
    public int compareTo(Product o) {
        return Double.compare(o.productPrice,this.productPrice);
    }
}
