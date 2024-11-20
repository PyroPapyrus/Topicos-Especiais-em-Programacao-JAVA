package br.grupointegrado.educacional.controller;

import br.grupointegrado.educacional.dto.CursoResponseDTO;
import br.grupointegrado.educacional.dto.DisciplinaRequestDTO;
import br.grupointegrado.educacional.dto.DisciplinaResponseDTO;
import br.grupointegrado.educacional.dto.ProfessorResponseDTO;
import br.grupointegrado.educacional.model.Curso;
import br.grupointegrado.educacional.model.Disciplina;
import br.grupointegrado.educacional.model.Professor;
import br.grupointegrado.educacional.repository.CursoRepository;
import br.grupointegrado.educacional.repository.DisciplinaRepository;
import br.grupointegrado.educacional.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disciplina")
public class DisciplinaController {

    @Autowired
    private DisciplinaRepository repository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private CursoRepository cursoRepository;


    @GetMapping
    public ResponseEntity<List<DisciplinaResponseDTO>> findAll() {
        List<Disciplina> disciplinas = this.repository.findAll();

        List<DisciplinaResponseDTO> response = disciplinas.stream()
                .map(disciplina -> new DisciplinaResponseDTO(
                        disciplina.getId(),
                        disciplina.getNome(),
                        disciplina.getCodigo(),
                        disciplina.getCurso().getNome(),
                        disciplina.getProfessor().getNome()
                ))
                .toList();

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<DisciplinaResponseDTO> findById(@PathVariable Integer id) {

        Disciplina disciplina = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrado"));

        DisciplinaResponseDTO response = new DisciplinaResponseDTO(
                disciplina.getId(),
                disciplina.getNome(),
                disciplina.getCodigo(),
                disciplina.getCurso() != null ? disciplina.getCurso().getNome() : null,
                disciplina.getProfessor() != null ? disciplina.getProfessor().getNome() : null
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<DisciplinaResponseDTO> save(@RequestBody DisciplinaRequestDTO dto) {

        Curso curso = this.cursoRepository.findById(dto.cursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        Professor professor = this.professorRepository.findById(dto.professorId())
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));


        Disciplina disciplina = new Disciplina();
        disciplina.setNome(dto.nome());
        disciplina.setCodigo(dto.codigo());
        disciplina.setCurso(curso);
        disciplina.setProfessor(professor);
        disciplina = this.repository.save(disciplina);

        DisciplinaResponseDTO response = new DisciplinaResponseDTO(
                disciplina.getId(),
                disciplina.getNome(),
                disciplina.getCodigo(),
                curso.getNome(),
                professor.getNome()
        );

        return  ResponseEntity.ok(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<DisciplinaResponseDTO> update(
            @PathVariable Integer id,
            @RequestBody DisciplinaRequestDTO dto) {

        Disciplina disciplina = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));

        disciplina.setNome(dto.nome());
        disciplina.setCodigo(dto.codigo());

        if (dto.cursoId() != null) {
            Curso curso = this.cursoRepository.findById(dto.cursoId())
                    .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));
            disciplina.setCurso(curso);
        }

        if (dto.professorId() != null) {
            Professor professor = this.professorRepository.findById(dto.professorId())
                    .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));
            disciplina.setProfessor(professor);
        }

        disciplina = this.repository.save(disciplina);

        DisciplinaResponseDTO response = new DisciplinaResponseDTO(
                disciplina.getId(),
                disciplina.getNome(),
                disciplina.getCodigo(),
                disciplina.getCurso() != null ? disciplina.getCurso().getNome() : null,
                disciplina.getProfessor() != null ? disciplina.getProfessor().getNome() : null
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {

        Disciplina disciplina = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrado"));


        this.repository.delete(disciplina);
        return ResponseEntity.noContent().build();
    }



}
