package my.webstore.http.request.user;

public record RegisterRequest(
        String firstName,
        String lastName,
        String email,
        String password) {
}
