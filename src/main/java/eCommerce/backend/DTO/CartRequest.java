package eCommerce.backend.DTO;

public class CartRequest {
    private int userId;
    private int categoryId;
    private int productId;
    private double amount;

    // Constructors
    public CartRequest() {
    }

    public CartRequest(int userId, int categoryId, int productId, double amount) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.productId = productId;
        this.amount = amount;
    }

    // Getters and Setters

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
