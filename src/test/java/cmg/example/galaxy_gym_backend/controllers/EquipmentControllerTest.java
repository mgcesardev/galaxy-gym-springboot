package cmg.example.galaxy_gym_backend.controllers;

import cmg.example.galaxy_gym_backend.exceptions.GlobalExceptionHandler;
import cmg.example.galaxy_gym_backend.models.Equipment;
import cmg.example.galaxy_gym_backend.services.EquipmentService;
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

class EquipmentControllerTest {

    private EquipmentService equipmentService;
    private EquipmentController equipmentController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        equipmentService = Mockito.mock(EquipmentService.class);
        equipmentController = new EquipmentController(equipmentService);
        mockMvc = MockMvcBuilders.standaloneSetup(equipmentController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testFindAll() throws Exception {
        Page<Equipment> page = new PageImpl<>(Collections.singletonList(new Equipment()), PageRequest.of(0, 10), 1);
        when(equipmentService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/equipments"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindById() throws Exception {
        Equipment equipment = new Equipment();
        equipment.setId(1L);
        when(equipmentService.findById(1L)).thenReturn(equipment);

        mockMvc.perform(get("/equipments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testSave() throws Exception {
        Equipment equipment = new Equipment();
        when(equipmentService.save(any(Equipment.class))).thenReturn(equipment);

        mockMvc.perform(post("/equipments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(equipment)))
                .andExpect(status().isOk());
    }

    @Test
    void testBuscarPorNombre() throws Exception {
        Page<Equipment> page = new PageImpl<>(Collections.singletonList(new Equipment()), PageRequest.of(0, 10), 1);
        when(equipmentService.buscarPorNombre(eq("Treadmill"), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/equipments/buscar")
                        .param("nombre", "Treadmill"))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/equipments/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdate() throws Exception {
        Equipment existing = new Equipment();
        existing.setId(1L);
        existing.setName("Old Equipment");

        Equipment updated = new Equipment();
        updated.setId(1L);
        updated.setName("New Equipment");

        when(equipmentService.findById(1L)).thenReturn(existing);
        when(equipmentService.save(any(Equipment.class))).thenReturn(updated);

        mockMvc.perform(patch("/equipments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Equipment"));
    }

    @Test
    void testUpdateNotFound() throws Exception {
        when(equipmentService.findById(1L)).thenReturn(null);

        mockMvc.perform(patch("/equipments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Equipment())))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void testBuscarPorNombreException() throws Exception {
        when(equipmentService.buscarPorNombre(any(String.class), any(Pageable.class))).thenThrow(new RuntimeException("DB error"));

        mockMvc.perform(get("/equipments/buscar")
                        .param("nombre", "Treadmill"))
                .andExpect(status().isInternalServerError());
    }
}
