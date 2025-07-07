package com.javanauta.It_trasacao_api.business.services;

import java.util.DoubleSummaryStatistics;
import java.util.List;

import org.springframework.stereotype.Service;

import com.javanauta.It_trasacao_api.controllers.dto.EstatisticasResponseDTO;
import com.javanauta.It_trasacao_api.controllers.dto.TransacaoRequestDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstatisticasService {

    public final TransacaoService transacaoService;

    public EstatisticasResponseDTO calcEstatisticaTransacoes(Integer intervaloBusca){
        List<TransacaoRequestDTO> transacoes = transacaoService.buscarTransacoes(intervaloBusca);
        
        DoubleSummaryStatistics estatisticasTransacoes = transacoes.stream()
            .mapToDouble(TransacaoRequestDTO::valor)
            .summaryStatistics(); 
        return new EstatisticasResponseDTO(estatisticasTransacoes.getCount(),
                                           estatisticasTransacoes.getSum(),
                                           estatisticasTransacoes.getMin(),
                                           estatisticasTransacoes.getMax(),
                                           estatisticasTransacoes.getAverage());
    }

}
