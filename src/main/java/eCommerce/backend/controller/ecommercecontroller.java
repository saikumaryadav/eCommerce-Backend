package eCommerce.backend.controller;

import eCommerce.backend.DTO.OrderRequest;
import eCommerce.backend.DTO.OrderResponse;
import eCommerce.backend.DTO.ProductRequest;
import eCommerce.backend.DTO.ProductResponse;
import eCommerce.backend.entities.Category;
import eCommerce.backend.entities.Order;
import eCommerce.backend.entities.Product;
import eCommerce.backend.entities.user;
import eCommerce.backend.repository.OrderRepository;
import eCommerce.backend.repository.ProductRepository;
import eCommerce.backend.repository.categoryRepository;
import eCommerce.backend.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    // User Registration
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody user newUser) {
        try {
            user savedUser = userRepository.save(newUser);

            if (savedUser.getId() > 0) {
                return ResponseEntity.ok("User registered successfully!");
            } else {
                return ResponseEntity.status(500).body("Registration failed: Could not save user.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Something went wrong during registration.");
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
    public ResponseEntity<?> loginUser(@RequestBody user loginRequest) {
        user existingUser = userRepository.findByEmail(loginRequest.getEmail());

        if (existingUser == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        if (!existingUser.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(401).body("Invalid password");
        }

        return ResponseEntity.ok("Login successful");
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

            // Create and save order
            Order order = new Order();
            order.setUser(userEntity);
            order.setProduct(product);
            orderRepository.save(order);

            return ResponseEntity.ok("Order placed successfully.");
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

}
