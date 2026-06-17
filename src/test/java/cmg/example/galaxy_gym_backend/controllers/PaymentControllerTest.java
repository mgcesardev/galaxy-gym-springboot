package cmg.example.galaxy_gym_backend.controllers;

import cmg.example.galaxy_gym_backend.exceptions.GlobalExceptionHandler;
import cmg.example.galaxy_gym_backend.models.Payment;
import cmg.example.galaxy_gym_backend.services.PaymentService;
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

class PaymentControllerTest {

    private PaymentService paymentService;
    private PaymentController paymentController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        paymentService = Mockito.mock(PaymentService.class);
        paymentController = new PaymentController(paymentService);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testFindAll() throws Exception {
        Page<Payment> page = new PageImpl<>(Collections.singletonList(new Payment()), PageRequest.of(0, 10), 1);
        when(paymentService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/payments"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindById() throws Exception {
        Payment payment = new Payment();
        payment.setId(1L);
        when(paymentService.findById(1L)).thenReturn(payment);

        mockMvc.perform(get("/payments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testSave() throws Exception {
        Payment payment = new Payment();
        when(paymentService.save(any(Payment.class))).thenReturn(payment);

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payment)))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/payments/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdate() throws Exception {
        Payment existing = new Payment();
        existing.setId(1L);
        existing.setDescription("Old Desc");

        Payment updated = new Payment();
        updated.setId(1L);
        updated.setDescription("New Desc");

        when(paymentService.findById(1L)).thenReturn(existing);
        when(paymentService.save(any(Payment.class))).thenReturn(updated);

        mockMvc.perform(patch("/payments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("New Desc"));
    }

    @Test
    void testUpdateNotFound() throws Exception {
        when(paymentService.findById(1L)).thenReturn(null);

        mockMvc.perform(patch("/payments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Payment())))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}
