package cmg.example.galaxy_gym_backend.auth;

import cmg.example.galaxy_gym_backend.DTO.AuthRequest;
import cmg.example.galaxy_gym_backend.DTO.GoogleAuthRequest;
import cmg.example.galaxy_gym_backend.exceptions.GlobalExceptionHandler;
import cmg.example.galaxy_gym_backend.models.Role;
import cmg.example.galaxy_gym_backend.models.User;
import cmg.example.galaxy_gym_backend.repositories.RoleRepository;
import cmg.example.galaxy_gym_backend.repositories.UserRepository;
import cmg.example.galaxy_gym_backend.services.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthControllerTest {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private JwtService jwtService;
    private AuthController authController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        roleRepository = Mockito.mock(RoleRepository.class);
        jwtService = Mockito.mock(JwtService.class);
        authController = new AuthController(userRepository, roleRepository, jwtService);
        ReflectionTestUtils.setField(authController, "googleClientId", "mock-google-client-id");
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testLoginSuccess() throws Exception {
        String password = "plainpassword";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword(hashedPassword);
        user.setIsActive(true);
        Role role = new Role();
        role.setName(Role.RoleType.ROLE_USER);
        user.setRoles(new HashSet<>(Collections.singletonList(role)));

        AuthRequest request = new AuthRequest();
        request.setCorreoElectronico("user@example.com");
        request.setContrasena(password);

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any(), any())).thenReturn("mocked-jwt-token");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mocked-jwt-token"))
                .andExpect(jsonPath("$.user.email").value("user@example.com"));
    }

    @Test
    void testLoginUnauthorized() throws Exception {
        AuthRequest request = new AuthRequest();
        request.setCorreoElectronico("user@example.com");
        request.setContrasena("wrongpassword");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.empty());

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGoogleLoginInvalidToken() throws Exception {
        GoogleAuthRequest request = new GoogleAuthRequest("invalid-id-token");

        // The verifier logic inside controller will throw IllegalArgumentException for mock-id-token
        // This is caught by GlobalExceptionHandler and returns a 500 error payload.
        mockMvc.perform(post("/auth/google")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }
}
