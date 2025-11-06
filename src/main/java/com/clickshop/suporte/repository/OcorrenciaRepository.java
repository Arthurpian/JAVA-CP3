package com.clickshop.suporte.repository;

import com.clickshop.suporte.model.Ocorrencia;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository // Define que esta classe é um "Bean" de repositório
public class OcorrenciaRepository {

    // Simula nosso banco de dados em memória
    private final Map<Long, Ocorrencia> ocorrenciasDB = new ConcurrentHashMap<>();

    // Gerador de IDs
    private final AtomicLong idSequence = new AtomicLong(0);

    public Ocorrencia save(Ocorrencia ocorrencia) {
        if (ocorrencia.getId() == null) {
            // Criação
            Long id = idSequence.incrementAndGet();
            ocorrencia.setId(id);
        }
        // Atualização ou Inserção
        ocorrenciasDB.put(ocorrencia.getId(), ocorrencia);
        return ocorrencia;
    }

    public Optional<Ocorrencia> findById(Long id) {
        return Optional.ofNullable(ocorrenciasDB.get(id));
    }

    public List<Ocorrencia> findAll() {
        return List.copyOf(ocorrenciasDB.values());
    }

    public void deleteById(Long id) {
        ocorrenciasDB.remove(id);
    }

    public boolean existsById(Long id) {
        return ocorrenciasDB.containsKey(id);
    }
}