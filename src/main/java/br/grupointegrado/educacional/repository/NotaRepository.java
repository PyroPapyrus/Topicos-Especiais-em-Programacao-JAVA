package br.grupointegrado.educacional.repository;

import br.grupointegrado.educacional.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotaRepository extends JpaRepository<Nota, Integer> {
}
