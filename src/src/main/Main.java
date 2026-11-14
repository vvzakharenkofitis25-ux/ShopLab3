package main;

import exception.InvalidPriceException;
import exception.ProductNotFoundException;
import exception.ShopException;
import model.*;
import service.ShopService;
import service.StringProcessor;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ShopService service = new ShopService();
        StringProcessor processor = new StringProcessor();
        Cart cart = new Cart();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Вітаємо в інтернет-магазині (Лабораторна робота №7)!");

        while (running) {
            System.out.println("\nОберіть дію:");
            System.out.println("1. Переглянути каталог");
            System.out.println("2. Імпортувати новий товар з CSV");
            System.out.println("3. Згенерувати звіт у файл");
            System.out.println("4. Протестувати файлові потоки, NIO.2 та ZIP архів (ЛР7)");
            System.out.println("0. Вихід");
            System.out.print("Ваш вибір: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    service.showCatalogPage(1, 5);
                    break;
                case 2:
                    System.out.print("Введіть товар у форматі CSV (напр: 10,мишка,електроніка,450): ");
                    String csv = scanner.nextLine();
                    try {
                        Product p = processor.parseProductFromCsv(csv);
                        service.addProduct(p);
                        System.out.println("Товар успішно додано і збережено у файли!");
                    } catch (ShopException | IllegalArgumentException e) {
                        System.out.println("Помилка: " + e.getMessage());
                    }
                    break;
                case 3:
                    service.generateFormattedReportToFile("shop_report.txt");
                    break;
                case 4:
                    service.performFileOperationsDemo();
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