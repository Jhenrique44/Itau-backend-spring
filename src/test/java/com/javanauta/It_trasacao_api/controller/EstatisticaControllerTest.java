package com.javanauta.It_trasacao_api.controller;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.javanauta.It_trasacao_api.business.services.EstatisticasService;
import com.javanauta.It_trasacao_api.controllers.EstatisticaController;
import com.javanauta.It_trasacao_api.controllers.dto.EstatisticasResponseDTO;

@ExtendWith(MockitoExtension.class)
public class EstatisticaControllerTest {

    @InjectMocks
    EstatisticaController estatisticaController;

    @Mock
    EstatisticasService estatisticasService;

    EstatisticasResponseDTO estatisticas;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(estatisticaController).build();

        estatisticas = new EstatisticasResponseDTO(1L, 20.0, 20.0, 20.0, 20.0);
    }

    @Test
    void deveBuscarEstatisticasComSucesso() throws Exception {

        when(estatisticasService.calcEstatisticaTransacoes(60)).thenReturn(estatisticas);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/estatistica")
                .param("intervaloBusca", "60")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.count").value(estatisticas.count()));

    }
}
