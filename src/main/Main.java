package main;

import exception.InvalidPriceException;
import exception.ProductNotFoundException;
import exception.ShopException;
import model.*;
import service.ShopService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ShopService service = new ShopService();
        Cart cart = new Cart();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Вітаємо в інтернет-магазині (Лабораторна робота №5)!");

        while (running) {
            System.out.println("\nОберіть дію:");
            System.out.println("1. Переглянути каталог");
            System.out.println("2. Додати товар у кошик (з перевіркою Optional та винятків)");
            System.out.println("3. Переглянути кошик та рахунок");
            System.out.println("4. Протестувати додавання товару з некоректною ціною (Unchecked Exception)");
            System.out.println("5. Протестувати пошук неіснуючого товару (Checked Exception)");
            System.out.println("6. Викликати узагальнений метод з wildcard (? extends Product)");
            System.out.println("0. Вихід");
            System.out.print("Ваш вибір: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Введіть сторінку: ");
                    int page = scanner.nextInt();
                    service.showCatalogPage(page, 2);
                    break;
                case 2:
                    System.out.print("Введіть ID товару для кошика: ");
                    String pId = scanner.nextLine();

                    try {
                        Product p = service.getProductById(pId);
                        System.out.print("Введіть кількість: ");
                        int qty = scanner.nextInt();
                        cart.addProduct(p, qty);
                        System.out.println("Товар успішно додано до кошика!");
                    } catch (ProductNotFoundException e) {
                        System.out.println("[ПОМИЛКА УПРАВЛІННЯ]: " + e.getMessage());
                    }
                    break;
                case 3:
                    cart.generateInvoice();
                    break;
                case 4:
                    try {
                        System.out.print("Введіть ID нового товару: ");
                        String nId = scanner.nextLine();
                        System.out.print("Введіть назву: ");
                        String nName = scanner.nextLine();
                        System.out.print("Введіть ціну (спробуйте ввести від'ємну): ");
                        double nPrice = scanner.nextDouble();
                        scanner.nextLine();

                        Electronics badProduct = new Electronics(nId, nName, nPrice, 12);
                        service.addProduct(badProduct);
                        System.out.println("Товар успішно додано!");
                    } catch (InvalidPriceException e) {
                        System.out.println("[КЕРУВАННЯ ВИНЯТКОМ UNCHECKED]: " + e.getMessage());
                    } catch (ShopException e) {
                        System.out.println("[ПОМИЛКА]: " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.print("Введіть неіснуючий ID (наприклад, 999): ");
                    String fakeId = scanner.nextLine();
                    try {
                        service.getProductById(fakeId);
                    } catch (ProductNotFoundException e) {
                        System.out.println("Спіймано очікуваний checked виняток: " + e.getMessage());
                    }
                    break;
                case 6:
                    service.printTotalCatalogPrice(service.getAllProducts());
                    break;
                case 0:
                    running = false;
                    System.out.println("Роботу завершено!");
                    break;
                default:
                    System.out.println("Невірний вибір.");
            }
        }
        scanner.close();
    }
}