package my.webstore.http.request.admin;

public record AdminLoginRequest(
        String email,
        String password,
        String adminKey) {
}
