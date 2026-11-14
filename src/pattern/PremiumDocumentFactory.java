package pattern;

public class PremiumDocumentFactory implements DocumentFactory {
    @Override
    public StoreDocument createWarranty() {
        return new WarrantyCard(); // Можна розширити преміум-версією
    }

    @Override
    public StoreDocument createReceipt() {
        return new Receipt();
    }
}
