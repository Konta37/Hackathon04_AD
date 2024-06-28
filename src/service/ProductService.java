package service;

import model.Catalog;
import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductService implements IGenericService<Product,String>{
    public static List<Product> productList = new ArrayList<Product>();


    @Override
    public List<Product> getAll() {
        return productList;
    }

    @Override
    public void save(Product product) {
        Product productCheck = findById(product.getProductId());
        if (productCheck == null) {
            productList.add(product);
            System.out.println("Product added");
        }else {
            productList.set(productList.size(), product);
            System.out.println("Product updated");
        }
    }

    @Override
    public Product findById(String integer) {
        for (Product product : productList) {
            if (product.getProductId().equals(integer)) {
                return product;
            }
        }
        return null;
    }

    @Override
    public void delete(String integer) {
        boolean isExist = false;
        for (Product product : productList) {
            if (product.getProductId().equals(integer)) {
                productList.remove(product);
                isExist = true;
                System.out.println("Product removed");
                break;
            }
        }
        if(isExist){
            System.out.println("Finish deleting Product");
        }else {
            System.out.println("Product with id not found to delete");
        }
    }

}
