package pattern;

public class StoreCustomer implements Observer {
    private String name;

    public StoreCustomer(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println("[Сповіщення для покупця " + name + "]: " + message);
    }
}
