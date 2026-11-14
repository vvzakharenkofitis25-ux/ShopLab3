package model;

import java.io.Serializable;
import java.util.Objects;

public abstract class Product implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private double price;
    private String category;

    public Product(String id, String name, double price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }


    public abstract void displayProductDetails();
    public abstract String getDeliveryInfo();

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }

    public void setPrice(double price) { this.price = price; }


    @Override
    public String toString() {
        return String.format("Product{id='%s', name='%s', price=%.2f, category='%s'}", id, name, price, category);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 &&
                Objects.equals(id, product.id) &&
                Objects.equals(name, name) &&
                Objects.equals(category, category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, category);
    }


    @Override
    public Product clone() {
        try {
            return (Product) super.clone(); // поверхневе копіювання
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}