package my.webstore.request;

public record PasswordRequest(String oldPassword, String newPassword, String repeatPassword) {
}
