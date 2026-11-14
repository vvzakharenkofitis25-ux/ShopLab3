package service;

import model.Electronics;
import model.Product;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringProcessor {


    private static final Pattern ID_PATTERN = Pattern.compile("^[A-Za-z0-9]{1,5}$");

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private static final Pattern PRICE_PATTERN = Pattern.compile("^\\d+(\\.\\d{1,2})?$");

    public Product parseProductFromCsv(String csvLine) {
        String cleanedLine = csvLine.trim();

        String[] parts = cleanedLine.split(",");
        if (parts.length < 4) {
            throw new IllegalArgumentException("Невірний формат CSV рядка.");
        }

        String id = parts[0].trim();
        String name = parts[1].trim().replace("_", " ");

        String categoryRaw = parts[2].trim();
        String category = categoryRaw.substring(0, 1).toUpperCase() + categoryRaw.substring(1).toLowerCase();

        double price = Double.parseDouble(parts[3].trim());

        return new Electronics(id, name, price, 12);
    }

    public boolean validateId(String id) {
        Matcher matcher = ID_PATTERN.matcher(id);
        return matcher.matches();
    }

    public boolean validateEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public boolean validatePriceFormat(String priceStr) {
        return PRICE_PATTERN.matcher(priceStr).matches();
    }
}
