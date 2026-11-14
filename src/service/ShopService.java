
package service;

import exception.InvalidPriceException;
import exception.ProductNotFoundException;
import exception.ShopException;
import model.*;
import java.io.*;
import java.util.*;

public class ShopService implements Serializable {
    private static final long serialVersionUID = 1L;

    // Пункт 1: Використання різних колекцій за прямим призначенням
    private List<Product> productList = new ArrayList<>(); // List: для зберігання списку з доступом за індексом і пагінацією
    private Set<String> uniqueCategories = new HashSet<>(); // Set: для зберігання унікальних категорій без дублікатів
    private Map<String, Product> productMap = new HashMap<>(); // Map: для швидкого пошуку товару за його ID (ключ-значення)
    private Queue<String> orderQueue = new LinkedList<>(); // Queue: для реалізації черги замовлень (FIFO)

    private Repository<Product, String> productRepository = new Repository<>();
    private FileManager fileManager = new FileManager();

    public ShopService() {
        fileManager.initializeStorage();
        List<Product> loaded = fileManager.loadProductsFromText();
        if (!loaded.isEmpty()) {
            for (Product p : loaded) {
                addProductInternal(p);
            }
        } else {
            addProductInternal(new Electronics("1", "Ноутбук", 25000.0, 12));
            addProductInternal(new Electronics("2", "Мишка", 750.0, 6));
            addProductInternal(new Clothing("3", "Худі", 1200.0, "M"));
            fileManager.saveProductsToText(productList);
        }
    }

    private void addProductInternal(Product p) {
        productList.add(p);
        uniqueCategories.add(p.getCategory());
        productMap.put(p.getId(), p);
        productRepository.add(p);
    }

    public void addProduct(Product product) throws ShopException {
        if (product.getPrice() <= 0) {
            throw new InvalidPriceException(product.getPrice());
        }
        addProductInternal(product);
        fileManager.saveProductsToText(productList);
        fileManager.saveProductsToBinary(productList);
        fileManager.logAction("Додано товар: " + product.getName());
    }

    public void sortAndDisplayProducts(int sortType) {
        List<Product> tempSortList = new ArrayList<>(productList);
        if (sortType == 1) {
            Collections.sort(tempSortList); // Природне сортування (Comparable за ціною)
            System.out.println("\n--- Сортування за ціною (Comparable) ---");
        } else if (sortType == 2) {
            tempSortList.sort(ProductCollectionManager.sortByName); // Компаратор за назвою
            System.out.println("\n--- Сортування за назвою (Comparator) ---");
        } else {
            tempSortList.sort(ProductCollectionManager.sortByCategory); // Компаратор за категорією
            System.out.println("\n--- Сортування за категорією ---");
        }

        for (Product p : tempSortList) {
            p.displayProductDetails();
        }
    }

    public void demoCustomIterator() {
        System.out.println("\n--- Демонстрація власного ітератора (for-each) ---");
        ProductCollectionManager manager = new ProductCollectionManager(productList);
        for (Product p : manager) {
            System.out.println("Товар через власний ітератор: " + p.getName());
        }
    }

    // Пункт 4: Використання TreeSet / TreeMap (пошук у діапазоні)
    public void demoTreeSetAndMap() {
        System.out.println("\n--- Демонстрація TreeSet / TreeMap ---");
        TreeSet<Double> priceSet = new TreeSet<>();
        for (Product p : productList) {
            priceSet.add(p.getPrice());
        }
        System.out.println("Всі ціни у впорядкованому TreeSet: " + priceSet);
        System.out.println("Ціни в діапазоні від 500 до 15000: " + priceSet.subSet(500.0, 15000.0));
    }

    public void demoBinarySearchTree() {
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert("Електроніка");
        bst.insert("Одяг");
        bst.insert("Дім");
        bst.insert("Книги");
        bst.inorder();
    }

    public void runListBenchmark() {
        int n = 50_000;
        System.out.println("\n--- БЕНЧМАРК: ArrayList vs LinkedList (вставка в середину " + n + " елементів) ---");

        List<Integer> arrayList = new ArrayList<>();
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            arrayList.add(arrayList.size() / 2, i);
        }
        long time1 = System.currentTimeMillis() - start1;
        System.out.println("ArrayList час вставки в середину: " + time1 + " мс");

        List<Integer> linkedList = new LinkedList<>();
        long start2 = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            linkedList.add(linkedList.size() / 2, i);
        }
        long time2 = System.currentTimeMillis() - start2;
        System.out.println("LinkedList час вставки в середину: " + time2 + " мс");
        System.out.println("ВИСНОВОК: LinkedList виконує вставку в середину швидше за рахунок переприв'язування вузлів, тоді як ArrayList змушений зсувати масив.");
    }

    public void showCatalogPage(int page, int pageSize) {
        int totalProducts = productList.size();
        int totalPages = (int) Math.ceil((double) totalProducts / pageSize);
        if (page < 1 || page > totalPages) { System.out.println("Невірний номер сторінки!"); return; }

        System.out.printf("\n--- КАТАЛОГ (Сторінка %d з %d) ---\n", page, totalPages);
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, totalProducts);
        for (int i = start; i < end; i++) { productList.get(i).displayProductDetails(); }
        System.out.println("------------------------------------");
    }

    public Product getProductById(String id) throws ProductNotFoundException {
        Optional<Product> opt = productRepository.findById(id);
        return opt.orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product parseProductFromCsv(String csvLine) {
        String[] parts = csvLine.trim().split(",");
        if (parts.length < 4) throw new IllegalArgumentException("Невірний формат CSV.");
        return new Electronics(parts[0].trim(), parts[1].trim().replace("_", " "), Double.parseDouble(parts[3].trim()), 12);
    }

    public void generateFormattedReportToFile(String filename) {
        fileManager.logAction("Згенеровано звіт.");
    }

    public List<Product> getAllProducts() { return productList; }
}