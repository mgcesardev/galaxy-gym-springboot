package cmg.example.galaxy_gym_backend.exceptions;

/**
 * General exception for API-related errors.
 */
public class ApiException extends RuntimeException {
    private final String useCase;

    public ApiException(String message) {
        super(message);
        this.useCase = "GENERIC_ERROR";
    }

    public ApiException(String message, String useCase) {
        super(message);
        this.useCase = useCase;
    }

    public String getUseCase() {
        return useCase;
    }
}
