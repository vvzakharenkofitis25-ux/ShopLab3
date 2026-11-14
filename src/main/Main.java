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

        System.out.println("Вітаємо в інтернет-магазині (Лабораторна робота №10)!");

        while (running) {
            System.out.println("\nОберіть дію:");
            System.out.println("1. Переглянути каталог");
            System.out.println("2. Імпортувати новий товар з CSV (з викликом Observer)");
            System.out.println("3. Протестувати поведінкові патерни (Strategy & Observer) [ЛР10]");
            System.out.println("0. Вихід");
            System.out.print("Ваш вибір: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    service.showCatalogPage(1, 5);
                    break;
                case 2:
                    System.out.print("Введіть товар у форматі CSV (напр: 10,планшет,електроніка,12000): ");
                    String csv = scanner.nextLine();
                    try {
                        Product p = processor.parseProductFromCsv(csv);
                        service.addProduct(p); // Тут спрацює Спостерігач
                        System.out.println("Товар успішно додано!");
                    } catch (Exception e) {
                        System.out.println("Помилка: " + e.getMessage());
                    }
                    break;
                case 3:
                    service.demoBehavioralPatterns();
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