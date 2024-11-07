package br.grupointegrado.educacional.dto;

import java.sql.Date;

public record AlunoRequestDTO(String nome, String email, String matricula, Date data_nascimento) {
}
