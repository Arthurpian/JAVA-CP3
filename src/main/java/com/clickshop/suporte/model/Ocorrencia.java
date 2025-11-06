package com.clickshop.suporte.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data // Anotação do Lombok (cria getters, setters, etc.)
public class Ocorrencia {

    private Long id;
    private String titulo;
    private String descricao;
    private String solicitante;
    private StatusOcorrencia status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataFechamento;

    public Ocorrencia(Long id, String titulo, String descricao, String solicitante) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.solicitante = solicitante;
        this.status = StatusOcorrencia.ABERTO; // Regra de negócio: sempre começa como ABERTO
        this.dataCriacao = LocalDateTime.now();
    }
}