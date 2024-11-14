package br.grupointegrado.educacional.controller;

import br.grupointegrado.educacional.dto.ProfessorRequestDTO;
import br.grupointegrado.educacional.dto.TurmaRequestDTO;
import br.grupointegrado.educacional.model.Curso;
import br.grupointegrado.educacional.model.Professor;
import br.grupointegrado.educacional.model.Turma;
import br.grupointegrado.educacional.repository.CursoRepository;
import br.grupointegrado.educacional.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/turmas")
public class TurmaController {

    @Autowired
    private TurmaRepository repository;

    private CursoRepository cursoRepository;

    @GetMapping
    public ResponseEntity<List<Turma>> findAll() {

        return ResponseEntity.ok(this.repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turma> findById(@PathVariable Integer id) {

        Turma turma = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrado"));

        return ResponseEntity.ok(turma);
    }




}
