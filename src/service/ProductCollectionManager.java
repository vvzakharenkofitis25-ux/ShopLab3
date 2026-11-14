package service;

import model.Product;
import java.util.Iterator;
import java.util.List;
import java.util.Comparator;

public class ProductCollectionManager implements Iterable<Product> {
    private List<Product> products;

    public ProductCollectionManager(List<Product> products) {
        this.products = products;
    }

    @Override
    public Iterator<Product> iterator() {
        return new ProductIterator();
    }

    private class ProductIterator implements Iterator<Product> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < products.size();
        }

        @Override
        public Product next() {
            return products.get(currentIndex++);
        }
    }

    public static Comparator<Product> sortByName = Comparator.comparing(Product::getName);
    public static Comparator<Product> sortByCategory = Comparator.comparing(Product::getCategory)
            .thenComparing(Product::getPrice);
}