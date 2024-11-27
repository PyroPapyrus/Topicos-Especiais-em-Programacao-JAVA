package br.grupointegrado.educacional.controller;

import br.grupointegrado.educacional.dto.AlunoRequestDTO;
import br.grupointegrado.educacional.dto.NotaRequestDTO;
import br.grupointegrado.educacional.model.Aluno;
import br.grupointegrado.educacional.model.Disciplina;
import br.grupointegrado.educacional.model.Matricula;
import br.grupointegrado.educacional.model.Nota;
import br.grupointegrado.educacional.repository.AlunoRepository;
import br.grupointegrado.educacional.repository.DisciplinaRepository;
import br.grupointegrado.educacional.repository.MatriculaRepository;
import br.grupointegrado.educacional.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository repository;

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @GetMapping
    public ResponseEntity<List<Aluno>> findAll(){

        return ResponseEntity.ok(this.repository.findAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> findById(@PathVariable Integer id) {

        Aluno aluno = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));

        return ResponseEntity.ok(aluno);

    }

    @PostMapping
    public ResponseEntity<Aluno> save(@RequestBody AlunoRequestDTO dto) {
        Aluno aluno = new Aluno();
        aluno.setNome(dto.nome());
        aluno.setEmail(dto.email());
        aluno.setMatricula(dto.matricula());
        aluno.setData_nascimento(dto.data_nascimento());

        return  ResponseEntity.ok(this.repository.save(aluno));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> update(@PathVariable Integer id, @RequestBody AlunoRequestDTO dto) {

        Aluno aluno = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));

        aluno.setNome(dto.nome());
        aluno.setEmail(dto.email());
        aluno.setMatricula(dto.matricula());
        aluno.setData_nascimento(dto.data_nascimento());

        return  ResponseEntity.ok(this.repository.save(aluno));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {

        Aluno aluno = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));


        this.repository.delete(aluno);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{matriculaId}/{disciplinaId}/add-nota")
    public ResponseEntity<Nota> addNota(@PathVariable Integer matriculaId,
                                        @PathVariable Integer disciplinaId,
                                        @RequestBody NotaRequestDTO dto){
        Matricula matricula = this.matriculaRepository.findById(matriculaId)
                .orElseThrow(() -> new IllegalArgumentException("Matricula não encontrada"));

        Disciplina disciplina = this.disciplinaRepository.findById(disciplinaId)
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));

        Nota nota = new Nota();

        nota.setNota(dto.nota());
        nota.setDataLancamento(dto.dataLancamento());

        nota.setMatricula(matricula);
        nota.setDisciplina(disciplina);

        return ResponseEntity.ok(this.notaRepository.save(nota));
    }

    @GetMapping("/nota")
    public ResponseEntity<List<Nota>> findAllNota(){

        return ResponseEntity.ok(this.notaRepository.findAll());

    }

    @GetMapping("/nota/{id}")
    public ResponseEntity<Nota> findNotaById(@PathVariable Integer id) {

        Nota nota = this.notaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("nota não encontrado"));

        return ResponseEntity.ok(nota);

    }

    @DeleteMapping("/nota/{id}")
    public ResponseEntity<Void> deleteNota(@PathVariable Integer id) {

        Nota nota = this.notaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nota não encontrado"));


        this.notaRepository.delete(nota);
        return ResponseEntity.noContent().build();
    }
}
