package cmg.example.galaxy_gym_backend.controllers;

import cmg.example.galaxy_gym_backend.exceptions.GlobalExceptionHandler;
import cmg.example.galaxy_gym_backend.models.Role;
import cmg.example.galaxy_gym_backend.services.RoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RoleControllerTest {

    private RoleService roleService;
    private RoleController roleController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        roleService = Mockito.mock(RoleService.class);
        roleController = new RoleController(roleService);
        mockMvc = MockMvcBuilders.standaloneSetup(roleController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testFindAll() throws Exception {
        Page<Role> page = new PageImpl<>(Collections.singletonList(new Role()), PageRequest.of(0, 10), 1);
        when(roleService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/roles"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindById() throws Exception {
        Role role = new Role();
        role.setId(1L);
        when(roleService.findById(1L)).thenReturn(role);

        mockMvc.perform(get("/roles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testSave() throws Exception {
        Role role = new Role();
        when(roleService.save(any(Role.class))).thenReturn(role);

        mockMvc.perform(post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(role)))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/roles/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdate() throws Exception {
        Role existing = new Role();
        existing.setId(1L);
        existing.setDescription("Old Desc");

        Role updated = new Role();
        updated.setId(1L);
        updated.setDescription("New Desc");

        when(roleService.findById(1L)).thenReturn(existing);
        when(roleService.save(any(Role.class))).thenReturn(updated);

        mockMvc.perform(patch("/roles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("New Desc"));
    }

    @Test
    void testUpdateNotFound() throws Exception {
        when(roleService.findById(1L)).thenReturn(null);

        mockMvc.perform(patch("/roles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Role())))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}
