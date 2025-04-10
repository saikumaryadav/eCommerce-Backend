package eCommerce.backend.DTO;

public class ProductResponse {
    private int id;
    private String name;
    private int categoryId;
    private String categoryName;

    public ProductResponse(int id, String name, int categoryId, String categoryName) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
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
}
