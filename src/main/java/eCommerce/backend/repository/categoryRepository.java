package eCommerce.backend.repository;

import eCommerce.backend.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface categoryRepository extends JpaRepository<Category, Integer> {
    @Query(value = "SELECT * FROM category WHERE id = :id", nativeQuery = true)
    Category findCategoryByIdNative(@Param("id") int id);
}
