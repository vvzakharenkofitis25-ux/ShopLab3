package model;

public class Electronics extends Product {
    private int warrantyMonths;

    public Electronics(String id, String name, double price, int warrantyMonths) {
        super(id, name, price, "Електроніка");
        this.warrantyMonths = warrantyMonths;
    }

    @Override
    public void displayProductDetails() {
        System.out.printf("[Електроніка] %s | Ціна: %.2f грн | Гарантія: %d міс.\n", getName(), getPrice(), warrantyMonths);
    }

    @Override
    public String getDeliveryInfo() {
        return "Доставка кур'єром у захисній коробці (крихкий товар).";
    }
}
