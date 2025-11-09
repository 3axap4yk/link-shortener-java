import model.User;
import service.LinkService;
import service.StorageService;

import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

// Главный класс — консольное приложение
public class App {
    public static void main(String[] args) {
        StorageService storage = new StorageService();
        Map<UUID, User> data = storage.load();

        Scanner sc = new Scanner(System.in);
        User user = new User();
        data.put(user.getId(), user);

        LinkService linkService = new LinkService();

        while (true) {
            System.out.println("\nТекущий пользователь: " + user.getId());
            System.out.println("1. Создать ссылку");
            System.out.println("2. Перейти по короткой ссылке");
            System.out.println("3. Показать мои ссылки");
            System.out.println("4. Изменить лимит ссылки");
            System.out.println("5. Удалить ссылку");
            System.out.println("6. Сменить пользователя (ввести UUID)");
            System.out.println("0. Выход");
            System.out.print("→ ");

            // Защита — если введено не число
            if (!sc.hasNextInt()) {
                System.out.println("Введите номер команды.");
                sc.nextLine();
                continue;
            }

            int cmd = sc.nextInt();
            sc.nextLine();

            switch (cmd) {
                case 1 -> {
                    System.out.print("Введите URL: ");
                    String url = sc.nextLine();
                    System.out.print("Введите лимит: ");

                    if (!sc.hasNextInt()) { System.out.println("Лимит должен быть числом."); sc.nextLine(); continue; }
                    int limit = sc.nextInt(); sc.nextLine();

                    linkService.createLink(user, url, limit);
                }
                case 2 -> {
                    System.out.print("Код: ");
                    linkService.openLink(user, sc.nextLine());
                }
                case 3 -> linkService.listLinks(user);

                case 4 -> {
                    System.out.print("Код: ");
                    String code = sc.nextLine();
                    System.out.print("Новый лимит: ");

                    if (!sc.hasNextInt()) { System.out.println("Лимит должен быть числом."); sc.nextLine(); continue; }
                    int newLimit = sc.nextInt(); sc.nextLine();

                    linkService.changeLimit(user, code, newLimit);
                }

                case 5 -> {
                    System.out.print("Код: ");
                    linkService.deleteLink(user, sc.nextLine());
                }

                case 6 -> {
                    System.out.print("Введите UUID: ");
                    String input = sc.nextLine();
                    try {
                        UUID id = UUID.fromString(input);
                        user = data.getOrDefault(id, new User(id));
                        data.putIfAbsent(id, user);
                        System.out.println("Пользователь изменён. Кол-во ссылок пользователя: " + user.getLinks().size());
                    } catch (Exception e) {
                        System.out.println("Некорректный формат UUID.");
                    }
                }

                case 0 -> {
                    storage.save(data);
                    System.out.println("Данные сохранены. Выход.");
                    return;
                }

                default -> System.out.println("Неизвестная команда.");
            }
        }
    }
}