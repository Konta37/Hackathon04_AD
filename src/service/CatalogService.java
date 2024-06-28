package service;

import model.Catalog;
import model.Product;
import run.BookManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CatalogService implements IGenericService<Catalog, Integer> {
    public static List<Catalog> catalogList = new ArrayList<Catalog>();

    @Override
    public List<Catalog> getAll() {
        return catalogList;
    }

    @Override
    public void save(Catalog catalog) {
        Catalog catalogCheck = findById(catalog.getCatalogId());
        if (catalogCheck == null) {
            catalogList.add(catalog);
            System.out.println("Catalog added");
        } else {
            catalogList.set(catalogList.size()-1, catalog);
            System.out.println("Catalog updated");
        }
    }

    @Override
    public Catalog findById(Integer integer) {
        for (Catalog catalog : catalogList) {
            if (catalog.getCatalogId() == integer) {
                return catalog;
            }
        }
        return null;
    }

    @Override
    public void delete(Integer integer) {
        for (Catalog catalog : catalogList) {
            if (catalog.getCatalogId() == integer) {
                boolean isExist = false;
                for (Product product : ProductService.productList) {
                    if (product.getCatalog().getCatalogId() == catalog.getCatalogId()) {
                        isExist = true;
                        break;
                    }
                }
                if (!isExist) {
                    catalogList.remove(catalog);
                } else {
                    System.out.println("Catalog has products. Can't delete");
                }

            }
        }
        System.out.println("Finish deleting Catalog");
    }

}
