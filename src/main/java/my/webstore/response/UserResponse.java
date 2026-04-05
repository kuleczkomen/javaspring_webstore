package my.webstore.response;

import lombok.Builder;

@Builder
public record UserResponse(
        String firstName,
        String lastName,
        String email) {
}
