package model;

public class Clothing extends Product {
    private String size;

    public Clothing(String id, String name, double price, String size) {
        super(id, name, price, "Одяг");
        this.size = size;
    }

    @Override
    public void displayProductDetails() {
        System.out.printf("[Одяг] %s | Ціна: %.2f грн | Розмір: %s\n", getName(), getPrice(), size);
    }

    @Override
    public String getDeliveryInfo() {
        return "Доставка у поштомат (у фірмовому пакеті).";
    }
}