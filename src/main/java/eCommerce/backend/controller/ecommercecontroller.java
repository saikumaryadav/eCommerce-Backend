package eCommerce.backend.controller;

import eCommerce.backend.DTO.*;
import eCommerce.backend.entities.*;
import eCommerce.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class ecommercecontroller {

    @Autowired
    private userRepository userRepository;

    @Autowired
    private categoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    // User Registration
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody user newUser) {
        try {
            user existingUser = userRepository.findByEmail(newUser.getEmail());
            if (existingUser != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Email already exists");
                return ResponseEntity.status(400).body(response);
            }

            user savedUser = userRepository.save(newUser);

            if (savedUser.getId() > 0) {
                Map<String, Object> response = new HashMap<>();
                response.put("userId", savedUser.getId());
                response.put("username", savedUser.getName());
                response.put("message", "User registered successfully!");
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Registration failed: Could not save user.");
                return ResponseEntity.status(500).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Something went wrong during registration.");
            return ResponseEntity.status(500).body(response);
        }
    }

    // Find All users
    @GetMapping("/users")
    public ResponseEntity<List<user>> getAllUsers() {
        List<user> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    // Find user by Id
    @GetMapping("/user/{id}")
    public ResponseEntity<user> getUserById(@PathVariable int id) {
        Optional<user> userOptional = userRepository.findById(id);
        return userOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    // Login
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody user loginRequest) {
        user existingUser = userRepository.findByEmail(loginRequest.getEmail());

        if (existingUser == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User not found");
            return ResponseEntity.status(404).body(response);
        }

        if (!existingUser.getPassword().equals(loginRequest.getPassword())) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Invalid password");
            return ResponseEntity.status(401).body(response);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("userId", existingUser.getId());
        response.put("username", existingUser.getName());
        response.put("message", "Login successful");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/category")
    public ResponseEntity<?> categoryRegister(@RequestBody Category category){

        try {
            Category savedCategory = categoryRepository.save(category);

            if (savedCategory.getId() > 0) {
                return ResponseEntity.ok("Category  registered successfully!");
            } else {
                return ResponseEntity.status(500).body("Registration failed: Could not save Category.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Something went wrong during registration.");
        }
    }

    @GetMapping("/catwiseproducts/{id}")
    public ResponseEntity<?> productsByCategory(@PathVariable int id){
        List<Product> products = productRepository.findProductsByCategoryId(id);
        //List<Produ>
        for(Product product : products){

        }

        if (products.isEmpty()) {
            return ResponseEntity.status(404).body("No products found for category ID: " + id);
        } else {
            return ResponseEntity.ok(products);
        }
    }
    @PostMapping("/product")
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest request) {
        try {
            // Fetch category by ID
            System.out.println("Prd "+ request);
            Category category = categoryRepository.findCategoryByIdNative(request.getCategoryId());
            if (category == null) {
                return ResponseEntity.status(404).body("Category not found.");
            }

            if (category == null) {
                return ResponseEntity.status(404).body("Category not found.");
            }

            // Create and save new product
            Product product = new Product();
            product.setName(request.getName());
            product.setCategory(category);
            product.setAmount(request.getAmount());
            product.setStock(request.getStock());
            product.setDescription(request.getDescription());

            productRepository.save(product);
            return ResponseEntity.ok("Product added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error while saving product.");
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<Product> products = productRepository.findAll();

        List<ProductResponse> response = products.stream().map(product ->
                new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getCategory().getId(),
                        product.getCategory().getName(),
                        product.getStock(),
                        product.getAmount(),
                        product.getDescription()
                )
        ).toList();

        return ResponseEntity.ok(response);
    }


    @PostMapping("/order")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest orderRequest) {
        try {
            // Fetch user by ID
            user userEntity = userRepository.findById(orderRequest.getUserId()).orElse(null);
            if (userEntity == null) {
                return ResponseEntity.status(404).body("User not found.");
            }

            // Fetch product by ID
            Product product = productRepository.findById(orderRequest.getProductId()).orElse(null);
            if (product == null) {
                return ResponseEntity.status(404).body("Product not found.");
            }

            // Check if stock is available
            if (product.getStock() <= 0) {
                return ResponseEntity.status(400).body("Product is out of stock.");
            }

            // Decrease stock by 1
            product.setStock(product.getStock() - 1);
            productRepository.save(product);

            // Create and save order
            Order order = new Order();
            order.setUser(userEntity);
            order.setProduct(product);
            orderRepository.save(order);

            return ResponseEntity.ok("Order placed successfully and stock updated.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error placing order.");
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        List<OrderResponse> response = orders.stream().map(order ->
                new OrderResponse(
                        order.getId(),
                        order.getUser().getId(),
                        order.getUser().getName(),
                        order.getProduct().getId(),
                        order.getProduct().getName()
                )
        ).toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/cart")
    public ResponseEntity<?> addToCart(@RequestBody CartRequest cartRequestDTO) {
        // Fetch entities
        System.out.println("Received cart request: " + cartRequestDTO);
        user userEntity = userRepository.findById(cartRequestDTO.getUserId()).orElse(null);
        System.out.println("User: " + userEntity);
        Category categoryEntity = categoryRepository.findById(cartRequestDTO.getCategoryId()).orElse(null);
        System.out.println("Category: " + categoryEntity);
        Product productEntity = productRepository.findById(cartRequestDTO.getProductId()).orElse(null);
        System.out.println("Product: " + productEntity);

        if (userEntity == null || categoryEntity == null || productEntity == null) {
            return ResponseEntity.badRequest().body("Invalid user, category, or product ID");
        }

        // Create Cart entity
        Cart cart = new Cart();
        cart.setUser(userEntity);
        cart.setCategory(categoryEntity);
        cart.setProduct(productEntity);
        cart.setAmount(cartRequestDTO.getAmount());

        // Save to DB
        Cart savedCart = cartRepository.save(cart);

        // Prepare response DTO
        CartResponse response = new CartResponse(
                savedCart.getId(),
                userEntity.getId(),
                userEntity.getName(),
                productEntity.getId(),
                productEntity.getName(),
                categoryEntity.getId(),
                categoryEntity.getName(),
                savedCart.getAmount()
        );

        return ResponseEntity.ok("Item added to cart successfully");
    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<?> getCartByUserId(@PathVariable int userId) {
        user userEntity = userRepository.findById(userId).orElse(null);

        if (userEntity == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        List<Cart> cartList = cartRepository.findByUser(userEntity);

        if (cartList.isEmpty()) {
            return ResponseEntity.ok("Cart is empty for user ID: " + userId);
        }
        System.out.println("cart"+cartList);
        for (Cart cart : cartList) {System.out.println(cart);}
        List<CartResponse> responseList = cartList.stream().map(cart -> new CartResponse(
                cart.getId(),
                cart.getUser().getId(),
                cart.getUser().getName(),
                cart.getProduct().getId(),
                cart.getProduct().getName(),
                cart.getCategory().getId(),
                cart.getCategory().getName(),
                cart.getAmount()
        )).toList();

        return ResponseEntity.ok(responseList);
    }
    @DeleteMapping("/cart/{cartId}")
    public ResponseEntity<String> removeFromCart(@PathVariable int cartId) {
        Optional<Cart> cartOptional = cartRepository.findById(cartId);

        if (cartOptional.isEmpty()) {
            return ResponseEntity.status(404).body("Cart item not found.");
        }

        cartRepository.deleteById(cartId);
        return ResponseEntity.ok("Item removed from cart successfully.");
    }

    @GetMapping("/orders/{userId}")
    public ResponseEntity<?> getOrdersByUserId(@PathVariable int userId) {
        Optional<user> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        List<Order> userOrders = orderRepository.findByUserOrderByIdDesc(userOptional.get());

        if (userOrders.isEmpty()) {
            return ResponseEntity.ok("No orders found for user ID: " + userId);
        }

        List<OrderResponse> response = userOrders.stream().map(order ->
                new OrderResponse(
                        order.getId(),
                        order.getUser().getId(),
                        order.getUser().getName(),
                        order.getProduct().getId(),
                        order.getProduct().getName()
                )
        ).toList();

        return ResponseEntity.ok(response);
    }


}
