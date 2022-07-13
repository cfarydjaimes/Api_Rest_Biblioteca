package dev.cris.biblioteca.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import dev.cris.biblioteca.Models.Biblioteca;


public interface BibliotecaRepository extends JpaRepository<Biblioteca, Integer> {

}
