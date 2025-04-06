package eCommerce.backend.repository;

import eCommerce.backend.entities.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepository  extends JpaRepository<user, Integer> {
    user findByEmail(String email);


}
