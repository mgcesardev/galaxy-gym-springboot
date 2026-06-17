package cmg.example.galaxy_gym_backend.controllers;

import cmg.example.galaxy_gym_backend.exceptions.GlobalExceptionHandler;
import cmg.example.galaxy_gym_backend.models.Exercise;
import cmg.example.galaxy_gym_backend.services.ExerciseService;
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

class ExerciseControllerTest {

    private ExerciseService exerciseService;
    private ExerciseController exerciseController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        exerciseService = Mockito.mock(ExerciseService.class);
        exerciseController = new ExerciseController(exerciseService);
        mockMvc = MockMvcBuilders.standaloneSetup(exerciseController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testFindAll() throws Exception {
        Page<Exercise> page = new PageImpl<>(Collections.singletonList(new Exercise()), PageRequest.of(0, 10), 1);
        when(exerciseService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/exercises"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindById() throws Exception {
        Exercise exercise = new Exercise();
        exercise.setId(1L);
        exercise.setName("Push Up");

        when(exerciseService.findById(1L)).thenReturn(exercise);

        mockMvc.perform(get("/exercises/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Push Up"));
    }

    @Test
    void testSave() throws Exception {
        Exercise exercise = new Exercise();
        exercise.setName("Squat");

        when(exerciseService.save(any(Exercise.class))).thenReturn(exercise);

        mockMvc.perform(post("/exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exercise)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Squat"));
    }

    @Test
    void testBuscarPorNombre() throws Exception {
        Page<Exercise> page = new PageImpl<>(Collections.singletonList(new Exercise()), PageRequest.of(0, 10), 1);
        when(exerciseService.buscarPorNombre(eq("Squat"), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/exercises/buscar")
                        .param("nombre", "Squat"))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/exercises/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdate() throws Exception {
        Exercise existing = new Exercise();
        existing.setId(1L);
        existing.setName("Old Exercise");

        Exercise updated = new Exercise();
        updated.setId(1L);
        updated.setName("New Exercise");

        when(exerciseService.findById(1L)).thenReturn(existing);
        when(exerciseService.save(any(Exercise.class))).thenReturn(updated);

        mockMvc.perform(patch("/exercises/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Exercise"));
    }

    @Test
    void testUpdateNotFound() throws Exception {
        when(exerciseService.findById(1L)).thenReturn(null);

        mockMvc.perform(patch("/exercises/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Exercise())))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void testBuscarPorNombreException() throws Exception {
        when(exerciseService.buscarPorNombre(any(String.class), any(Pageable.class))).thenThrow(new RuntimeException("DB error"));

        mockMvc.perform(get("/exercises/buscar")
                        .param("nombre", "Squat"))
                .andExpect(status().isInternalServerError());
    }
}
