package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Класс пользователя хранит его UUID и список его ссылок
public class User {
    private UUID id;
    private List<Link> links = new ArrayList<>();

    public User() {
        this.id = UUID.randomUUID();
    }

    public User(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public List<Link> getLinks() {
        return links;
    }
}
