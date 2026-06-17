package cmg.example.galaxy_gym_backend.controllers;

import cmg.example.galaxy_gym_backend.exceptions.GlobalExceptionHandler;
import cmg.example.galaxy_gym_backend.models.MembershipPlan;
import cmg.example.galaxy_gym_backend.services.MembershipPlanService;
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

class MembershipPlanControllerTest {

    private MembershipPlanService membershipPlanService;
    private MembershipPlanController membershipPlanController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        membershipPlanService = Mockito.mock(MembershipPlanService.class);
        membershipPlanController = new MembershipPlanController(membershipPlanService);
        mockMvc = MockMvcBuilders.standaloneSetup(membershipPlanController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testFindAll() throws Exception {
        Page<MembershipPlan> page = new PageImpl<>(Collections.singletonList(new MembershipPlan()), PageRequest.of(0, 10), 1);
        when(membershipPlanService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/membership-plans"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindById() throws Exception {
        MembershipPlan plan = new MembershipPlan();
        plan.setId(1L);
        when(membershipPlanService.findById(1L)).thenReturn(plan);

        mockMvc.perform(get("/membership-plans/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testSave() throws Exception {
        MembershipPlan plan = new MembershipPlan();
        when(membershipPlanService.save(any(MembershipPlan.class))).thenReturn(plan);

        mockMvc.perform(post("/membership-plans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(plan)))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/membership-plans/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdate() throws Exception {
        MembershipPlan existing = new MembershipPlan();
        existing.setId(1L);
        existing.setName("Old Plan");

        MembershipPlan updated = new MembershipPlan();
        updated.setId(1L);
        updated.setName("New Plan");

        when(membershipPlanService.findById(1L)).thenReturn(existing);
        when(membershipPlanService.save(any(MembershipPlan.class))).thenReturn(updated);

        mockMvc.perform(patch("/membership-plans/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Plan"));
    }

    @Test
    void testUpdateNotFound() throws Exception {
        when(membershipPlanService.findById(1L)).thenReturn(null);

        mockMvc.perform(patch("/membership-plans/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new MembershipPlan())))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}
