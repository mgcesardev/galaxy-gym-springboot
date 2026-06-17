package cmg.example.galaxy_gym_backend.controllers;

import cmg.example.galaxy_gym_backend.exceptions.GlobalExceptionHandler;
import cmg.example.galaxy_gym_backend.models.Trainer;
import cmg.example.galaxy_gym_backend.services.TrainerService;
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

class TrainerControllerTest {

    private TrainerService trainerService;
    private TrainerController trainerController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        trainerService = Mockito.mock(TrainerService.class);
        trainerController = new TrainerController(trainerService);
        mockMvc = MockMvcBuilders.standaloneSetup(trainerController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testFindAll() throws Exception {
        Page<Trainer> page = new PageImpl<>(Collections.singletonList(new Trainer()), PageRequest.of(0, 10), 1);
        when(trainerService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/trainers"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindById() throws Exception {
        Trainer trainer = new Trainer();
        trainer.setId(1L);
        when(trainerService.findById(1L)).thenReturn(trainer);

        mockMvc.perform(get("/trainers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testSave() throws Exception {
        Trainer trainer = new Trainer();
        when(trainerService.save(any(Trainer.class))).thenReturn(trainer);

        mockMvc.perform(post("/trainers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trainer)))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/trainers/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdate() throws Exception {
        Trainer existing = new Trainer();
        existing.setId(1L);
        existing.setBiography("Old Bio");

        Trainer updated = new Trainer();
        updated.setId(1L);
        updated.setBiography("New Bio");

        when(trainerService.findById(1L)).thenReturn(existing);
        when(trainerService.save(any(Trainer.class))).thenReturn(updated);

        mockMvc.perform(patch("/trainers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.biography").value("New Bio"));
    }

    @Test
    void testUpdateNotFound() throws Exception {
        when(trainerService.findById(1L)).thenReturn(null);

        mockMvc.perform(patch("/trainers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Trainer())))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}
