package br.grupointegrado.educacional.controller;

import br.grupointegrado.educacional.dto.CursoRequestDTO;
import br.grupointegrado.educacional.dto.TurmaRequestDTO;
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
    public ResponseEntity<List<Curso>> findAll(){
//        return this.repository.findAll();

        return ResponseEntity.ok(this.repository.findAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> findById(@PathVariable Integer id) {

        Curso curso = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        return ResponseEntity.ok(curso);

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
    public ResponseEntity<Curso> update(@PathVariable Integer id, @RequestBody CursoRequestDTO dto) {

        Curso curso = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        curso.setNome(dto.nome());
        curso.setCodigo(dto.codigo());
        curso.setCarga_horaria(dto.carga_horaria());

        return  ResponseEntity.ok(this.repository.save(curso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {

        Curso curso = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));


        this.repository.delete(curso);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/add-turma")
    public ResponseEntity<Curso> addTurma(@PathVariable Integer id,
                                          @RequestBody Turma turma) {
        Curso curso = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        turma.setCurso(curso);
        this.turmaRepository.save(turma);

        return ResponseEntity.ok(curso);
    }

}
