package service;

import model.Link;
import model.User;

import java.net.URI;
import java.awt.Desktop;
import java.util.UUID;

// Сервис управления ссылками (создание / переход / список / изменение / удаление)
public class LinkService {

    private static final long HOUR = 60 * 60 * 1000; // TTL: 1 час

    // Проверка корректности URL
    private boolean isValidUrl(String url) {
        return url.matches("https?://.+");
    }

    // Создание ссылки
    public void createLink(User user, String url, int limit) {
        if (!isValidUrl(url)) {
            System.out.println("Некорректная ссылка. Пример: https://site.com");
            return;
        }

        String shortCode = UUID.randomUUID().toString().substring(0, 6);
        Link link = new Link(url, shortCode, limit, HOUR);
        user.getLinks().add(link);
        System.out.println("Короткая ссылка: " + shortCode);
    }

    // Переход по ссылке
    public void openLink(User user, String code) {
        for (Link link : user.getLinks()) {
            if (link.getShortCode().equals(code)) {

                if (link.isExpired()) {
                    System.out.println("Срок жизни ссылки истёк.");
                    return;
                }
                if (link.getClickLimit() <= 0) {
                    System.out.println("Лимит переходов исчерпан.");
                    return;
                }

                link.decreaseLimit();
                try { Desktop.getDesktop().browse(new URI(link.getOriginalUrl())); } catch (Exception ignored) {}
                System.out.println("Открываю: " + link.getOriginalUrl());
                return;
            }
        }
        System.out.println("Ссылка не найдена.");
    }

    // Вывод списка ссылок
    public void listLinks(User user) {
        System.out.println("Всего ссылок: " + user.getLinks().size());
        for (Link l : user.getLinks()) {
            System.out.println(l.getShortCode() + " → " + l.getOriginalUrl() + " | лимит: " + l.getClickLimit());
        }
    }

    // Изменение лимита
    public void changeLimit(User user, String code, int newLimit) {
        for (Link link : user.getLinks()) {
            if (link.getShortCode().equals(code)) {
                link.setClickLimit(newLimit);
                System.out.println("Лимит обновлён.");
                return;
            }
        }
        System.out.println("Ссылка не найдена.");
    }

    // Удаление ссылки
    public void deleteLink(User user, String code) {
        boolean removed = user.getLinks().removeIf(l -> l.getShortCode().equals(code));
        System.out.println(removed ? "Ссылка удалена." : "Ссылка не найдена.");
    }
}
