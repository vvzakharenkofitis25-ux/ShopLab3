package main;

import model.Cart;
import model.Product;
import service.ShopService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ShopService service = new ShopService();
        Cart cart = new Cart();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Вітаємо в консольному інтернет-магазині!");

        while (running) {
            System.out.println("\nОберіть дію:");
            System.out.println("1. Переглянути каталог (з пагінацією)");
            System.out.println("2. Додати товар у кошик");
            System.out.println("3. Переглянути кошик та рахунок-фактуру");
            System.out.println("4. Додати новий товар до магазину");
            System.out.println("5. Видалити товар з магазину");
            System.out.println("0. Вихід (збереження даних)");
            System.out.print("Ваш вибір: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Введіть номер сторінки (наприклад, 1): ");
                    int page = scanner.nextInt();
                    service.showCatalogPage(page, 2);
                    break;
                case 2:
                    System.out.print("Введіть ID товару для додавання в кошик: ");
                    String pId = scanner.nextLine();
                    Product p = service.getProductById(pId);
                    if (p != null) {
                        System.out.print("Введіть кількість: ");
                        int qty = scanner.nextInt();
                        cart.addProduct(p, qty);
                        System.out.println("Товар додано до кошика!");
                    } else {
                        System.out.println("Товар не знайдено.");
                    }
                    break;
                case 3:
                    cart.generateInvoice();
                    break;
                case 4:
                    System.out.print("Введіть ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Введіть назву: ");
                    String name = scanner.nextLine();
                    System.out.print("Введіть ціну: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.println("Введіть категорію: ");
                    String cat = scanner.nextLine();

                    service.addProduct(new Product(id, name, price, cat));
                    System.out.println("Товар успішно додано до каталогу!");
                    break;
                case 5:
                    System.out.print("Введіть ID товару для видалення: ");
                    String remId = scanner.nextLine();
                    service.removeProduct(remId);
                    System.out.println("Товар видалено.");
                    break;
                case 0:
                    running = false;
                    System.out.println("Роботу завершено. Дані збережено успішно!");
                    break;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
        scanner.close();
    }
}