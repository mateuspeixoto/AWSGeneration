package com.generationaws.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generationaws.demo.entities.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

}
