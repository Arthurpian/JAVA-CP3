package com.clickshop.suporte.service;

import com.clickshop.suporte.dto.OcorrenciaInputDTO;
import com.clickshop.suporte.exception.BusinessRuleException;
import com.clickshop.suporte.exception.ResourceNotFoundException;
import com.clickshop.suporte.model.Ocorrencia;
import com.clickshop.suporte.model.StatusOcorrencia;
import com.clickshop.suporte.repository.OcorrenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service // Define que esta classe é um "Bean" de serviço
public class OcorrenciaService {

    @Autowired
    private OcorrenciaRepository repository;

    // Método para buscar uma ocorrência (usado internamente)
    private Ocorrencia buscarPorIdOuFalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ocorrência não encontrada com o ID: " + id));
    }

    // CREATE
    public Ocorrencia criar(OcorrenciaInputDTO inputDTO) {
        // Converte DTO para Modelo (o construtor já define o status e data)
        Ocorrencia novaOcorrencia = new Ocorrencia(
                null, // ID será gerado pelo repositório
                inputDTO.getTitulo(),
                inputDTO.getDescricao(),
                inputDTO.getSolicitante()
        );
        return repository.save(novaOcorrencia);
    }

    // READ (All)
    public List<Ocorrencia> listarTodas(Optional<StatusOcorrencia> status) {
        List<Ocorrencia> todas = repository.findAll();

        // Filtro opcional por status
        if (status.isPresent()) {
            return todas.stream()
                    .filter(o -> o.getStatus() == status.get())
                    .collect(Collectors.toList());
        }
        return todas;
    }

    // READ (By ID)
    public Ocorrencia buscarPorId(Long id) {
        return buscarPorIdOuFalhar(id);
    }

    // DELETE
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Ocorrência não encontrada com o ID: " + id);
        }
        repository.deleteById(id);
    }

    // --- REGRA DE NEGÓCIO: ENCERRAR OCORRÊNCIA ---
    public Ocorrencia encerrar(Long id) {
        Ocorrencia ocorrencia = buscarPorIdOuFalhar(id);

        // Regra: Não se pode fechar uma ocorrência já fechada
        if (ocorrencia.getStatus() == StatusOcorrencia.FECHADO) {
            throw new BusinessRuleException("Esta ocorrência já está fechada.");
        }

        // Atualiza o status e a data de fechamento
        ocorrencia.setStatus(StatusOcorrencia.FECHADO);
        ocorrencia.setDataFechamento(LocalDateTime.now());

        return repository.save(ocorrencia); // Salva a atualização
    }
}