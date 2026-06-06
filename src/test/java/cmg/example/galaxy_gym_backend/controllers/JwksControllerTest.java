package cmg.example.galaxy_gym_backend.controllers;

import cmg.example.galaxy_gym_backend.config.JwkKeyManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class JwksControllerTest {

    private JwkKeyManager jwkKeyManager;
    private JwksController jwksController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        jwkKeyManager = new JwkKeyManager();
        jwksController = new JwksController(jwkKeyManager);
        mockMvc = MockMvcBuilders.standaloneSetup(jwksController).build();
    }

    @Test
    void testGetJwkSet() throws Exception {
        mockMvc.perform(get("/auth/jwks.json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.keys").isArray())
                .andExpect(jsonPath("$.keys[0].kty").value("RSA"))
                .andExpect(jsonPath("$.keys[0].kid").exists())
                .andExpect(jsonPath("$.keys[0].d").doesNotExist()); // Ensure private key is NOT exposed
    }
}
