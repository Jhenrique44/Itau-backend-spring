package com.javanauta.It_trasacao_api.business.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.time.OffsetDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.javanauta.It_trasacao_api.business.services.TransacaoService;
import com.javanauta.It_trasacao_api.controllers.dto.EstatisticasResponseDTO;
import com.javanauta.It_trasacao_api.controllers.dto.TransacaoRequestDTO;
import com.javanauta.It_trasacao_api.infrastructure.exceptions.UnprocessableEntity;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

    @InjectMocks
    TransacaoService transacaoService;

    TransacaoRequestDTO transacao;

    EstatisticasResponseDTO estatisticas;

    @BeforeEach
    void setUp() {
        transacao = new TransacaoRequestDTO(20.0, OffsetDateTime.now());
        estatisticas = new EstatisticasResponseDTO(1L, 20.0, 20.0, 20.0, 20.0);
    }

    @Test
    void transacaoComSucesso() {

        transacaoService.registrarTransacao(transacao);

        List<TransacaoRequestDTO> transacoes = transacaoService.buscarTransacoes(5000);

        assertTrue(transacoes.contains(transacao), "Transação não encontrada na lista de transações");

    }

    @Test
    void trowExceptionWhenValueNegative(){
        UnprocessableEntity exception = assertThrows(UnprocessableEntity.class, 
            () -> transacaoService.registrarTransacao(new TransacaoRequestDTO(-20.0, OffsetDateTime.now())));
        
        assertEquals("O valor da transação não pode ser negativo.", exception.getMessage());
    }
    
    @Test
    void trowExceptionWhenDateInFuture() {
        UnprocessableEntity exception = assertThrows(UnprocessableEntity.class, 
            () -> transacaoService.registrarTransacao(new TransacaoRequestDTO(20.0, OffsetDateTime.now().plusDays(1))));
        
        assertEquals("A data e hora da transação não podem ser no futuro.", exception.getMessage());                        
    }

    @Test
    void whenDeleteTransacaoSucess() {
        transacaoService.deleteTransacao();

        List<TransacaoRequestDTO> transacoes = transacaoService.buscarTransacoes(5000);
        assertTrue(transacoes.isEmpty());
    }

    @Test
    void buscarTransacoesComSucesso() {

        TransacaoRequestDTO dto =  new TransacaoRequestDTO(10.00, OffsetDateTime.now().minusHours(1));
        
        transacaoService.registrarTransacao(transacao);
        transacaoService.registrarTransacao(dto);
        
        List<TransacaoRequestDTO> transacoes = transacaoService.buscarTransacoes(60);
        
        assertTrue(transacoes.contains(transacao));
        assertFalse(transacoes.contains(dto), "Transação fora do intervalo não deveria estar presente na lista");
    }

}
