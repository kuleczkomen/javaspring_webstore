package my.webstore.request;

public record ProductReviewRequest(Integer productId, Integer rating, String description) {
}
