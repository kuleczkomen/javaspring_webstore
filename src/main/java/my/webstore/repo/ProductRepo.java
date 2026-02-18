package my.webstore.repo;

import my.webstore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

        // JPQL -> JPA Query Language

    @Query("SELECT p from Product p where "+
    "lower(p.title) like lower(concat('%', :keyword, '%')) OR " +
    "lower(p.description) like(concat('%', :keyword, '%'))")
    List<Product> searchProducts(String keyword);

}
