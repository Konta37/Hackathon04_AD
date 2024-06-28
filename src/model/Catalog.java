package model;

import java.util.Scanner;

public class Catalog {
    private int catalogId;
    private String catalogName;
    private String description;

    public Catalog() {
    }

    public Catalog(int catalogId, String catalogName, String description) {
        this.catalogId = catalogId;
        this.catalogName = catalogName;
        this.description = description;
    }

    public int getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(int catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void inputData(){
        Scanner sc = new Scanner(System.in);
        this.catalogId = inputCatalogId(sc);
        this.catalogName = inputCatalogName(sc);
        this.description = inputCatalogDescription(sc);
    }

    public String toString(){
        return "Catalog ID: " + catalogId + " Catalog Name: " + catalogName + " Description: " + description +"\n";
    }

    public int inputCatalogId(Scanner sc) {
        System.out.println("Enter Catalog ID: ");
        do {
            String inputId = sc.nextLine();
            try{
                if(Integer.parseInt(inputId) >=0){
                    return Integer.parseInt(inputId);
                }else {
                    System.err.println("Invalid Catalog ID. Try again.");
                }
            }catch (NumberFormatException e){
                System.err.println("Invalid Catalog ID type. Must be number int. Try again.");
            }
        }while (true);
    }

    public String inputCatalogName(Scanner sc) {
        System.out.println("Enter Catalog Name: ");
        do {
            String inputName = sc.nextLine();
            if(!inputName.isEmpty()){
                return inputName;
            }else {
                System.err.println("Invalid Catalog Name. Can not empty. Try again.");
            }
        }while (true);
    }

    public String inputCatalogDescription(Scanner sc) {
        System.out.println("Enter Catalog Description: ");
        do {
            String inputDes = sc.nextLine();
            if(!inputDes.isEmpty()){
                return inputDes;
            }else {
                System.err.println("Invalid Catalog Description. Can not empty. Try again.");
            }
        }while (true);
    }
}
