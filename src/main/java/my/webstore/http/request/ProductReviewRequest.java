package my.webstore.http.request;

public record ProductReviewRequest(Integer productId, Integer rating, String description) {
}
