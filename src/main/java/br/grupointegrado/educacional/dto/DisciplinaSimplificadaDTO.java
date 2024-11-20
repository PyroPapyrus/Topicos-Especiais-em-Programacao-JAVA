package br.grupointegrado.educacional.dto;

public record DisciplinaSimplificadaDTO(
        Integer id,
        String nome,
        String codigo,
        Integer cursoId,
        String cursoNome,
        String cursoCodigo
) {
}
