package br.grupointegrado.educacional.dto;

public record DisciplinaResponseDTO(
        Integer id,
        String nome,
        String codigo,
        String cursoNome,
        String professorNome
) {}
