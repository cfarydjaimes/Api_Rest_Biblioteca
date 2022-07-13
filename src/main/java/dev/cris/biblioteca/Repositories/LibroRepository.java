package dev.cris.biblioteca.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.cris.biblioteca.Models.Libro;

public interface LibroRepository extends JpaRepository<Libro, Integer>{
    
 
}
