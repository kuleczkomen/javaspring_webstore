package my.webstore.repo;

import my.webstore.model.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductReviewRepo extends JpaRepository<ProductReview, Integer> {

    List<ProductReview> findProductReviewByProductId(Integer prodId);

    boolean existsProductReviewsByProductIdAndUserId(int prodId, int userId);

    Optional<ProductReview> findProductReviewByProductIdAndUserId(int prodId, int userId);
}
