package pattern;

public class ShippingContext {
    private ShippingStrategy strategy;

    public void setStrategy(ShippingStrategy strategy) {
        this.strategy = strategy;
    }

    public double executeCalculation(double orderAmount) {
        if (strategy == null) {
            throw new IllegalStateException("Стратегію доставки не обрано!");
        }
        return strategy.calculateShippingCost(orderAmount);
    }

    public String getDescription() {
        if (strategy == null) return "Стратегія не задана";
        return strategy.getShippingDescription();
    }
}