package com.javanauta.It_trasacao_api.business.services;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.javanauta.It_trasacao_api.controllers.dto.TransacaoRequestDTO;
import com.javanauta.It_trasacao_api.infrastructure.exceptions.UnprocessableEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransacaoService {

    private final List<TransacaoRequestDTO> transacoes = new ArrayList<>();

    public void registrarTransacao( TransacaoRequestDTO dto)  {
        
        log.info("Iniciando o registro da transação" + dto);

        if (dto.dataHora().isAfter(OffsetDateTime.now())) {
            log.error("Data e hora da transação no futuro.");
            throw new UnprocessableEntity("A data e hora da transação não podem ser no futuro.");
        } if (dto.valor() < 0) {
            log.error("Valor menor que 0.");
            throw new UnprocessableEntity("O valor da transação não pode ser negativo.");
        }
        transacoes.add(dto);
        log.info("Transação registrada com sucesso!" );
    }
    public void deleteTransacao()  { 
        log.info("Iniciando a exclusão de todas as transações");
        transacoes.clear();
        log.info("Transações foram excluídas com sucesso!");
    }

    public List<TransacaoRequestDTO> buscarTransacoes(Integer intervaloBusca) {
        log.info("Buscando todas as transações nos ultimos" + intervaloBusca + "segundos");
        OffsetDateTime dataHoraIntervalo = OffsetDateTime.now().minusDays(intervaloBusca);
        
        log.info("Retorno de transações com sucesso!");
        return transacoes.stream()
            .filter(transacao -> transacao.dataHora().isAfter(dataHoraIntervalo))
            .toList();
    }
}