package br.grupointegrado.educacional.controller;

import br.grupointegrado.educacional.dto.*;
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
    public ResponseEntity<List<TurmaResponseDTO>> findAll() {
        List<TurmaResponseDTO> turmas = this.repository.findAll().stream()
                .map(turma -> new TurmaResponseDTO(
                        turma.getId(),
                        turma.getAno(),
                        turma.getSemestre(),
                        new CursoSimplificadoDTO(
                                turma.getCurso().getId(),
                                turma.getCurso().getNome(),
                                turma.getCurso().getCodigo(),
                                turma.getCurso().getCarga_horaria()
                        )
                ))
                .toList();

        return ResponseEntity.ok(turmas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurmaResponseDTO> findById(@PathVariable Integer id) {

        Turma turma = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada"));

        TurmaResponseDTO turmaResponse = new TurmaResponseDTO(
                turma.getId(),
                turma.getAno(),
                turma.getSemestre(),
                new CursoSimplificadoDTO(
                        turma.getCurso().getId(),
                        turma.getCurso().getNome(),
                        turma.getCurso().getCodigo(),
                        turma.getCurso().getCarga_horaria()
                )
        );

        return ResponseEntity.ok(turmaResponse);
    }


    @PutMapping("/{id}")
    public ResponseEntity<TurmaResponseDTO> update(@PathVariable Integer id, @RequestBody TurmaRequestDTO dto) {

        Turma turma = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrado"));

        turma.setAno(dto.ano());
        turma.setSemestre(dto.semestre());

        Turma turmaAtualizada = this.repository.save(turma);

        TurmaResponseDTO turmaResponse = new TurmaResponseDTO(
                turmaAtualizada.getId(),
                turmaAtualizada.getAno(),
                turmaAtualizada.getSemestre(),
                new CursoSimplificadoDTO(
                        turmaAtualizada.getCurso().getId(),
                        turmaAtualizada.getCurso().getNome(),
                        turmaAtualizada.getCurso().getCodigo(),
                        turmaAtualizada.getCurso().getCarga_horaria()
                )
        );


        return  ResponseEntity.ok(turmaResponse);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {

        Turma turma = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrado"));

        this.repository.delete(turma);
        return ResponseEntity.noContent().build();

    }



}
