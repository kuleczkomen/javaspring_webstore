package my.webstore.http.request;

public record PasswordRequest(String oldPassword, String newPassword, String repeatPassword) {
}
