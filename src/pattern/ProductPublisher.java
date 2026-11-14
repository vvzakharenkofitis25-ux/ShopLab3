package pattern;

import java.util.ArrayList;
import java.util.List;

public class ProductPublisher {
    private List<Observer> observers = new ArrayList<>();

    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String productName) {
        for (Observer observer : observers) {
            observer.update("У нашому каталозі з'явився новий товар: " + productName + "!");
        }
    }
}