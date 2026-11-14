package service;

import exception.InvalidPriceException;
import exception.ProductNotFoundException;
import exception.ShopException;
import model.*;
import java.io.*;
import java.util.List;
import java.util.Optional;

public class ShopService {
    private Repository<Product, String> productRepository = new Repository<>();
    private final String FILE_NAME = "shop_data_lab5.ser";

    public ShopService() {
        loadData();
        if (productRepository.getAll().isEmpty()) {
            productRepository.add(new Electronics("1", "Ноутбук", 25000.0, 12));
            productRepository.add(new Electronics("2", "Мишка", 750.0, 6));
            productRepository.add(new Clothing("3", "Худі", 1200.0, "M"));
        }
    }

    public void addProduct(Product product) throws ShopException {
        if (product.getPrice() <= 0) {
            throw new InvalidPriceException(product.getPrice());
        }
        productRepository.add(product);
        saveData();
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


    public void printTotalCatalogPrice(List<? extends Product> products) {
        double sum = 0;
        for (Product p : products) {
            sum += p.getPrice();
        }
        System.out.printf("Загальна вартість усіх товарів у вибірці: %.2f грн\n", sum);
    }

    public List<Product> getAllProducts() {
        return productRepository.getAll();
    }

    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(productRepository);
        } catch (IOException e) {
            System.out.println("Помилка збереження файлу: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            productRepository = (Repository<Product, String>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Помилка завантаження файлу: " + e.getMessage());
        }
    }
}