package model;

public enum OrderStatus {
    NEW("Новий"),
    PROCESSING("В обробці"),
    PAID("Оплачено"),
    SHIPPED("Відправлено"),
    CANCELED("Скасовано");

    private final String ukrainianName;

    OrderStatus(String ukrainianName) {
        this.ukrainianName = ukrainianName;
    }

    public String getUkrainianName() {
        return ukrainianName;
    }

    public void printDescription() {
        System.out.println("Статус замовлення: " + ukrainianName);
    }
}