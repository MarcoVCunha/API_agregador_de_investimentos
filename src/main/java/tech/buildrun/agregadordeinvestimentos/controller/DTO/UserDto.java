package tech.buildrun.agregadordeinvestimentos.controller.DTO;

import tech.buildrun.agregadordeinvestimentos.entity.User;

import java.time.Instant;
import java.util.UUID;

public class UserDto {
    private UUID userId;
    private String username;
    private String email;
    private Instant creationTimestamp;
    private Instant updateTimestamp;

    public UserDto(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.creationTimestamp = user.getCreationTimestamp();
        this.updateTimestamp = user.getUpdateTimestamp();
    }

    // Getters
    public UUID getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public Instant getCreationTimestamp() { return creationTimestamp; }
    public Instant getUpdateTimestamp() { return updateTimestamp; }
}
