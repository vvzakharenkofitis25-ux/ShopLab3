package pattern;

public class GiftWrapDecorator implements ProductPrice {
    private ProductPrice wrappable;

    public GiftWrapDecorator(ProductPrice wrappable) {
        this.wrappable = wrappable;
    }

    @Override
    public double getCost() {
        return wrappable.getCost() + 75.0;
    }

    @Override
    public String getDescription() {
        return wrappable.getDescription() + " + подарункове упакування";
    }
}