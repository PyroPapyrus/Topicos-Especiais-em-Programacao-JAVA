package br.grupointegrado.educacional.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record NotaRequestDTO(BigDecimal nota, LocalDate dataLancamento) {
}
