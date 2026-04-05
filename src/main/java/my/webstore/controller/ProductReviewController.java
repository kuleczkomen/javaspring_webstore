package my.webstore.controller;

import lombok.AllArgsConstructor;
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
}
