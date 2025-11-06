package com.clickshop.suporte.controller;

import com.clickshop.suporte.dto.OcorrenciaInputDTO;
import com.clickshop.suporte.model.Ocorrencia;
import com.clickshop.suporte.model.StatusOcorrencia;
import com.clickshop.suporte.service.OcorrenciaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ocorrencias") // Prefixo para todos os endpoints
public class OcorrenciaController {

    @Autowired
    private OcorrenciaService service;

    // CREATE: POST /api/ocorrencias
    @PostMapping
    public ResponseEntity<Ocorrencia> criarOcorrencia(@Valid @RequestBody OcorrenciaInputDTO inputDTO) {
        Ocorrencia novaOcorrencia = service.criar(inputDTO);
        // Retorna 201 Created e o objeto criado
        return new ResponseEntity<>(novaOcorrencia, HttpStatus.CREATED);
    }

    // READ (All): GET /api/ocorrencias
    // READ (Filtered): GET /api/ocorrencias?status=ABERTO
    @GetMapping
    public List<Ocorrencia> listarOcorrencias(@RequestParam(required = false) StatusOcorrencia status) {
        // Retorna 200 OK (padrão)
        return service.listarTodas(Optional.ofNullable(status));
    }

    // READ (By ID): GET /api/ocorrencias/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Ocorrencia> buscarOcorrenciaPorId(@PathVariable Long id) {
        Ocorrencia ocorrencia = service.buscarPorId(id);
        // Retorna 200 OK (padrão)
        return ResponseEntity.ok(ocorrencia);
    }

    // DELETE: DELETE /api/ocorrencias/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarOcorrencia(@PathVariable Long id) {
        service.deletar(id);
        // Retorna 204 No Content (sucesso sem corpo de resposta)
        return ResponseEntity.noContent().build();
    }

    // UPDATE (Ação Específica): PATCH /api/ocorrencias/{id}/encerrar
    @PatchMapping("/{id}/encerrar") // Usamos PATCH pois é uma atualização parcial/ação
    public ResponseEntity<Ocorrencia> encerrarOcorrencia(@PathVariable Long id) {
        Ocorrencia ocorrenciaAtualizada = service.encerrar(id);
        // Retorna 200 OK com o objeto atualizado
        return ResponseEntity.ok(ocorrenciaAtualizada);
    }
}