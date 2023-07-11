package Lab2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileHandler {

    public static void fileHandler() {
        FileHandler.CheckStoreInventoryFile();
        FileHandler.GatherStoreInventory();
        FileHandler.CheckValidCategoriesFile();
        FileHandler.gatherValidCategoriesFile();
    }

    public static void CheckStoreInventoryFile() {
        try {
            File file = new File("storeInventory.txt");

            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
                System.out.println(file.getAbsolutePath());
            } else {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public static void GatherStoreInventory() {
        Path path = Path.of("storeInventory.txt");
        try {
            Stream<String> lines = Files.lines(path);
            {
                lines.forEach((product -> {
                    var products = product.split(" ");
                    Shop.storeInventory.add(new Product(products[0], Integer.parseInt(products[1]), products[2], Integer.parseInt(products[3]), products[4], Integer.parseInt(products[5])));
                }));
            }
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
            Shop.storeInventorySeeder();
        }
    }

    public static void CheckValidCategoriesFile() {
        try {
            File file = new File("validCategories.txt");

            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
                System.out.println(file.getAbsolutePath());
            } else {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public static void gatherValidCategoriesFile() {
        Path path = Path.of("validCategories.txt");
        try {
            Stream<String> lines = Files.lines(path);
            lines.forEach((category -> Product.validCategories.add(category)));
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
            Shop.validCategoriesSeeder();
        }
    }

    public static void saveValidCategoriesToFile() {
        File file = new File("validCategories.txt");
        if (file.delete()) {
            FileHandler.CheckValidCategoriesFile();
            try {
                FileWriter fileWriter = new FileWriter("validCategories.txt");
                for (var category : Product.validCategories)
                    fileWriter.write(category + " ");
                fileWriter.close();
                System.out.println("successfully wrote to the file");
            } catch (IOException e) {
                System.out.println("An error occurred");
                e.printStackTrace();
            }
        }
    }

    public static void writeStoreInventoryToFile() {
        File file = new File("storeInventory.txt");
        if (file.delete()) {
            FileHandler.CheckStoreInventoryFile();
            try {
                FileWriter fileWriter = new FileWriter("storeInventory.txt");
                for (var product : Shop.storeInventory)
                    fileWriter.write(product.getName() + " " + product.getPrice() + " " + product.getBrand() + " " + product.getBarcode() + " " + product.getCategory() + " " + product.getAmount() + "\n");
                fileWriter.close();
                System.out.println("successfully wrote to the file");
            } catch (IOException e) {
                System.out.println("An error occurred");
                e.printStackTrace();
            }
        }
    }
}

