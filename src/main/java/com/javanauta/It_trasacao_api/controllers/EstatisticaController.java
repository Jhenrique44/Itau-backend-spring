package com.javanauta.It_trasacao_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javanauta.It_trasacao_api.business.services.EstatisticasService;
import com.javanauta.It_trasacao_api.controllers.dto.EstatisticasResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/estatistica")
public class EstatisticaController {

    private final EstatisticasService estatisticaService;

    @GetMapping
    @Operation(description = "Endpoint responsavel por buscar as estatísticas das transações")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso!"),
        @ApiResponse(responseCode = "400", description = "Erro na busca de estatísticas de transações"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<EstatisticasResponseDTO> buscarEstatisticas(
        @RequestParam(value = "intervaloBusca", required = false, defaultValue = "60") Integer intervaloBusca){
            return ResponseEntity.ok(
                estatisticaService.calcEstatisticaTransacoes(intervaloBusca));
    }
    
}
