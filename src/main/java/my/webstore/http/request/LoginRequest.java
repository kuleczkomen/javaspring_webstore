package my.webstore.http.request;

public record LoginRequest(
        String email,
        String password) {
}
