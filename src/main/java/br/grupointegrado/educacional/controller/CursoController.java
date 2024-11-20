package br.grupointegrado.educacional.controller;

import br.grupointegrado.educacional.dto.*;
import br.grupointegrado.educacional.model.Curso;
import br.grupointegrado.educacional.model.Turma;
import br.grupointegrado.educacional.repository.CursoRepository;
import br.grupointegrado.educacional.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoRepository repository;

    @Autowired
    private TurmaRepository turmaRepository;

    @GetMapping
    public ResponseEntity<List<CursoResponseDTO>> findAll() {
        List<CursoResponseDTO> cursos = this.repository.findAll().stream()
                .map(curso -> new CursoResponseDTO(
                        curso.getId(),
                        curso.getNome(),
                        curso.getCodigo(),
                        curso.getCarga_horaria(),
                        curso.getDisciplinas().stream()
                                .map(disciplina -> new DisciplinaSimplificada2DTO(
                                        disciplina.getId(),
                                        disciplina.getNome(),
                                        disciplina.getCodigo(),
                                        disciplina.getProfessor().getId(),
                                        disciplina.getProfessor().getNome()
                                ))
                                .toList()
                ))
                .toList();

        return ResponseEntity.ok(cursos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> findById(@PathVariable Integer id) {
        Curso curso = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        CursoResponseDTO cursoResponse = new CursoResponseDTO(
                curso.getId(),
                curso.getNome(),
                curso.getCodigo(),
                curso.getCarga_horaria(),
                curso.getDisciplinas().stream()
                        .map(disciplina -> new DisciplinaSimplificada2DTO(
                                disciplina.getId(),
                                disciplina.getNome(),
                                disciplina.getCodigo(),
                                disciplina.getProfessor().getId(),
                                disciplina.getProfessor().getNome()
                        ))
                        .toList()
        );

        return ResponseEntity.ok(cursoResponse);
    }


    @PostMapping
    public ResponseEntity<Curso> save(@RequestBody CursoRequestDTO dto) {
        Curso curso = new Curso();
        curso.setNome(dto.nome());
        curso.setCodigo(dto.codigo());
        curso.setCarga_horaria(dto.carga_horaria());

        return  ResponseEntity.ok(this.repository.save(curso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> update(
            @PathVariable Integer id,
            @RequestBody CursoRequestDTO dto) {

        Curso curso = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        curso.setNome(dto.nome());
        curso.setCodigo(dto.codigo());
        curso.setCarga_horaria(dto.carga_horaria());

        Curso cursoAtualizado = this.repository.save(curso);

        CursoResponseDTO cursoResponse = new CursoResponseDTO(
                cursoAtualizado.getId(),
                cursoAtualizado.getNome(),
                cursoAtualizado.getCodigo(),
                cursoAtualizado.getCarga_horaria(),
                cursoAtualizado.getDisciplinas().stream()
                        .map(disciplina -> new DisciplinaSimplificada2DTO(
                                disciplina.getId(),
                                disciplina.getNome(),
                                disciplina.getCodigo(),
                                disciplina.getProfessor().getId(),
                                disciplina.getProfessor().getNome()
                        ))
                        .toList()
        );

        return ResponseEntity.ok(cursoResponse);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {

        Curso curso = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));


        this.repository.delete(curso);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/add-turma")
    public ResponseEntity<TurmaResponseDTO> addTurma(@PathVariable Integer id,
                                                     @RequestBody Turma turma) {
        Curso curso = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        turma.setCurso(curso);
        Turma turmaSalva = this.turmaRepository.save(turma);

        TurmaResponseDTO turmaResponse = new TurmaResponseDTO(
                turmaSalva.getId(),
                turma.getAno(),
                turma.getSemestre(),
                new CursoSimplificadoDTO(
                        curso.getId(),
                        curso.getNome(),
                        curso.getCodigo(),
                        curso.getCarga_horaria()
                )
        );


        return ResponseEntity.ok(turmaResponse);
    }

}
