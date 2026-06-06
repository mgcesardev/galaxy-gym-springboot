package cmg.example.galaxy_gym_backend.DTO;

import cmg.example.galaxy_gym_backend.models.User;

public record AuthResponse(
    String token,
    User user
) {}
