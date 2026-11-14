package pattern;

public class BaseProductPrice implements ProductPrice {
    private double price;
    private String name;

    public BaseProductPrice(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public double getCost() {
        return price;
    }

    @Override
    public String getDescription() {
        return name;
    }
}