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
import dev.cris.biblioteca.Repositories.BibliotecaRepository;

@RestController
@RequestMapping("/api/biblioteca")
public class BibliotecaController {

    @Autowired
    private BibliotecaRepository bibliotecaRepository;


    @PostMapping
    public ResponseEntity<Biblioteca> guardarBiblioteca(@Valid @RequestBody Biblioteca biblioteca) {
        Biblioteca bibliotecaGuardada = bibliotecaRepository.save(biblioteca);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(bibliotecaGuardada.getId()).toUri();
        return ResponseEntity.created(location).body(bibliotecaGuardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Biblioteca> actualizarBiblioteca(@PathVariable int id,
            @Valid @RequestBody Biblioteca biblioteca) {
        Optional<Biblioteca> bibliotecaTemp = bibliotecaRepository.findById(id);
        if (!bibliotecaTemp.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        biblioteca.setId(bibliotecaTemp.get().getId());
        bibliotecaRepository.save(biblioteca);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
	public ResponseEntity<Biblioteca> eliminarBiblioteca(@PathVariable int id){
		Optional<Biblioteca> bibliotecaOptional = bibliotecaRepository.findById(id);
		
		if(!bibliotecaOptional.isPresent()){
			return ResponseEntity.unprocessableEntity().build();
		}
		
		bibliotecaRepository.delete(bibliotecaOptional.get());
		return ResponseEntity.noContent().build();
	}

    @GetMapping("/{id}")
    public ResponseEntity<Biblioteca> obtenerBibliotecaPorId(@PathVariable int id) {
        Optional<Biblioteca> bibliotecaTemp = bibliotecaRepository.findById(id);
        if (!bibliotecaTemp.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(bibliotecaTemp.get());
    }
}
