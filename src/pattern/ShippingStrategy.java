package pattern;

public interface ShippingStrategy {
    double calculateShippingCost(double orderAmount);
    String getShippingDescription();
}