package service;

import model.Product;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TaskProcessor {
    private int sharedCounter = 0;

    public synchronized void incrementSync() {
        sharedCounter++;
    }

    public void incrementUnsync() {
        sharedCounter++;
    }

    public void runMultithreadingDemo(List<Product> products) {
        System.out.println("\n--- Багатопотокова обробка замовлень (Пункт 4 та 5) ---");

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) incrementSync();
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) incrementSync();
        });

        Thread t3 = new Thread(() -> {
            for (int i = 0; i < products.size(); i++) {
                System.out.println("Потік 3 обробляє товар: " + products.get(i).getName());
            }
        });

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.out.println("Потік перервано: " + e.getMessage());
        }

        System.out.println("Результат лічильника після синхронізації (очікувано 2000): " + sharedCounter);

        runProducerConsumerDemo();
    }

    public void runProducerConsumerDemo() {
        System.out.println("\n--- Сценарій Виробник-Споживач (BlockingQueue) ---");
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(5);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 3; i++) {
                    String order = "Замовлення #" + i;
                    queue.put(order);
                    System.out.println("Виробник додав: " + order);
                    Thread.sleep(300);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 3; i++) {
                    String order = queue.take();
                    System.out.println("Споживач обробив: " + order);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            System.out.println("Помилка join: " + e.getMessage());
        }
    }
}