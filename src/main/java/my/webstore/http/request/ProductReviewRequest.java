package my.webstore.http.request;

public record ProductReviewRequest(int productId, int rating, String description) {
}
