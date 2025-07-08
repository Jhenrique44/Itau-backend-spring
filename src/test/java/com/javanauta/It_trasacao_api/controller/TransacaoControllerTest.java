package com.javanauta.It_trasacao_api.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.javanauta.It_trasacao_api.business.services.TransacaoService;
import com.javanauta.It_trasacao_api.controllers.TransacaoController;
import com.javanauta.It_trasacao_api.controllers.dto.TransacaoRequestDTO;
import com.javanauta.It_trasacao_api.infrastructure.exceptions.UnprocessableEntity;

@ExtendWith(MockitoExtension.class)
public class TransacaoControllerTest {

    @InjectMocks
    TransacaoController transacaoController;

    @Mock 
    TransacaoService transacaoService;

    TransacaoRequestDTO transacao;

    MockMvc mockMvc;

    @Autowired
    final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp(){ 
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(transacaoController).build();
        transacao = new TransacaoRequestDTO(20.0, OffsetDateTime.of(2025, 2, 10, 14, 30, 0, 0, ZoneOffset.UTC));
    }

    @Test
    void deveRegistrarTransacaoComSucesso() throws Exception {
        doNothing().when(transacaoService).registrarTransacao(transacao);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/transacao")
                .content(objectMapper.writeValueAsString(transacao))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isCreated());
    }

    @Test 
    void gerarExceptionAdicionarTransacao() throws Exception{ 

        doThrow(new UnprocessableEntity("Erro de requisição"))
            .when(transacaoService).registrarTransacao(transacao);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/transacao")
                .content(objectMapper.writeValueAsString(transacao))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void deveExcluirTransacaoComSucesso() throws Exception {
        doNothing().when(transacaoService).deleteTransacao();

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/transacao"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
    }

}
