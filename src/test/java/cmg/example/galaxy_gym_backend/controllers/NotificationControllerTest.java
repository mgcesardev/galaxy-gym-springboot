package cmg.example.galaxy_gym_backend.controllers;

import cmg.example.galaxy_gym_backend.exceptions.GlobalExceptionHandler;
import cmg.example.galaxy_gym_backend.models.Notification;
import cmg.example.galaxy_gym_backend.services.NotificationService;
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

class NotificationControllerTest {

    private NotificationService notificationService;
    private NotificationController notificationController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        notificationService = Mockito.mock(NotificationService.class);
        notificationController = new NotificationController(notificationService);
        mockMvc = MockMvcBuilders.standaloneSetup(notificationController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testFindAll() throws Exception {
        Page<Notification> page = new PageImpl<>(Collections.singletonList(new Notification()), PageRequest.of(0, 10), 1);
        when(notificationService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/notifications"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindById() throws Exception {
        Notification notification = new Notification();
        notification.setId(1L);
        when(notificationService.findById(1L)).thenReturn(notification);

        mockMvc.perform(get("/notifications/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testSave() throws Exception {
        Notification notification = new Notification();
        when(notificationService.save(any(Notification.class))).thenReturn(notification);

        mockMvc.perform(post("/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notification)))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/notifications/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdate() throws Exception {
        Notification existing = new Notification();
        existing.setId(1L);
        existing.setTitle("Old Title");

        Notification updated = new Notification();
        updated.setId(1L);
        updated.setTitle("New Title");

        when(notificationService.findById(1L)).thenReturn(existing);
        when(notificationService.save(any(Notification.class))).thenReturn(updated);

        mockMvc.perform(patch("/notifications/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Title"));
    }

    @Test
    void testUpdateNotFound() throws Exception {
        when(notificationService.findById(1L)).thenReturn(null);

        mockMvc.perform(patch("/notifications/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Notification())))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}
