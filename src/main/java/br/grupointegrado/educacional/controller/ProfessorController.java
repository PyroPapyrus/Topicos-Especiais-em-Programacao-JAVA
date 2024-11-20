package br.grupointegrado.educacional.controller;


import br.grupointegrado.educacional.dto.DisciplinaRequestDTO;
import br.grupointegrado.educacional.dto.DisciplinaSimplificadaDTO;
import br.grupointegrado.educacional.dto.ProfessorRequestDTO;
import br.grupointegrado.educacional.dto.ProfessorResponseDTO;
import br.grupointegrado.educacional.model.Disciplina;
import br.grupointegrado.educacional.model.Professor;
import br.grupointegrado.educacional.repository.DisciplinaRepository;
import br.grupointegrado.educacional.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professores")
public class ProfessorController {

    @Autowired
    private ProfessorRepository repository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @GetMapping
    public ResponseEntity<List<ProfessorResponseDTO>> findAll() {
        List<ProfessorResponseDTO> response = this.repository.findAll()
                .stream()
                .map(professor -> {
                    List<DisciplinaSimplificadaDTO> disciplinas = professor.getDisciplinas()
                            .stream()
                            .map(disciplina -> new DisciplinaSimplificadaDTO(
                                    disciplina.getId(),
                                    disciplina.getNome(),
                                    disciplina.getCodigo(),
                                    disciplina.getCurso().getId(),
                                    disciplina.getCurso().getNome(),
                                    disciplina.getCurso().getCodigo()
                            ))
                            .toList();

                    return new ProfessorResponseDTO(
                            professor.getId(),
                            professor.getNome(),
                            professor.getEmail(),
                            professor.getTelefone(),
                            professor.getEspecialidade(),
                            disciplinas
                    );
                })
                .toList();

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> findById(@PathVariable Integer id) {
        Professor professor = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));

        List<DisciplinaSimplificadaDTO> disciplinas = professor.getDisciplinas()
                .stream()
                .map(disciplina -> new DisciplinaSimplificadaDTO(
                        disciplina.getId(),
                        disciplina.getNome(),
                        disciplina.getCodigo(),
                        disciplina.getCurso().getId(),
                        disciplina.getCurso().getNome(),
                        disciplina.getCurso().getCodigo()
                ))
                .toList();

        ProfessorResponseDTO response = new ProfessorResponseDTO(
                professor.getId(),
                professor.getNome(),
                professor.getEmail(),
                professor.getTelefone(),
                professor.getEspecialidade(),
                disciplinas
        );

        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<Professor> save(@RequestBody ProfessorRequestDTO dto) {
        Professor professor = new Professor();
        professor.setNome(dto.nome());
        professor.setEmail(dto.email());
        professor.setTelefone(dto.telefone());
        professor.setEspecialidade(dto.especialidade());

        return  ResponseEntity.ok(this.repository.save(professor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professor> update(@PathVariable Integer id, @RequestBody ProfessorRequestDTO dto) {

        Professor professor = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));

        professor.setNome(dto.nome());
        professor.setEmail(dto.email());
        professor.setTelefone(dto.telefone());
        professor.setEspecialidade(dto.especialidade());

        return  ResponseEntity.ok(this.repository.save(professor));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {

        Professor professor = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));


        this.repository.delete(professor);
        return ResponseEntity.noContent().build();
    }

}
