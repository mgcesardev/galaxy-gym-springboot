package cmg.example.galaxy_gym_backend.controllers;

import cmg.example.galaxy_gym_backend.exceptions.GlobalExceptionHandler;
import cmg.example.galaxy_gym_backend.models.GymClass;
import cmg.example.galaxy_gym_backend.services.GymClassService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GymClassControllerTest {

    private GymClassService gymClassService;
    private GymClassController gymClassController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        gymClassService = Mockito.mock(GymClassService.class);
        gymClassController = new GymClassController(gymClassService);
        mockMvc = MockMvcBuilders.standaloneSetup(gymClassController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testFindAll() throws Exception {
        Page<GymClass> page = new PageImpl<>(Collections.singletonList(new GymClass()), PageRequest.of(0, 10), 1);
        when(gymClassService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/gym-classes"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindById() throws Exception {
        GymClass gymClass = new GymClass();
        gymClass.setId(1L);
        when(gymClassService.findById(1L)).thenReturn(gymClass);

        mockMvc.perform(get("/gym-classes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testSave() throws Exception {
        GymClass gymClass = new GymClass();
        when(gymClassService.save(any(GymClass.class))).thenReturn(gymClass);

        mockMvc.perform(post("/gym-classes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gymClass)))
                .andExpect(status().isOk());
    }

    @Test
    void testBuscarPorNombre() throws Exception {
        Page<GymClass> page = new PageImpl<>(Collections.singletonList(new GymClass()), PageRequest.of(0, 10), 1);
        when(gymClassService.buscarPorNombre(eq("Yoga"), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/gym-classes/buscar")
                        .param("nombre", "Yoga"))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/gym-classes/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdate() throws Exception {
        GymClass existing = new GymClass();
        existing.setId(1L);
        existing.setName("Old Class");

        GymClass updated = new GymClass();
        updated.setId(1L);
        updated.setName("New Class");

        when(gymClassService.findById(1L)).thenReturn(existing);
        when(gymClassService.save(any(GymClass.class))).thenReturn(updated);

        mockMvc.perform(patch("/gym-classes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Class"));
    }

    @Test
    void testUpdateNotFound() throws Exception {
        when(gymClassService.findById(1L)).thenReturn(null);

        mockMvc.perform(patch("/gym-classes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new GymClass())))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void testBuscarPorNombreException() throws Exception {
        when(gymClassService.buscarPorNombre(any(String.class), any(Pageable.class))).thenThrow(new RuntimeException("DB error"));

        mockMvc.perform(get("/gym-classes/buscar")
                        .param("nombre", "Yoga"))
                .andExpect(status().isInternalServerError());
    }
}
