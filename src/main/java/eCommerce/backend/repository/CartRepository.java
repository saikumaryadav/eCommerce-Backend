package eCommerce.backend.repository;

import eCommerce.backend.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import eCommerce.backend.entities.*;

import java.util.List;

@Repository
public interface CartRepository  extends JpaRepository<Cart, Integer> {
    List<Cart> findByUser(user userEntity);
}
