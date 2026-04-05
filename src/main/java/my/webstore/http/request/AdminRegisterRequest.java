package my.webstore.http.request;

public record AdminRegisterRequest(
        String firstName,
        String lastName,
        String email,
        String password,
        String adminKey
) {
}
