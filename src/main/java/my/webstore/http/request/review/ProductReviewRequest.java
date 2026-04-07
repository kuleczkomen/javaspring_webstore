package my.webstore.http.request.review;

public record ProductReviewRequest(int productId, int rating, String description) {
}
