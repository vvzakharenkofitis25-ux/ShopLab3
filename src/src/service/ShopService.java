package service;

import exception.InvalidPriceException;
import exception.ProductNotFoundException;
import exception.ShopException;
import model.*;
import pattern.*;
import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopService implements Serializable {
    private static final long serialVersionUID = 1L;

    private Repository<Product, String> productRepository = new Repository<>();
    private FileManager fileManager = new FileManager();

    private static final Pattern ID_PATTERN = Pattern.compile("^[A-Za-z0-9]{1,5}$");

    public ShopService() {
        fileManager.initializeStorage();
        List<Product> loaded = fileManager.loadProductsFromText();
        if (!loaded.isEmpty()) {
            for (Product p : loaded) {
                productRepository.add(p);
            }
        } else {
            productRepository.add(new Electronics("1", "Ноутбук", 25000.0, 12));
            productRepository.add(new Electronics("2", "Мишка", 750.0, 6));
            productRepository.add(new Clothing("3", "Худі", 1200.0, "M"));
            fileManager.saveProductsToText(productRepository.getAll());
        }
    }

    public void addProduct(Product product) throws ShopException {
        if (product.getPrice() <= 0) {
            throw new InvalidPriceException(product.getPrice());
        }
        productRepository.add(product);
        fileManager.saveProductsToText(productRepository.getAll());
        fileManager.logAction("Додано товар: " + product.getName());
    }

    public void demoDesignPatterns() {
        System.out.println("\n--- ДЕМОНСТРАЦІЯ ШАБЛОНІВ ПРОЄКТУВАННЯ (ЛР9) ---");

        ShopConfig config = ShopConfig.getInstance();
        System.out.println("[Singleton]: Назва магазину: " + config.getStoreName() +
                " | Валюта: " + config.getCurrency() +
                " | ПДВ: " + config.getTaxRate() + "%");

        System.out.println("[Abstract Factory]: Створення документації через фабрику преміум-товарів:");
        DocumentFactory factory = new PremiumDocumentFactory();
        StoreDocument warranty = factory.createWarranty();
        StoreDocument receipt = factory.createReceipt();
        warranty.printDocumentInfo();
        receipt.printDocumentInfo();
    }

    public void showCatalogPage(int page, int pageSize) {
        List<Product> catalog = productRepository.getAll();
        int totalPages = (int) Math.ceil((double) catalog.size() / pageSize);
        if (page < 1 || page > totalPages) { System.out.println("Невірний номер сторінки!"); return; }

        System.out.printf("\n--- КАТАЛОГ (Сторінка %d з %d) [Магазин: %s] ---\n",
                page, totalPages, ShopConfig.getInstance().getStoreName());

        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, catalog.size());
        for (int i = start; i < end; i++) {
            catalog.get(i).displayProductDetails();
        }
        System.out.println("------------------------------------");
    }

    public Product getProductById(String id) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product parseProductFromCsv(String csvLine) {
        String[] parts = csvLine.trim().split(",");
        if (parts.length < 4) throw new IllegalArgumentException("Невірний формат CSV.");
        return new Electronics(parts[0].trim(), parts[1].trim().replace("_", " "), Double.parseDouble(parts[3].trim()), 12);
    }

    public List<Product> getAllProducts() { return productRepository.getAll(); }
}