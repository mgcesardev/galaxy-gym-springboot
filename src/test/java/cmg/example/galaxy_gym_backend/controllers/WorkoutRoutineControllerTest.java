package cmg.example.galaxy_gym_backend.controllers;

import cmg.example.galaxy_gym_backend.exceptions.GlobalExceptionHandler;
import cmg.example.galaxy_gym_backend.models.WorkoutRoutine;
import cmg.example.galaxy_gym_backend.services.WorkoutRoutineService;
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

class WorkoutRoutineControllerTest {

    private WorkoutRoutineService workoutRoutineService;
    private WorkoutRoutineController workoutRoutineController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        workoutRoutineService = Mockito.mock(WorkoutRoutineService.class);
        workoutRoutineController = new WorkoutRoutineController(workoutRoutineService);
        mockMvc = MockMvcBuilders.standaloneSetup(workoutRoutineController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testFindAll() throws Exception {
        Page<WorkoutRoutine> page = new PageImpl<>(Collections.singletonList(new WorkoutRoutine()), PageRequest.of(0, 10), 1);
        when(workoutRoutineService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/workout-routines"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindById() throws Exception {
        WorkoutRoutine routine = new WorkoutRoutine();
        routine.setId(1L);
        when(workoutRoutineService.findById(1L)).thenReturn(routine);

        mockMvc.perform(get("/workout-routines/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testSave() throws Exception {
        WorkoutRoutine routine = new WorkoutRoutine();
        when(workoutRoutineService.save(any(WorkoutRoutine.class))).thenReturn(routine);

        mockMvc.perform(post("/workout-routines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(routine)))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/workout-routines/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdate() throws Exception {
        WorkoutRoutine existing = new WorkoutRoutine();
        existing.setId(1L);
        existing.setName("Old Name");

        WorkoutRoutine updated = new WorkoutRoutine();
        updated.setId(1L);
        updated.setName("New Name");

        when(workoutRoutineService.findById(1L)).thenReturn(existing);
        when(workoutRoutineService.save(any(WorkoutRoutine.class))).thenReturn(updated);

        mockMvc.perform(patch("/workout-routines/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Name"));
    }

    @Test
    void testUpdateNotFound() throws Exception {
        when(workoutRoutineService.findById(1L)).thenReturn(null);

        mockMvc.perform(patch("/workout-routines/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new WorkoutRoutine())))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}
