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

        System.out.println("Вітаємо в інтернет-магазині (Лабораторна робота №6)!");

        while (running) {
            System.out.println("\nОберіть дію:");
            System.out.println("1. Переглянути каталог");
            System.out.println("2. Імпортувати товар з CSV рядка (String методи)");
            System.out.println("3. Перевірити валідацію через регулярні вирази (RegEx)");
            System.out.println("4. Сформувати звіт у файл та консоль (StringBuilder + PrintWriter)");
            System.out.println("5. Запустити бенчмарк (String '+' vs StringBuilder)");
            System.out.println("0. Вихід");
            System.out.print("Ваш вибір: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    service.showCatalogPage(1, 5);
                    break;
                case 2:
                    System.out.print("Введіть товар у форматі CSV (наприклад:  10, геймпад , електроніка , 1800.5 ): ");
                    String csv = scanner.nextLine();
                    try {
                        Product p = processor.parseProductFromCsv(csv);
                        service.addProduct(p);
                        System.out.println("Товар успішно імпортовано та додано до каталогу: " + p);
                    } catch (Exception e) {
                        System.out.println("Помилка розбору CSV: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("\n--- ТЕСТУВАННЯ РЕГУЛЯРНИХ ВИРАЗІВ ---");
                    System.out.print("Введіть ID для перевірки (напр. A12): ");
                    String testId = scanner.nextLine();
                    System.out.println("ID валідний? -> " + processor.validateId(testId));

                    System.out.print("Введіть Email для перевірки (напр. user@gmail.com): ");
                    String testEmail = scanner.nextLine();
                    System.out.println("Email валідний? -> " + processor.validateEmail(testEmail));
                    break;
                case 4:
                    service.generateFormattedReportToFile("shop_report.txt");
                    break;
                case 5:
                    service.runStringBenchmark();
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