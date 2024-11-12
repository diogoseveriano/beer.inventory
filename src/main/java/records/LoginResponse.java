package records;

import lombok.Builder;

@Builder
public record LoginResponse(String token, String username, String email, String role) {
}
