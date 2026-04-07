package my.webstore.controller;

import lombok.AllArgsConstructor;
import my.webstore.http.request.DescriptionRequest;
import my.webstore.http.request.RatingRequest;
import my.webstore.model.ProductReview;
import my.webstore.http.request.ProductReviewRequest;
import my.webstore.service.ProductReviewService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/prod-review")
@AllArgsConstructor
public class ProductReviewController {

    private final ProductReviewService service;

    @GetMapping("/{prodId}")
    public List<ProductReview> getProductReviewsById(@PathVariable int prodId) {
        return service.getProductReviewsById(prodId);
    }

    @PostMapping("/")
    public void addProductReview(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ProductReviewRequest request) {
        service.addProductReview(userDetails.getUsername(), request);
    }

    @DeleteMapping("/{prodId}")
    public void deleteProductReview(@AuthenticationPrincipal UserDetails userDetails, @PathVariable int prodId) {
        service.deleteProductReview(userDetails.getUsername(), prodId);
    }

    @PatchMapping("/{prodId}/rating")
    public void updateReviewRating(@AuthenticationPrincipal UserDetails userDetails, @PathVariable int prodId, @RequestBody RatingRequest request) {
        service.updateReviewRating(userDetails.getUsername(), prodId, request.rating());
    }

    @PatchMapping("/{prodId}/descr")
    public void updateReviewDescription(@AuthenticationPrincipal UserDetails userDetails, @PathVariable int prodId, @RequestBody DescriptionRequest request) {
        service.updateReviewDescription(userDetails.getUsername(), prodId, request.description());
    }
}
