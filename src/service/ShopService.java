package service;

import model.Product;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ShopService {
    private List<Product> catalog = new ArrayList<>();
    private final String FILE_NAME = "shop_data.ser";

    public ShopService() {
        loadData();
        if (catalog.isEmpty()) {
            catalog.add(new Product("1", "Ноутбук", 25000.0, "Електроніка"));
            catalog.add(new Product("2", "Мишка", 750.0, "Електроніка"));
            catalog.add(new Product("3", "Клавіатура", 1500.0, "Електроніка"));
            catalog.add(new Product("4", "Чашка", 200.0, "Дім"));
            catalog.add(new Product("5", "Плед", 800.0, "Дім"));
        }
    }

    public void addProduct(Product product) {
        catalog.add(product);
        saveData();
    }

    public void removeProduct(String id) {
        catalog.removeIf(p -> p.getId().equals(id));
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
            System.out.println(catalog.get(i));
        }
        System.out.println("------------------------------------");
    }

    public Product getProductById(String id) {
        return catalog.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(catalog);
        } catch (IOException e) {
            System.out.println("Помилка збереження даних: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            catalog = (List<Product>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Помилка завантаження даних: " + e.getMessage());
        }
    }
}
