package my.webstore.request;

public record AdminRegisterRequest(
        String firstName,
        String lastName,
        String email,
        String password) {
}
