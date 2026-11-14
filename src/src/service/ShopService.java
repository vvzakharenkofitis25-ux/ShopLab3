package service;

import exception.InvalidPriceException;
import exception.ProductNotFoundException;
import exception.ShopException;
import model.*;
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
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PRICE_PATTERN = Pattern.compile("^\\d+(\\.\\d{1,2})?$");

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
        fileManager.saveProductsToBinary(productRepository.getAll());
        fileManager.logAction("Додано новий товар: " + product.getName());
    }

    public void showCatalogPage(int page, int pageSize) {
        List<Product> catalog = productRepository.getAll();
        int totalProducts = catalog.size();
        int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

        if (page < 1 || page > totalPages) {
            System.out.println("Невірний номер сторінки!");
            return;
        }

        System.out.printf("\n--- КАТАЛОГ (Сторінка %d з %d) ---\n", page, totalPages);
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, totalProducts);

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
        String cleanedLine = csvLine.trim();
        String[] parts = cleanedLine.split(",");
        if (parts.length < 4) {
            throw new IllegalArgumentException("Невірний формат CSV рядка.");
        }

        String id = parts[0].trim();
        String name = parts[1].trim().replace("_", " ");
        String categoryRaw = parts[2].trim();
        String category = categoryRaw.substring(0, 1).toUpperCase() + categoryRaw.substring(1).toLowerCase();
        double price = Double.parseDouble(parts[3].trim());

        return new Electronics(id, name, price, 12);
    }

    public boolean validateId(String id) { return ID_PATTERN.matcher(id).matches(); }
    public boolean validateEmail(String email) { return EMAIL_PATTERN.matcher(email).matches(); }
    public boolean validatePriceFormat(String priceStr) { return PRICE_PATTERN.matcher(priceStr).matches(); }

    public void generateFormattedReportToFile(String filename) {
        StringBuilder sb = new StringBuilder();
        sb.append("====================================================\n");
        sb.append("               ТОВАРНИЙ ЗВІТ МАГАЗИНУ               \n");
        sb.append("====================================================\n");
        sb.append(String.format("%-6s | %-15s | %-12s | %-10s\n", "ID", "Назва", "Категорія", "Ціна (грн)"));
        sb.append("----------------------------------------------------\n");

        List<Product> products = productRepository.getAll();
        for (Product p : products) {
            sb.append(String.format("%-6s | %-15s | %-12s | %10.2f\n", p.getId(), p.getName(), p.getCategory(), p.getPrice()));
        }

        sb.append("----------------------------------------------------\n");
        sb.append("Загальна кількість товарів у каталозі: ").append(products.size()).append("\n");
        sb.append("====================================================\n");

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.write(sb.toString());
            System.out.println("Звіт успішно збережено у файл: " + filename);
        } catch (IOException e) {
            System.out.println("Помилка запису файлу звіту: " + e.getMessage());
        }
        fileManager.logAction("Згенеровано текстовий звіт магазину.");
    }

    public void runStringBenchmark() {
        int iterations = 100_000;
        System.out.println("\n--- БЕНЧМАРК КОНКАТЕНАЦІЇ РЯДКІВ (" + iterations + " ітерацій) ---");
        long startTime1 = System.currentTimeMillis();
        String str = "";
        for (int i = 0; i < iterations; i++) { str += "a"; }
        long duration1 = System.currentTimeMillis() - startTime1;
        System.out.println("Час з '+': " + duration1 + " мс");

        long startTime2 = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iterations; i++) { sb.append("a"); }
        long duration2 = System.currentTimeMillis() - startTime2;
        System.out.println("Час з StringBuilder: " + duration2 + " мс");
        System.out.println("------------------------------------------------------------------");
    }

    public void performFileOperationsDemo() {
        System.out.println("\n--- ДЕМОНСТРАЦІЯ ЛАБОРАТОРНОЇ №7 ---");
        fileManager.printFileMetadata("data_storage/products_text.txt");
        fileManager.saveProductsToBinary(productRepository.getAll());
        fileManager.createZipBackup();
    }

    public List<Product> getAllProducts() { return productRepository.getAll(); }
}