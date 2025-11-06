package com.clickshop.suporte.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OcorrenciaInputDTO {

    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @NotBlank(message = "O nome do solicitante é obrigatório")
    private String solicitante;
}