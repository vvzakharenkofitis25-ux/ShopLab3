package service;

import model.Electronics;
import model.Product;
import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileManager {
    private static final String DATA_DIR = "data_storage";
    private static final String TEXT_FILE = DATA_DIR + "/products_text.txt";
    private static final String BINARY_FILE = DATA_DIR + "/products_binary.dat";
    private static final String LOG_FILE = DATA_DIR + "/app_log.txt";
    private static final String ZIP_BACKUP = DATA_DIR + "/backup.zip";

    private int logCounter = 0;
    private static final int LOG_ROTATION_LIMIT = 5; // ротація після 5 записів

    public void initializeStorage() {
        try {
            Path dirPath = Paths.get(DATA_DIR);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
                System.out.println("Створено директорію для сховища: " + DATA_DIR);
            } else {
                System.out.println("Директорія сховища вже існує.");
            }
        } catch (IOException e) {
            System.out.println("Помилка ініціалізації сховища: " + e.getMessage());
        }
    }

    public void saveProductsToText(List<Product> products) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(TEXT_FILE), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Product p : products) {
                writer.write(p.getId() + ";" + p.getName() + ";" + p.getCategory() + ";" + p.getPrice());
                writer.newLine();
            }
            System.out.println("Дані успішно збережено у текстовий файл: " + TEXT_FILE);
            logAction("Збережено товари у текстовий формат.");
        } catch (IOException e) {
            System.out.println("Помилка збереження у текст: " + e.getMessage());
        }
    }

    public List<Product> loadProductsFromText() {
        List<Product> products = new ArrayList<>();
        Path path = Paths.get(TEXT_FILE);
        if (!Files.exists(path)) return products;

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 4) {
                    String id = parts[0];
                    String name = parts[1];
                    String category = parts[2];
                    double price = Double.parseDouble(parts[3]);
                    products.add(new Electronics(id, name, price, 12));
                }
            }
            System.out.println("Дані завантажено з текстового файлу.");
            logAction("Завантажено товари з текстового формату.");
        } catch (IOException e) {
            System.out.println("Помилка читання тексту: " + e.getMessage());
        }
        return products;
    }

    public void saveProductsToBinary(List<Product> products) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(BINARY_FILE))) {
            dos.writeInt(products.size());
            for (Product p : products) {
                dos.writeUTF(p.getId());
                dos.writeUTF(p.getName());
                dos.writeUTF(p.getCategory());
                dos.writeDouble(p.getPrice());
            }
            System.out.println("Дані успішно збережено у бінарний файл: " + BINARY_FILE);
            logAction("Збережено товари у бінарний формат.");
        } catch (IOException e) {
            System.out.println("Помилка бінарного збереження: " + e.getMessage());
        }
    }

    public List<Product> loadProductsFromBinary() {
        List<Product> products = new ArrayList<>();
        File file = new File(BINARY_FILE);
        if (!file.exists()) return products;

        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                String id = dis.readUTF();
                String name = dis.readUTF();
                String category = dis.readUTF();
                double price = dis.readDouble();
                products.add(new Electronics(id, name, price, 12));
            }
            System.out.println("Дані завантажено з бінарного файлу.");
            logAction("Завантажено товари з бінарного формату.");
        } catch (IOException e) {
            System.out.println("Помилка читання бінарного файлу: " + e.getMessage());
        }
        return products;
    }

    public void printFileMetadata(String filePathStr) {
        Path path = Paths.get(filePathStr);
        try {
            if (Files.exists(path)) {
                System.out.println("Файл існує: " + path.toAbsolutePath());
                System.out.println("Розмір файлу: " + Files.size(path) + " байт");
            } else {
                System.out.println("Файл не знайдено: " + filePathStr);
            }
        } catch (IOException e) {
            System.out.println("Помилка отримання метаданих: " + e.getMessage());
        }
    }

    public void createZipBackup() {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(ZIP_BACKUP))) {
            Path filePath = Paths.get(TEXT_FILE);
            if (Files.exists(filePath)) {
                ZipEntry zipEntry = new ZipEntry(filePath.getFileName().toString());
                zos.putNextEntry(zipEntry);
                Files.copy(filePath, zos);
                zos.closeEntry();
                System.out.println("Створено резервну копію у ZIP: " + ZIP_BACKUP);
                logAction("Створено ZIP резервну копію.");
            }
        } catch (IOException e) {
            System.out.println("Помилка створення архіву: " + e.getMessage());
        }
    }

    public void logAction(String action) {
        logCounter++;
        if (logCounter > LOG_ROTATION_LIMIT) {
            rotateLogs();
            logCounter = 1;
        }

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(LOG_FILE), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            writer.write("[" + timeStamp + "] " + action);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Помилка логування: " + e.getMessage());
        }
    }

    private void rotateLogs() {
        try {
            Path logPath = Paths.get(LOG_FILE);
            if (Files.exists(logPath)) {
                Path archivedLog = Paths.get(DATA_DIR + "/app_log_old.txt");
                Files.move(logPath, archivedLog, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("[РОТАЦІЯ ЛОГІВ]: Старий лог переміщено в app_log_old.txt");
            }
        } catch (IOException e) {
            System.out.println("Помилка ротації логів: " + e.getMessage());
        }
    }
}
