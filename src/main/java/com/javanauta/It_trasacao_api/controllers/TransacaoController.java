package com.javanauta.It_trasacao_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javanauta.It_trasacao_api.business.services.TransacaoService;
import com.javanauta.It_trasacao_api.controllers.dto.TransacaoRequestDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;
    @PostMapping
    @Operation(description = "Endpoint para registrar uma transação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Transação registrada com sucesso"),
        @ApiResponse(responseCode = "422", description = "Erro de validação, dados"),
        @ApiResponse(responseCode = "400", description = "Erro de requisição"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> registrarTransacao(@RequestBody TransacaoRequestDTO dto) {
        transacaoService.registrarTransacao(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @Operation(description = "Endpoint para deletar transações")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transação deletada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de requisição"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> deleteTransacao(){ 
        transacaoService.deleteTransacao();
        return ResponseEntity.ok().build();
    }
}
