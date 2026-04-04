package my.webstore.service;

import lombok.RequiredArgsConstructor;
import my.webstore.model.Product;
import my.webstore.model.ProductReview;
import my.webstore.repo.ProductReviewRepo;
import my.webstore.request.ProductReviewRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
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
        // check if product exists
        Product product = productService.getProductById(request.productId());
        if(product.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product doesn't exist");
        }

        Integer userId = userService.getUserId(email);

        ProductReview review = ProductReview.builder()
                .userId(userId)
                .productId(request.productId())
                .rating(request.rating())
                .description(request.description())
                .build();
        repo.save(review);
    }
}
