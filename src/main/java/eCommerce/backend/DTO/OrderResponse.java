package eCommerce.backend.DTO;

public class OrderResponse {
    private int orderId;
    private int userId;
    private String userName;
    private int productId;
    private String productName;
    private String address;

    public OrderResponse(int orderId, int userId, String userName, int productId, String productName, String address) {
        this.orderId = orderId;
        this.userId = userId;
        this.userName = userName;
        this.productId = productId;
        this.productName = productName;
        this.address =address;
    }

    // Getters
    public int getOrderId() {
        return orderId;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
