package exception;

public class ProductNotFoundException extends ShopException {
    private String productId;

    public ProductNotFoundException(String productId) {
        super("Товар з ID '" + productId + "' не знайдено в каталозі.");
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }
}