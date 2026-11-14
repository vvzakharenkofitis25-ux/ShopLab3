package exception;

public class InvalidPriceException extends RuntimeException {
    private double invalidPrice;

    public InvalidPriceException(double invalidPrice) {
        super("Помилка: ціна не може бути від'ємною або нульовою (" + invalidPrice + ").");
        this.invalidPrice = invalidPrice;
    }

    public double getInvalidPrice() {
        return invalidPrice;
    }
}
