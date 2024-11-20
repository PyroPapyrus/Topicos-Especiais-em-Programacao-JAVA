package br.grupointegrado.educacional.dto;

public record DisciplinaSimplificada2DTO(
        Integer id,
        String nome,
        String codigo,
        Integer professorId,
        String professorNome
) {}
