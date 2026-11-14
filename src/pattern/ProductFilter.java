package pattern;

import model.Product;

@FunctionalInterface
public interface ProductFilter {
    boolean test(Product product);
}
