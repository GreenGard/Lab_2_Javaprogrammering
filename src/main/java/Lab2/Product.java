package Lab2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Product implements Cloneable {
    private String name;
    private int price;
    private String brand;
    private int barcode;
    private String category;
    private int amount;
    public static List<String> validCategories = new ArrayList<>();  // Lista med tillåtna kategorier.

    public Product(String name, int price, String brand, int barcode, String category, int amount) {
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.barcode = barcode;
        this.category = category;
        this.amount = amount;
    }

    public Product() {

    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return price == product.price && barcode == product.barcode && amount == product.amount && Objects.equals(name, product.name) && Objects.equals(brand, product.brand) && Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, brand, barcode, category, amount);
    }

    private String ToString() {
        return "Name: " + this.name + " Price: " + this.price + " Brand: " + this.brand + " Barcode: " + this.barcode + " Category: " + this.category + " Amount: " + this.amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getBarcode() {
        return barcode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) { // Endast för tilläggning av ny produkt för att se till att kategorin är en som existerar i List<string> validCategories
        if (!validCategories.contains(category)) {
            this.category = category;
        } else {
            Scanner sc = new Scanner(System.in);
            System.out.println("This category does currently not exist\nPlease reenter a category: ");
            setCategory(sc.nextLine());
        }
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
