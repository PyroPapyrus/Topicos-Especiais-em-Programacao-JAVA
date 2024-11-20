package br.grupointegrado.educacional.dto;

import java.util.List;

public record CursoResponseDTO(
        Integer id,
        String nome,
        String codigo,
        Integer carga_horaria,
        List<DisciplinaSimplificada2DTO> disciplinas
) {}
