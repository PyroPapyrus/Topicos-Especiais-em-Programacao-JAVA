package br.grupointegrado.educacional.dto;

import java.util.List;

public record ProfessorResponseDTO(
        Integer id,
        String nome,
        String email,
        String telefone,
        String especialidade,
        List<DisciplinaSimplificadaDTO> disciplinas
) {
}
