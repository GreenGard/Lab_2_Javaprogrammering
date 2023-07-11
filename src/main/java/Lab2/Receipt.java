package Lab2;

import java.util.ArrayList;

public class Receipt {
    static int totalCost = 0;

    public static void outputReceipt() {
        ArrayList<Product> cartStream = new ArrayList<>(Shop.cart);

        cartStream.forEach((product) -> {
            System.out.println("Product: " + product.getName() + " " + product.getAmount() + "x" + " Price: " + product.getPrice());
            totalCost += product.getPrice() * product.getAmount();
            totalCost += discount(product);
        });

        totalCost += discount(totalCost);
        System.out.println("Total cost: " + totalCost);
    }

    public static int discount(Product discountProduct) { // om man köpt minst 3 av en vara får man en av dess varor på köpet.
        if (discountProduct.getAmount() >= 3) {
            System.out.println("You have received a " + discountProduct.getPrice() + " kr discount from buy 3 pay for 2 from" + discountProduct.getCategory());
            return -discountProduct.getPrice();
        }
        return 0;
    }

    public static int discount(int totalCost) {// 10 % rabatt om du handlar minst 50 kr.
        if (totalCost >= 50) {
            System.out.println("You have received a 10% discount for shopping above 50 kr");
            int temp = totalCost;
            totalCost = (totalCost / 10) * 9;
            temp -= totalCost;
            return -temp;
        }
        return 0;
    }
}