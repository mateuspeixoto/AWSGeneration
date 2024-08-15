package com.generationaws.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generationaws.demo.entities.Aluno;
import com.generationaws.demo.services.AlunoService;

@RestController
@RequestMapping(value = "/alunos")
public class AlunoController {

	private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping
    public ResponseEntity<?> getAllAlunos() {
        try {
            List<Aluno> alunos = alunoService.findAll();
            return ResponseEntity.ok(alunos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar alunos: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> getAlunoById(@PathVariable Long id) {
        try {
            Optional<Aluno> aluno = alunoService.findById(id);
            return aluno.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<?> createAluno(@RequestBody Aluno aluno) {
        try {
            Aluno novoAluno = alunoService.save(aluno);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoAluno);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar aluno: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAluno(@PathVariable Long id, @RequestBody Aluno alunoDetails) {
        try {
            Optional<Aluno> aluno = alunoService.findById(id);
            if (aluno.isPresent()) {
                Aluno updatedAluno = aluno.get();
                updatedAluno.setNotaSegundoSemestre(alunoDetails.getNotaSegundoSemestre());
                updatedAluno.setProfessor(alunoDetails.getProfessor());
                updatedAluno.setSala(alunoDetails.getSala());
                return ResponseEntity.ok(alunoService.save(updatedAluno));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno não encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar aluno: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAluno(@PathVariable Long id) {
        try {
            if (alunoService.findById(id).isPresent()) {
                alunoService.deleteById(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno não encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar aluno: " + e.getMessage());
        }
    }

}
