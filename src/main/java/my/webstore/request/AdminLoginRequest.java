package my.webstore.request;

public record AdminLoginRequest(
        String email,
        String password,
        String adminKey) {
}
