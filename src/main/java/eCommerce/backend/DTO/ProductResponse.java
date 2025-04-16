package eCommerce.backend.DTO;

public class ProductResponse {
    private int id;
    private String name;
    private int categoryId;
    private String categoryName;

    private int stock;

    private double amount;

    private String description;

    public ProductResponse(int id, String name, int categoryId, String categoryName, int stock, double amount, String description) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.stock = stock;
        this.amount = amount;
        this.description = description;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
