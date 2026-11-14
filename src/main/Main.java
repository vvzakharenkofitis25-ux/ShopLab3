package main;

import model.*;
import service.ShopService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ShopService service = new ShopService();
        Cart cart = new Cart();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Вітаємо в інтернет-магазині (Лабораторна робота №4)!");

        while (running) {
            System.out.println("\nОберіть дію:");
            System.out.println("1. Переглянути каталог (поліморфізм)");
            System.out.println("2. Додати товар у кошик");
            System.out.println("3. Переглянути кошик та рахунок (з enum статусом)");
            System.out.println("4. Протестувати клонування кошика (Deep vs Shallow copy)");
            System.out.println("5. Використати SearchCriteria Record для пошуку");
            System.out.println("6. Перевірити специфіку товару (instanceof pattern matching)");
            System.out.println("0. Вихід");
            System.out.print("Ваш вибір: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Введіть сторінку (наприклад, 1): ");
                    int page = scanner.nextInt();
                    service.showCatalogPage(page, 2);
                    break;
                case 2:
                    System.out.println("Введіть ID товару: ");
                    String pId = scanner.nextLine();
                    Product p = service.getProductById(pId);
                    if (p != null) {
                        System.out.println("Введіть кількість: ");
                        int qty = scanner.nextInt();
                        cart.addProduct(p, qty);
                        cart.setStatus(OrderStatus.PROCESSING);
                        System.out.println("Товар додано! Статус змінено на: " + OrderStatus.PROCESSING.getUkrainianName());
                    } else {
                        System.out.println("Товар не знайдено.");
                    }
                    break;
                case 3:
                    cart.generateInvoice();
                    break;
                case 4:

                    Cart clonedCart = cart.clone();
                    System.out.println("Кошик успішно клоновано (deep copy створено)!");
                    System.out.println("Оригінал сума: " + cart.getTotalAmount() + " | Клон сума: " + clonedCart.getTotalAmount());
                    break;
                case 5:

                    System.out.println("Введіть ключове слово для пошуку: ");
                    String kw = scanner.nextLine();
                    System.out.println("Введіть максимальну ціну: ");
                    double maxPr = scanner.nextDouble();
                    SearchCriteria criteria = new SearchCriteria(kw, maxPr);
                    System.out.println("Створено пошуковий Record: " + criteria);
                    break;
                case 6:
                    System.out.println("Введіть ID товару для перевірки специфіки: ");
                    String specId = scanner.nextLine();
                    Product sp = service.getProductById(specId);
                    if (sp != null) {
                        service.processProductSpecificFeatures(sp);
                    } else {
                        System.out.println("Товар не знайдено.");
                    }
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