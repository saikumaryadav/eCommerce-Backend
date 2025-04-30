package eCommerce.backend.repository;

import eCommerce.backend.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import eCommerce.backend.entities.user;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserOrderByIdDesc(user user);
}
