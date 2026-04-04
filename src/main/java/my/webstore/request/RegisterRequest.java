package my.webstore.request;

public record RegisterRequest(
        String firstName,
        String lastName,
        String email,
        String password) {
}
