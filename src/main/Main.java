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

        System.out.println("Вітаємо в інтернет-магазині (Лабораторна робота №8)!");

        while (running) {
            System.out.println("\nОберіть дію:");
            System.out.println("1. Переглянути каталог");
            System.out.println("2. Сортування товарів (Comparable / Comparator)");
            System.out.println("3. Перевірити власний ітератор");
            System.out.println("4. Протестувати TreeSet / TreeMap (діапазони цін)");
            System.out.println("5. Протестувати власне Бінарне дерево пошуку (BST)");
            System.out.println("6. Запустити бенчмарк (ArrayList vs LinkedList)");
            System.out.println("0. Вихід");
            System.out.print("Ваш вибір: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    service.showCatalogPage(1, 5);
                    break;
                case 2:
                    System.out.println("Оберіть тип сортування: 1 - за ціною (Comparable), 2 - за назвою (Comparator), 3 - за категорією");
                    int sortType = scanner.nextInt();
                    service.sortAndDisplayProducts(sortType);
                    break;
                case 3:
                    service.demoCustomIterator();
                    break;
                case 4:
                    service.demoTreeSetAndMap();
                    break;
                case 5:
                    service.demoBinarySearchTree();
                    break;
                case 6:
                    service.runListBenchmark();
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