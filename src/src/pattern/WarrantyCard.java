package pattern;

public class WarrantyCard implements StoreDocument {
    @Override
    public void printDocumentInfo() {
        System.out.println("Видано офіційний Гарантійний талон (12 місяців).");
    }
}