package com.javanauta.It_trasacao_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javanauta.It_trasacao_api.business.services.EstatisticasService;
import com.javanauta.It_trasacao_api.controllers.dto.EstatisticasResponseDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/estatistica")
public class EstatisticaController {

    private final EstatisticasService estatisticaService;

    public ResponseEntity<EstatisticasResponseDTO> buscarEstatisticas(
        @RequestParam(value = "intervaloBusca", required = false, defaultValue = "60") Integer intervaloBusca){
            return ResponseEntity.ok(
                estatisticaService.calcEstatisticaTransacoes(intervaloBusca));
    }
    
}
