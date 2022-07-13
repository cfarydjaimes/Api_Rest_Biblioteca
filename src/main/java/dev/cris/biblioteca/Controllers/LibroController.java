package dev.cris.biblioteca.Controllers;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import dev.cris.biblioteca.Models.Biblioteca;
import dev.cris.biblioteca.Models.Libro;
import dev.cris.biblioteca.Repositories.BibliotecaRepository;
import dev.cris.biblioteca.Repositories.LibroRepository;

@RestController
@RequestMapping("/api/libro")
public class LibroController {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private BibliotecaRepository bibliotecaRepository;



    @PostMapping
    public ResponseEntity<Libro> guardarLibro(@Valid @RequestBody Libro libro) {
        Optional<Biblioteca> bOptional = bibliotecaRepository.findById(libro.getBiblioteca().getId());
        if (!bOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        libro.setBiblioteca(bOptional.get());
        Libro libroGuardado = libroRepository.save(libro);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(libroGuardado.getId()).toUri();
        return ResponseEntity.created(location).body(libroGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizarLibro(@PathVariable int id, @Valid @RequestBody Libro libro) {
        Optional<Biblioteca> bOptional = bibliotecaRepository.findById(libro.getBiblioteca().getId());
        if (!bOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        Optional<Libro> libroOptional = libroRepository.findById(id);
        if (!libroOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        libro.setBiblioteca(bOptional.get());
        libro.setId(libroOptional.get().getId());
        libroRepository.save(libro);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Libro> eliminarLibro(@PathVariable int id) {
        Optional<Libro> libroOptional = libroRepository.findById(id);
        if (!libroOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        libroRepository.delete(libroOptional.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> obtenerLibroPorId(@PathVariable int id) {

        Optional<Libro> libroOptional = libroRepository.findById(id);
        if (!libroOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(libroOptional.get());
    }

}
