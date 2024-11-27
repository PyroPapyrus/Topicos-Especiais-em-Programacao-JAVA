package br.grupointegrado.educacional.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "notas")
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private BigDecimal nota;

    @Column(name = "data_lancamento")
    private LocalDate dataLancamento;

    @ManyToOne
    @JoinColumn(name = "matricula_id", referencedColumnName = "id")
    @JsonIgnoreProperties("turma")
    private Matricula matricula;

    @ManyToOne
    @JoinColumn(name = "disciplina_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"curso", "professor", "nota"})
    private Disciplina disciplina;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }
}
