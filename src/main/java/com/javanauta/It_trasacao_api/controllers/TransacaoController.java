package com.javanauta.It_trasacao_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javanauta.It_trasacao_api.business.services.TransacaoService;
import com.javanauta.It_trasacao_api.controllers.dto.TransacaoRequestDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;
    @PostMapping
    public ResponseEntity<Void> registrarTransacao(@RequestBody TransacaoRequestDTO dto) {
        transacaoService.registrarTransacao(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTransacao(){ 
        transacaoService.deleteTransacao();
        return ResponseEntity.ok().build();
    }
}
