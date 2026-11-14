package pattern;

public class ExtendedWarrantyDecorator implements ProductPrice {
    private ProductPrice wrappable;

    public ExtendedWarrantyDecorator(ProductPrice wrappable) {
        this.wrappable = wrappable;
    }

    @Override
    public double getCost() {
        return wrappable.getCost() + 300.0;
    }

    @Override
    public String getDescription() {
        return wrappable.getDescription() + " + розширена гарантія";
    }
}
