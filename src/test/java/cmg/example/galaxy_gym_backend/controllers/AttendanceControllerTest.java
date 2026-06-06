package cmg.example.galaxy_gym_backend.controllers;

import cmg.example.galaxy_gym_backend.models.Attendance;
import cmg.example.galaxy_gym_backend.services.AttendanceService;
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

class AttendanceControllerTest {

    private AttendanceService attendanceService;
    private AttendanceController attendanceController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        attendanceService = Mockito.mock(AttendanceService.class);
        attendanceController = new AttendanceController(attendanceService);
        mockMvc = MockMvcBuilders.standaloneSetup(attendanceController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testFindAll() throws Exception {
        Page<Attendance> page = new PageImpl<>(Collections.singletonList(new Attendance()), PageRequest.of(0, 10), 1);
        when(attendanceService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/attendances"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindById() throws Exception {
        Attendance attendance = new Attendance();
        attendance.setId(1L);
        when(attendanceService.findById(1L)).thenReturn(attendance);

        mockMvc.perform(get("/attendances/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testSave() throws Exception {
        Attendance attendance = new Attendance();
        when(attendanceService.save(any(Attendance.class))).thenReturn(attendance);

        mockMvc.perform(post("/attendances")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(attendance)))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/attendances/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdate() throws Exception {
        Attendance existing = new Attendance();
        existing.setId(1L);
        existing.setNotes("Old Notes");

        Attendance updated = new Attendance();
        updated.setId(1L);
        updated.setNotes("New Notes");

        when(attendanceService.findById(1L)).thenReturn(existing);
        when(attendanceService.save(any(Attendance.class))).thenReturn(updated);

        mockMvc.perform(patch("/attendances/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notes").value("New Notes"));
    }

    @Test
    void testUpdateNotFound() throws Exception {
        when(attendanceService.findById(1L)).thenReturn(null);

        mockMvc.perform(patch("/attendances/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Attendance())))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}
