package pattern;

public class StandardShipping implements ShippingStrategy {
    @Override
    public double calculateShippingCost(double orderAmount) {
        return orderAmount > 2000 ? 0.0 : 100.0; // Безкоштовно при замовленні від 2000 грн
    }

    @Override
    public String getShippingDescription() {
        return "Стандартна доставка (Нова Пошта / Укрпошта) - 100 грн (безкоштовно від 2000 грн)";
    }
}