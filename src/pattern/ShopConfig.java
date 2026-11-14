package pattern;

public class ShopConfig {
    private static volatile ShopConfig instance;

    private String storeName;
    private String currency;
    private double taxRate;

    private ShopConfig() {
        this.storeName = "ТехноСвіт (Інтернет-магазин)";
        this.currency = "грн";
        this.taxRate = 20.0; // ПДВ у відсотках
    }

    public static ShopConfig getInstance() {
        if (instance == null) {
            synchronized (ShopConfig.class) {
                if (instance == null) {
                    instance = new ShopConfig();
                }
            }
        }
        return instance;
    }

    public String getStoreName() { return storeName; }
    public String getCurrency() { return currency; }
    public double getTaxRate() { return taxRate; }
}