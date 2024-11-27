package br.grupointegrado.educacional.controller;

import br.grupointegrado.educacional.dto.*;
import br.grupointegrado.educacional.model.*;
import br.grupointegrado.educacional.repository.AlunoRepository;
import br.grupointegrado.educacional.repository.CursoRepository;
import br.grupointegrado.educacional.repository.MatriculaRepository;
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

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

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


    @PostMapping("/{cursoId}")
    public ResponseEntity<TurmaResponseDTO> addTurma(@PathVariable Integer cursoId,
                                                     @RequestBody Turma turma) {

        Curso curso = this.cursoRepository.findById(cursoId)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        turma.setCurso(curso);
        Turma turmaSalva = this.repository.save(turma);


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


    @GetMapping("/matricula")
    public ResponseEntity<List<Matricula>> getMatriculas() {

        return ResponseEntity.ok(this.matriculaRepository.findAll());

    }


    @GetMapping("/matricula/{id}")
    public ResponseEntity<Matricula> findMatriculaById(@PathVariable Integer id) {

        Matricula matricula = this.matriculaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matricula não encontrado"));

        return ResponseEntity.ok(matricula);

    }


    @PostMapping("/matricula/{turmaId}")
    public ResponseEntity<Matricula> addMatriculaAluno( @RequestBody Integer alunoId,
                                                        @PathVariable Integer turmaId) {

        Turma turma = this.repository.findById(turmaId)
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrado"));

        Aluno aluno = this.alunoRepository.findById(alunoId)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));

        Matricula matricula = new Matricula();

        matricula.setTurma(turma);
        matricula.setAluno(aluno);

        return  ResponseEntity.ok(this.matriculaRepository.save(matricula));

    }

//
//    @PutMapping("/matricula/{id}")
//    public ResponseEntity<Matricula> updateMatricula(@PathVariable Integer id,
//                                                     @RequestBody Integer alunoId,
//                                                     @RequestBody Integer turmaId) {
//
//        Matricula matricula = this.matriculaRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Matricula não encontrado"));
//
//        Turma turma = this.repository.findById(turmaId)
//                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrado"));
//
//        Aluno aluno = this.alunoRepository.findById(alunoId)
//                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));
//
//
//        matricula.setTurma(turma);
//        matricula.setAluno(aluno);
//
//        return ResponseEntity.ok(this.matriculaRepository.save(matricula));
//
//    }


    @DeleteMapping("/matricula/{id}")
    public ResponseEntity<Void> deleteMatricula(@PathVariable Integer id) {

        Matricula matricula = this.matriculaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matricula não encontrado"));

        this.matriculaRepository.delete(matricula);
        return  ResponseEntity.noContent().build();

    }

}
