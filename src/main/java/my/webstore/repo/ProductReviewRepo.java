package my.webstore.repo;

import my.webstore.model.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductReviewRepo extends JpaRepository<ProductReview, Integer> {

    List<ProductReview> findProductReviewByProductId(Integer prodId);
}
