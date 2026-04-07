package my.webstore.service;

import lombok.RequiredArgsConstructor;
import my.webstore.model.ProductReview;
import my.webstore.repo.ProductReviewRepo;
import my.webstore.http.request.review.ProductReviewRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductReviewService {

    private final ProductReviewRepo repo;
    private final UserService userService;
    private final ProductService productService;

    public List<ProductReview> getProductReviewsById(Integer prodId) {
        return repo.findProductReviewByProductId(prodId);
    }

    public void addProductReview(String email, ProductReviewRequest request) {
        int userId = userService.getUserId(email);
        if(isReviewAlreadyAdded(request.productId(), email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You've already reviewed this product");
        }
        if(!verifyRating(request.rating())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rating must be 1 to 5");
        }

        ProductReview review = ProductReview.builder()
                .userId(userId)
                .productId(request.productId())
                .rating(request.rating())
                .description(request.description())
                .build();
        repo.save(review);
    }

    public void deleteProductReview(String email, int prodId) {
        ProductReview review = getReviewOrThrow(email, prodId);
        repo.delete(review);
    }

    public void updateReviewRating(String email, int prodId, int rating) {
        if(!verifyRating(rating)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Review must be 1 to 5");
        }
        ProductReview review = getReviewOrThrow(email, prodId);
        review.setRating(rating);
        repo.save(review);
    }

    public void updateReviewDescription(String email, int prodId, String description) {
        ProductReview review = getReviewOrThrow(email, prodId);
        review.setDescription(description);
        repo.save(review);
    }

    private boolean verifyRating(int rating){
        return rating >= 1 && rating <= 5;
    }

    private boolean isReviewAlreadyAdded(int prodId, String email) {
        return repo.existsProductReviewsByProductIdAndUserId(prodId, userService.getUserId(email));
    }

    private ProductReview getReviewOrThrow(String email, int prodId) {
        return repo.findProductReviewByProductIdAndUserId(prodId, userService.getUserId(email))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Review doesn't exist"));
    }
}
