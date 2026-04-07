package my.webstore.http.request.user;

public record PasswordRequest(String oldPassword, String newPassword, String repeatPassword) {
}
