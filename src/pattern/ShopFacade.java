package pattern;

import service.ShopService;

public class ShopFacade {
    private ShopService shopService;

    public ShopFacade(ShopService shopService) {
        this.shopService = shopService;
    }

    public void completeOrderOperation(String csvData) {
        try {
            shopService.addProduct(shopService.parseProductFromCsv(csvData));
            shopService.generateFormattedReportToFile("shop_report.txt");
            System.out.println("Операція фасаду виконана успішно.");
        } catch (Exception e) {
            System.out.println("Помилка у фасаді: " + e.getMessage());
        }
    }
}