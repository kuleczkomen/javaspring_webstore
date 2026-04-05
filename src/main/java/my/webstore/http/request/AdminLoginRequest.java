package my.webstore.http.request;

public record AdminLoginRequest(
        String email,
        String password,
        String adminKey) {
}
