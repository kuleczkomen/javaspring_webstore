package my.webstore.response;

import my.webstore.model.Role;

// role included
public record UserResponseForAdmin(
        String firstName,
        String lastName,
        String email,
        Role role) {
}
