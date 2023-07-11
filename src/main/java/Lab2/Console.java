package Lab2;

import java.util.Scanner;

public class Console {

    public static void menu() {
        String input;

        do {
            System.out.println("[1] Show inventory\n[2] Add product to cart\n[3] Add product to inventory\n[4] Search for product\n[5] Edit product in inventory\n[6] Add inventory category\n[7] Save store inventory\n[E] Exit application");
            Scanner sc = new Scanner(System.in);
            input = sc.nextLine().toUpperCase();
            switch (input) {
                case "1" -> Shop.showInventory();
                case "2" -> Shop.addProductToCart();
                case "3" -> Shop.addProductToInventory();
                case "4" -> searchMenu();
                case "5" -> Shop.editStoredProduct();
                case "6" -> Shop.addCategory();
                case "7" -> Shop.saveStoreInventory();
                case "E" -> {
                    if (Shop.cart.size() > 0) {
                        Receipt.outputReceipt();
                    }
                    Shop.saveStoreInventory();
                }
                default -> System.out.println("Incorrect input please try again");
            }
        } while (!input.equals("E"));
    }

    public static void searchMenu() {
        System.out.println("[1] Search products through price range\n[2] Search products through brand\n[3] Search products through category");
        Scanner sc = new Scanner(System.in);
        switch (sc.nextLine()) {
            case "1" -> {
                String minPrice, maxPrice;
                do {
                    System.out.println("Minimum price?");
                    minPrice = sc.nextLine();
                } while (!Shop.parseIntTrueOrFalse(minPrice));
                do {
                    System.out.println("Maximum price?");
                    maxPrice = sc.nextLine();
                } while (!Shop.parseIntTrueOrFalse(maxPrice));
                Shop.searchProductByPrice(Integer.parseInt(minPrice), Integer.parseInt(maxPrice));
            }
            case "2" -> {
                System.out.println("Brand you want to search for");
                Shop.searchByBrand(sc.nextLine());
            }
            case "3" -> {
                System.out.println("Category you want to search for");
                Shop.searchProductByCategory(sc.nextLine());
            }
            default -> System.out.println("Incorrect input please try again");
        }
    }
}
