package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Cart implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    private Map<Product, Integer> items = new HashMap<>();
    private OrderStatus status = OrderStatus.NEW;

    public void addProduct(Product product, int quantity) {
        items.put(product, items.getOrDefault(product, 0) + quantity);
    }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public void generateInvoice() {
        System.out.println("\n========= РАХУНОК-ФАКТУРА =========");
        status.printDescription();
        if (items.isEmpty()) {
            System.out.println("Кошик порожній.");
        } else {
            for (Map.Entry<Product, Integer> entry : items.entrySet()) {
                Product p = entry.getKey();
                int qty = entry.getValue();
                System.out.printf("%s x %d шт. = %.2f грн\n", p.getName(), qty, p.getPrice() * qty);
                System.out.println("   -> Спосіб доставки: " + p.getDeliveryInfo());
            }
            System.out.println("-----------------------------------");
            System.out.printf("ЗАГАЛЬНА СУМА: %.2f грн\n", getTotalAmount());
        }
        System.out.println("===================================\n");
    }

    public double getTotalAmount() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }


    @Override
    public Cart clone() {
        try {

            Cart clonedCart = (Cart) super.clone();

            clonedCart.items = new HashMap<>(this.items);
            return clonedCart;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}