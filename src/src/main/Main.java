package main;

import model.*;
import service.ShopService;
import service.StringProcessor;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ShopService service = new ShopService();
        StringProcessor processor = new StringProcessor();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Вітаємо в інтернет-магазині (Лабораторна робота №9)!");

        while (running) {
            System.out.println("\nОберіть дію:");
            System.out.println("1. Переглянути каталог");
            System.out.println("2. Імпортувати новий товар з CSV");
            System.out.println("3. Протестувати шаблони проєктування GoF (Singleton & Abstract Factory) [ЛР9]");
            System.out.println("0. Вихід");
            System.out.print("Ваш вибір: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    service.showCatalogPage(1, 5);
                    break;
                case 2:
                    System.out.print("Введіть товар у форматі CSV (напр: 10,клавіатура,електроніка,1500): ");
                    String csv = scanner.nextLine();
                    try {
                        Product p = processor.parseProductFromCsv(csv);
                        service.addProduct(p);
                        System.out.println("Товар успішно додано!");
                    } catch (Exception e) {
                        System.out.println("Помилка: " + e.getMessage());
                    }
                    break;
                case 3:
                    service.demoDesignPatterns();
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