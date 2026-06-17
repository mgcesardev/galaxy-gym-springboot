package cmg.example.galaxy_gym_backend.controllers;

import cmg.example.galaxy_gym_backend.exceptions.GlobalExceptionHandler;
import cmg.example.galaxy_gym_backend.models.Membership;
import cmg.example.galaxy_gym_backend.services.MembershipService;
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

class MembershipControllerTest {

    private MembershipService membershipService;
    private MembershipController membershipController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        membershipService = Mockito.mock(MembershipService.class);
        membershipController = new MembershipController(membershipService);
        mockMvc = MockMvcBuilders.standaloneSetup(membershipController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testFindAll() throws Exception {
        Page<Membership> page = new PageImpl<>(Collections.singletonList(new Membership()), PageRequest.of(0, 10), 1);
        when(membershipService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/memberships"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindById() throws Exception {
        Membership membership = new Membership();
        membership.setId(1L);

        when(membershipService.findById(1L)).thenReturn(membership);

        mockMvc.perform(get("/memberships/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testSave() throws Exception {
        Membership membership = new Membership();

        when(membershipService.save(any(Membership.class))).thenReturn(membership);

        mockMvc.perform(post("/memberships")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(membership)))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/memberships/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdate() throws Exception {
        Membership existing = new Membership();
        existing.setId(1L);
        existing.setNotes("Old Notes");

        Membership updated = new Membership();
        updated.setId(1L);
        updated.setNotes("New Notes");

        when(membershipService.findById(1L)).thenReturn(existing);
        when(membershipService.save(any(Membership.class))).thenReturn(updated);

        mockMvc.perform(patch("/memberships/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notes").value("New Notes"));
    }

    @Test
    void testUpdateNotFound() throws Exception {
        when(membershipService.findById(1L)).thenReturn(null);

        mockMvc.perform(patch("/memberships/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Membership())))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}
