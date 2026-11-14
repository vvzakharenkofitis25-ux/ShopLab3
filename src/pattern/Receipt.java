package pattern;

public class Receipt implements StoreDocument {
    @Override
    public void printDocumentInfo() {
        System.out.println("Згенеровано фіскальний чек інтернет-магазину.");
    }
}