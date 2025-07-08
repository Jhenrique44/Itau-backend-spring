package com.javanauta.It_trasacao_api.business.service;

import static org.mockito.Mockito.*;

import java.time.OffsetDateTime;
import java.util.Collections;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.javanauta.It_trasacao_api.business.services.EstatisticasService;
import com.javanauta.It_trasacao_api.business.services.TransacaoService;
import com.javanauta.It_trasacao_api.controllers.dto.EstatisticasResponseDTO;
import com.javanauta.It_trasacao_api.controllers.dto.TransacaoRequestDTO;

@ExtendWith(MockitoExtension.class)
public class EstatisticaServiceTest {

    @InjectMocks
    EstatisticasService estatisticasService;

    @Mock
    TransacaoService transacaoService;

    TransacaoRequestDTO transacao;

    EstatisticasResponseDTO estatisticas;

    @BeforeEach
    void setUp() {
        transacao = new TransacaoRequestDTO(20.0, OffsetDateTime.now());
        estatisticas = new EstatisticasResponseDTO(1L, 20.0, 20.0, 20.0, 20.0);
    }

    @Test
    void calcEstatisticaComSucesso() {
        when(transacaoService.buscarTransacoes(60))
                .thenReturn(Collections.singletonList(transacao));

        EstatisticasResponseDTO resultado = estatisticasService.calcEstatisticaTransacoes(60);

        verify(transacaoService, times(1)).buscarTransacoes(60);

        Assertions.assertThat(resultado).usingRecursiveComparison().isEqualTo(estatisticas);
    }

    @Test
    void calcEstatisticaListaVazia() {
        EstatisticasResponseDTO estatisticaEsperado = new EstatisticasResponseDTO(0L, 0.0, 0.0, 0.0, 0.0);
        
        when(transacaoService.buscarTransacoes(60))
                .thenReturn(Collections.emptyList());

        EstatisticasResponseDTO resultado = estatisticasService.calcEstatisticaTransacoes(60);

        verify(transacaoService, times(1)).buscarTransacoes(60);

        Assertions.assertThat(resultado).usingRecursiveComparison().isEqualTo(estatisticaEsperado);
    }
}
