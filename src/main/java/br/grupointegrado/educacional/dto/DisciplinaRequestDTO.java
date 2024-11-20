package br.grupointegrado.educacional.dto;

import br.grupointegrado.educacional.model.Curso;
import br.grupointegrado.educacional.model.Professor;

public record DisciplinaRequestDTO(
        Integer id,
        String nome,
        String codigo,
        Integer cursoId,
        Integer professorId
) {}
