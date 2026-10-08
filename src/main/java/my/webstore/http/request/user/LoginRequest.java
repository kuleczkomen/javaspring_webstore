package my.webstore.http.request.user;

public record LoginRequest(
        String email,
        String password) {
}
