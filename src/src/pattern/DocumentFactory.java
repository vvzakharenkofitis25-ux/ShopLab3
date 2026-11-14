package pattern;

public interface DocumentFactory {
    StoreDocument createWarranty();
    StoreDocument createReceipt();
}