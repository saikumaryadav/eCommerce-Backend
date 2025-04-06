package eCommerce.backend.controller;

import eCommerce.backend.entities.user;
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


}
