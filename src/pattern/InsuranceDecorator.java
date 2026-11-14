package pattern;

public class InsuranceDecorator implements ProductPrice {
    private ProductPrice wrappable;

    public InsuranceDecorator(ProductPrice wrappable) {
        this.wrappable = wrappable;
    }

    @Override
    public double getCost() {
        return wrappable.getCost() + 150.0;
    }

    @Override
    public String getDescription() {
        return wrappable.getDescription() + " + страховка доставки";
    }
}
