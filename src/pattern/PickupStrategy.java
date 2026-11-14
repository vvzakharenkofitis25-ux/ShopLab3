package pattern;

public class PickupStrategy implements ShippingStrategy {
    @Override
    public double calculateShippingCost(double orderAmount) {
        return 0.0;
    }

    @Override
    public String getShippingDescription() {
        return "Самовивіз із магазину/пункту видачі - Безкоштовно (0 грн)";
    }
}