package cmg.example.galaxy_gym_backend.controllers;

import cmg.example.galaxy_gym_backend.exceptions.GlobalExceptionHandler;
import cmg.example.galaxy_gym_backend.models.Reservation;
import cmg.example.galaxy_gym_backend.services.ReservationService;
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

class ReservationControllerTest {

    private ReservationService reservationService;
    private ReservationController reservationController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        reservationService = Mockito.mock(ReservationService.class);
        reservationController = new ReservationController(reservationService);
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testFindAll() throws Exception {
        Page<Reservation> page = new PageImpl<>(Collections.singletonList(new Reservation()), PageRequest.of(0, 10), 1);
        when(reservationService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/reservations"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindById() throws Exception {
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        when(reservationService.findById(1L)).thenReturn(reservation);

        mockMvc.perform(get("/reservations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testSave() throws Exception {
        Reservation reservation = new Reservation();
        when(reservationService.save(any(Reservation.class))).thenReturn(reservation);

        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservation)))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/reservations/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdate() throws Exception {
        Reservation existing = new Reservation();
        existing.setId(1L);
        existing.setNotes("Old Notes");

        Reservation updated = new Reservation();
        updated.setId(1L);
        updated.setNotes("New Notes");

        when(reservationService.findById(1L)).thenReturn(existing);
        when(reservationService.save(any(Reservation.class))).thenReturn(updated);

        mockMvc.perform(patch("/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notes").value("New Notes"));
    }

    @Test
    void testUpdateNotFound() throws Exception {
        when(reservationService.findById(1L)).thenReturn(null);

        mockMvc.perform(patch("/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Reservation())))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}
