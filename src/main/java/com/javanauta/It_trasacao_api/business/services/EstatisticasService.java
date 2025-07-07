package com.javanauta.It_trasacao_api.business.services;

import java.util.DoubleSummaryStatistics;
import java.util.List;

import org.springframework.stereotype.Service;

import com.javanauta.It_trasacao_api.controllers.dto.EstatisticasResponseDTO;
import com.javanauta.It_trasacao_api.controllers.dto.TransacaoRequestDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstatisticasService {

    public final TransacaoService transacaoService;

    public EstatisticasResponseDTO calcEstatisticaTransacoes(Integer intervaloBusca){
        
        log.info("Iniciando o cálculo das estatísticas das transações pelo periodo de tempo" + intervaloBusca + " segundos");
        List<TransacaoRequestDTO> transacoes = transacaoService.buscarTransacoes(intervaloBusca);
        
        if(transacoes.isEmpty()){
            log.error("Nenhuma transação encontrada.");
            return new EstatisticasResponseDTO(0L,0.0,0.0,0.0,0.0);
        }
        
        DoubleSummaryStatistics estatisticasTransacoes = transacoes.stream()
            .mapToDouble(TransacaoRequestDTO::valor)
            .summaryStatistics(); 
        log.info("Estatísticas calculadas!");
        return new EstatisticasResponseDTO(estatisticasTransacoes.getCount(),
                                           estatisticasTransacoes.getSum(),
                                           estatisticasTransacoes.getMin(),
                                           estatisticasTransacoes.getMax(),
                                           estatisticasTransacoes.getAverage());
    }

}
