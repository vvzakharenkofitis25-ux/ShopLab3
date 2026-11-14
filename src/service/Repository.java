package service;

import model.Identifiable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Repository<T extends Identifiable, ID> implements Serializable {
    private List<T> items = new ArrayList<>();

    public void add(T item) {
        items.add(item);
    }

    public void remove(T item) {
        items.remove(item);
    }

    public List<T> getAll() {
        return items;
    }


    public Optional<T> findById(ID id) {
        return items.stream()
                .filter(item -> item.getId().equals(id.toString()))
                .findFirst();
    }
}