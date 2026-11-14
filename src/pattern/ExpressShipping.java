package pattern;

public class ExpressShipping implements ShippingStrategy {
    @Override
    public double calculateShippingCost(double orderAmount) {
        return 250.0;
    }

    @Override
    public String getShippingDescription() {
        return "Експрес-доставка кур'єром до дверей - 250 грн";
    }
}