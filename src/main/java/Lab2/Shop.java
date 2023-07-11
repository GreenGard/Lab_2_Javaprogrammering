package Lab2;

import java.util.*;
import java.util.stream.Collectors;

public class Shop {

    public static List<Product> storeInventory = new ArrayList<>(); // Lista med butikens lager (skall bytas mot en fil istället för lista)
    public static List<Product> cart = new ArrayList<>(); // Lista av kundens varor i kundvagnen.

    public static void showInventory() {
        storeInventory.stream()
                .forEach(Shop::printProduct);
    }

    public static void addProductToCart() {
        System.out.println("Name of the product you want to add to your cart");
        Scanner scanner = new Scanner(System.in);
        var input = scanner.nextLine().replaceAll(" ", "_");

        var nameProduct = storeInventory.stream()
                .filter(product -> product.getName().equalsIgnoreCase(input) && product.getAmount() > 0)
                .findFirst();

        nameProduct.ifPresentOrElse(
                (cartProduct) -> { // Tvungen att göra en ny stream eftersom vi inte kan nå getAmount från streamen ovan.
                    var foundProduct = cart.stream()
                            .filter(product -> product.getName().equalsIgnoreCase(cartProduct.getName()))
                            .findFirst();

                    foundProduct.ifPresentOrElse(
                            (product) -> {
                                product.setAmount(product.getAmount() + 1);
                                cartProduct.setAmount(cartProduct.getAmount() - 1);
                                System.out.println(cartProduct.getName() + " added to cart");
                            },
                            () -> {
                                Product productClone = new Product(); // skapar en klon av objeketet för att lägga in i kundvagnen.
                                try {
                                    productClone = (Product) cartProduct.clone();
                                } catch (CloneNotSupportedException e) {
                                    e.printStackTrace();
                                }
                                cartProduct.setAmount(cartProduct.getAmount() - 1);
                                productClone.setAmount(1);
                                cart.add(productClone);
                                System.out.println(cartProduct.getName() + " added to cart");
                            });
                },
                () -> System.out.println(input + " could not be found in store inventory, or is out of stock"));
    }


    public static void addProductToInventory() {  //Metoden ska lägga till en produkt i filen "StoreInventory" genom att skapa ett nytt "Product" objekt
        System.out.println("Name of the product you want to add to the store inventory");
        Scanner scanner = new Scanner(System.in);
        var input = scanner.nextLine();

        var nameMatchProduct = storeInventory.stream()
                .filter(product -> product.getName().equalsIgnoreCase(input))
                .findFirst();

        nameMatchProduct.ifPresentOrElse(
                (product) -> {
                    product.setAmount(product.getAmount() + 1);
                    System.out.println("One more of that product was added to store inventory");
                },
                () -> productInput(input));
    }

    public static void productInput(String input) {
        Scanner scanner = new Scanner(System.in);
        var name = input.replaceAll(" ", "_");

        String price;
        do {
            System.out.println("Price of the product you want to add to the store inventory");
            price = scanner.nextLine().replaceAll(" ", "");
        } while (!parseIntTrueOrFalse(price));

        System.out.println("Brand of the product you want to add to the store inventory");
        var brand = scanner.nextLine().replaceAll(" ", "_");

        int barcode = storeInventory.size() + 1;

        String category;
        do {
            System.out.println("Category of the product you want to add");
            Product.validCategories.forEach(System.out::println);
            category = scanner.nextLine().replaceAll(" ", "_");
        } while (!Product.validCategories.contains(category));

        String amount;
        do {
            System.out.println("Amount of the product you want to add to the store inventory");
            amount = scanner.nextLine().replaceAll(" ", "");
        } while (!parseIntTrueOrFalse(amount));

        storeInventory.add(new Product(name, Integer.parseInt(price), brand, barcode, category, Integer.parseInt(amount)));
        System.out.println("Product added!");
    }

    public static void searchProductByPrice(int minPrice, int maxPrice) {
        var priceProducts = storeInventory.stream()
                .filter(product -> product.getPrice() >= minPrice && product.getPrice() <= maxPrice)
                .sorted(Comparator.comparing(Product::getPrice))
                .collect(Collectors.toList());

        priceProducts.forEach(Shop::printProduct);

        if (priceProducts.isEmpty()) {
            System.out.println("No products were found");
        }
    }

    public static void searchByBrand(String brand) {
        var brandProducts = storeInventory.stream()
                .filter(product -> product.getBrand().equalsIgnoreCase(brand))
                .collect(Collectors.toList());

        brandProducts.forEach(Shop::printProduct);

        if (brandProducts.isEmpty()) {
            System.out.println("No products were found");
        }
    }

    public static void searchProductByCategory(String category) {
        var categoryProducts = storeInventory.stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());

        categoryProducts.forEach(Shop::printProduct);

        if (categoryProducts.isEmpty()) {
            System.out.println("No products were found");
        }
    }

    public static void editStoredProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Name of the product you want to edit in the store inventory");
        var input = scanner.nextLine();

        var editProduct = storeInventory.stream()
                .filter(product -> product.getName().equalsIgnoreCase(input))
                .findFirst();

        editProduct.ifPresentOrElse(
                (product) -> {
                    String input2;
                    do {
                        printProduct(product);
                        System.out.println("What do you want to edit? (name, price, brand, category, amount)");
                        input2 = scanner.nextLine();
                    } while (!input2.equals("name") && !input2.equals("price") && !input2.equals("brand") && !input2.equals("category") && !input2.equals("amount"));

                    if (input2.equalsIgnoreCase("name")) {
                        System.out.println("Enter new name for " + product.getName());
                        product.setName(scanner.nextLine().replaceAll(" ", "_"));

                    } else if (input2.equalsIgnoreCase("price")) {
                        String price;
                        do {
                            System.out.println("Enter new price for the product");
                            price = scanner.nextLine().replaceAll(" ", "");
                        } while (!parseIntTrueOrFalse(price));
                        product.setPrice(Integer.parseInt(price));

                    } else if (input2.equalsIgnoreCase("brand")) {
                        System.out.println("Enter new brand for the product");
                        product.setBrand(scanner.nextLine().replaceAll(" ", "_"));

                    } else if (input2.equalsIgnoreCase("category")) {
                        System.out.println("Enter new category for the product");
                        product.setCategory(scanner.nextLine().replaceAll(" ", "_"));

                    } else {
                        String amount;
                        do {
                            System.out.println("Enter new amount for the product");
                            amount = scanner.nextLine().replaceAll(" ", "");
                        } while (!parseIntTrueOrFalse(amount));
                        product.setAmount(Integer.parseInt(amount));
                    }
                    System.out.println("Product Updated!");
                },
                () -> System.out.println("That product could not be found"));
    }

    public static void addCategory() {  //att vi kan lägga till nya kategorier enkelt enligt uppgiften
        System.out.println("Name of the category you want to add.");
        Scanner scanner = new Scanner(System.in);
        var input = scanner.nextLine();

        if (!Product.validCategories.contains(input)) {
            Product.validCategories.add(input);
            FileHandler.saveValidCategoriesToFile();
            System.out.println("Category added!");
        } else {
            System.out.println("This category already exists");
        }
    }

    public static void saveStoreInventory() {
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            System.out.println("Do you want to save the store inventory Y/N");
            input = scanner.nextLine();
        } while (!input.equalsIgnoreCase("Y") && !input.equalsIgnoreCase("N"));

        if (input.equalsIgnoreCase("Y")) {
            FileHandler.writeStoreInventoryToFile();
        }
    }

    public static void storeInventorySeeder() { // Metod för att lägga till produkter i store inventory om filen är tom
        storeInventory.add(new Product("Levain", 33, "Pågen", 1, "Bakery", 5));
        storeInventory.add(new Product("Gifflar", 25, "Pågen", 2, "Bakery", 5));
        storeInventory.add(new Product("Abisko", 24, "Polarbröd", 3, "Bakery", 5));
        storeInventory.add(new Product("Mjölk", 16, "Arla", 4, "Dairy", 5));
        storeInventory.add(new Product("Vispgrädde_10%", 15, "Arla", 5, "Dairy", 5));
        storeInventory.add(new Product("Gräddfil", 10, "Arla", 6, "Dairy", 5));
        storeInventory.add(new Product("Cookiedough", 50, "Ben", 7, "FrozenFood", 5));
        storeInventory.add(new Product("Chicken_nuggets", 50, "Max", 8, "FrozenFood", 5));
        storeInventory.add(new Product("Calzone", 15, "Grandiosa", 9, "FrozenFood", 5));

        FileHandler.writeStoreInventoryToFile();
    }

    public static void printProduct(Product product) { // eftersom man inte fick använda ToString() för att skriva ut produkter i konsolen
        System.out.println("Name: " + product.getName() + " Price: " + product.getPrice() + " Brand: " + product.getBrand() + " Category: " + product.getCategory() + " Amount: " + product.getAmount());
    }

    public static void validCategoriesSeeder() { // Metod för att lägga in kategorier i validCategories om filen är tom.
        Product.validCategories = List.of("Bakery", "Dairy", "FrozenFood");
        FileHandler.saveValidCategoriesToFile();
    }

    public static boolean parseIntTrueOrFalse(String value) { // Metod för att kolla ifall användaren har angivet en siffra eller inte.
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            System.out.println(value + " Is not a number");
            return false;
        }
    }
}
