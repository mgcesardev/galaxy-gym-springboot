package cmg.example.galaxy_gym_backend.controllers;

import cmg.example.galaxy_gym_backend.exceptions.GlobalExceptionHandler;
import cmg.example.galaxy_gym_backend.models.RoutineExercise;
import cmg.example.galaxy_gym_backend.services.RoutineExerciseService;
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

class RoutineExerciseControllerTest {

    private RoutineExerciseService routineExerciseService;
    private RoutineExerciseController routineExerciseController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        routineExerciseService = Mockito.mock(RoutineExerciseService.class);
        routineExerciseController = new RoutineExerciseController(routineExerciseService);
        mockMvc = MockMvcBuilders.standaloneSetup(routineExerciseController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testFindAll() throws Exception {
        Page<RoutineExercise> page = new PageImpl<>(Collections.singletonList(new RoutineExercise()), PageRequest.of(0, 10), 1);
        when(routineExerciseService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/routine-exercises"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindById() throws Exception {
        RoutineExercise routineExercise = new RoutineExercise();
        routineExercise.setId(1L);
        when(routineExerciseService.findById(1L)).thenReturn(routineExercise);

        mockMvc.perform(get("/routine-exercises/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testSave() throws Exception {
        RoutineExercise routineExercise = new RoutineExercise();
        when(routineExerciseService.save(any(RoutineExercise.class))).thenReturn(routineExercise);

        mockMvc.perform(post("/routine-exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(routineExercise)))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/routine-exercises/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdate() throws Exception {
        RoutineExercise existing = new RoutineExercise();
        existing.setId(1L);
        existing.setNotes("Old Notes");

        RoutineExercise updated = new RoutineExercise();
        updated.setId(1L);
        updated.setNotes("New Notes");

        when(routineExerciseService.findById(1L)).thenReturn(existing);
        when(routineExerciseService.save(any(RoutineExercise.class))).thenReturn(updated);

        mockMvc.perform(patch("/routine-exercises/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notes").value("New Notes"));
    }

    @Test
    void testUpdateNotFound() throws Exception {
        when(routineExerciseService.findById(1L)).thenReturn(null);

        mockMvc.perform(patch("/routine-exercises/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RoutineExercise())))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}
