package service;

import exception.InvalidPriceException;
import exception.ProductNotFoundException;
import exception.ShopException;
import model.*;
import pattern.*;
import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ShopService implements Serializable {
    private static final long serialVersionUID = 1L;

    private Repository<Product, String> productRepository = new Repository<>();
    private FileManager fileManager = new FileManager();
    private TaskProcessor taskProcessor = new TaskProcessor();

    private ProductPublisher publisher = new ProductPublisher();
    private ShippingContext shippingContext = new ShippingContext();

    private static final Pattern ID_PATTERN = Pattern.compile("^[A-Za-z0-9]{1,5}$");

    public ShopService() {
        fileManager.initializeStorage();
        List<Product> loaded = fileManager.loadProductsFromText();
        if (!loaded.isEmpty()) {
            loaded.forEach(productRepository::add);
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
        publisher.notifyObservers(product.getName());
    }

    public void demoStreamAndFunctionalInterfaces() {
        List<Product> products = productRepository.getAll();

        Predicate<Product> expensiveFilter = p -> p.getPrice() > 1000.0;
        Function<Product, String> nameMapper = p -> p.getName().toUpperCase();
        Consumer<String> printer = name -> System.out.println("Товар (Upper): " + name);

        products.stream()
                .filter(expensiveFilter)
                .map(nameMapper)
                .forEach(printer);

        products.stream()
                .sorted((p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice()))
                .forEach(p -> System.out.println(p.getName() + " - " + p.getPrice() + " грн"));

        double totalPrice = products.stream()
                .map(Product::getPrice)
                .reduce(0.0, Double::sum);
        System.out.println("Загальна вартість каталогу: " + totalPrice + " грн");

        List<String> cheapProducts = products.stream()
                .filter(p -> p.getPrice() < 5000)
                .map(Product::getName)
                .collect(Collectors.toList());
        System.out.println("Бюджетні товари: " + cheapProducts);

        ProductFilter customFilter = p -> p.getCategory().equalsIgnoreCase("Електроніка");
        products.stream()
                .filter(customFilter::test)
                .forEach(p -> System.out.println("Електроніка з фільтра: " + p.getName()));

        taskProcessor.runMultithreadingDemo(products);
    }

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

    public void showCatalogPage(int page, int pageSize) {
        List<Product> catalog = productRepository.getAll();
        int totalPages = (int) Math.ceil((double) catalog.size() / pageSize);
        if (page < 1 || page > totalPages) { System.out.println("Невірний номер сторінки!"); return; }

        System.out.printf("\n--- КАТАЛОГ (Сторінка %d з %d) ---\n", page, totalPages);
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, catalog.size());

        catalog.subList(start, end).forEach(Product::displayProductDetails);
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