package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<Product, Integer> items = new HashMap<>();

    public void addProduct(Product product, int quantity) {
        items.put(product, items.getOrDefault(product, 0) + quantity);
    }

    public void removeProduct(Product product) {
        items.remove(product);
    }

    public Map<Product, Integer> getItems() {
        return items;
    }

    public double getTotalAmount() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    public void generateInvoice() {
        System.out.println("\n========= РАХУНОК-ФАКТУРА =========");
        if (items.isEmpty()) {
            System.out.println("Кошик порожній.");
        } else {
            for (Map.Entry<Product, Integer> entry : items.entrySet()) {
                Product p = entry.getKey();
                int qty = entry.getValue();
                System.out.printf("%s x %d шт. = %.2f грн\n", p.getName(), qty, p.getPrice() * qty);
            }
            System.out.println("-----------------------------------");
            System.out.printf("ЗАГАЛЬНА СУМА ДО СПЛАТИ: %.2f грн\n", getTotalAmount());
        }
        System.out.println("===================================\n");
    }
}
