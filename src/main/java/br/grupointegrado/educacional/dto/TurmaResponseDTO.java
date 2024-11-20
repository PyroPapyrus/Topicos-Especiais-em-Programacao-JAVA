package br.grupointegrado.educacional.dto;

public record TurmaResponseDTO(
        Integer id,
        Integer ano,
        Integer semestre,
        CursoSimplificadoDTO curso
) {}
