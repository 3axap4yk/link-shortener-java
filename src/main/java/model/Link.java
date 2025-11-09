package model;

// Класс ссылки — хранит исходный URL, короткий код, лимит, срок жизни и время создания
public class Link {
    private String originalUrl;
    private String shortCode;
    private int clickLimit;
    private long createdAt;
    private long ttl;

    public Link(String originalUrl, String shortCode, int clickLimit, long ttl) {
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
        this.clickLimit = clickLimit;
        this.ttl = ttl;
        this.createdAt = System.currentTimeMillis();
    }

    public String getOriginalUrl() { return originalUrl; }
    public String getShortCode() { return shortCode; }
    public int getClickLimit() { return clickLimit; }
    public void setClickLimit(int clickLimit) { this.clickLimit = clickLimit; }

    // Проверка протухания ссылки
    public boolean isExpired() {
        return System.currentTimeMillis() - createdAt > ttl;
    }

    public void decreaseLimit() {
        clickLimit--;
    }
}
