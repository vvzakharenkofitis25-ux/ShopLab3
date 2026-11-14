package service;

import model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ShopService {
    private List<Product> catalog = new ArrayList<>();
    private final String FILE_NAME = "shop_data_lab4.ser";

    public ShopService() {
        loadData();
        if (catalog.isEmpty()) {
            catalog.add(new Electronics("1", "Ноутбук", 25000.0, 12));
            catalog.add(new Electronics("2", "Мишка", 750.0, 6));
            catalog.add(new Clothing("3", "Худі", 1200.0, "M"));
            catalog.add(new Clothing("4", "Куртка", 3500.0, "L"));
        }
    }

    public void addProduct(Product product) {
        catalog.add(product);
        saveData();
    }

    public void showCatalogPage(int page, int pageSize) {
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


    public void processProductSpecificFeatures(Product product) {
        System.out.println("\n--- Аналіз специфіки товару ---");
        if (product instanceof Electronics el) {
            System.out.println("Це електроніка. Знижка на обслуговування доступна. Гарантія діє.");
        } else if (product instanceof Clothing cl) {
            System.out.println("Це одяг. Доступна послуга примірки у відділенні.");
        } else {
            System.out.println("Загальний товар.");
        }
    }

    public Product getProductById(String id) {
        return catalog.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(catalog);
        } catch (IOException e) {
            System.out.println("Помилка збереження: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            catalog = (List<Product>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Помилка завантаження: " + e.getMessage());
        }
    }
}