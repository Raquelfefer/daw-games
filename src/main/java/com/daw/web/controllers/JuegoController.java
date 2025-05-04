package com.daw.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daw.persistence.entities.Juego;
import com.daw.services.JuegoService;
import com.daw.services.exceptions.JuegoException;
import com.daw.services.exceptions.JuegoNotFoundException;

@RestController
@RequestMapping("/juegos")
public class JuegoController {

	@Autowired
	private JuegoService juegoService;
	
	//Obtener todos los juegos
	@GetMapping
	public ResponseEntity<List<Juego>> list() {
		return ResponseEntity.status(HttpStatus.OK).body(this.juegoService.findAll());
	}
	
	//Obtener el juego por su ID
	@GetMapping("/{idJuego}")
	public ResponseEntity<?> findById(@PathVariable int idJuego){
		try {
			return ResponseEntity.ok(this.juegoService.findById(idJuego));
		} catch (JuegoNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
	
	//Crear un juego
	@PostMapping
	public ResponseEntity<Juego> create(@RequestBody Juego juego) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.juegoService.create(juego));
	}
	
	//Modificar un juego
	@PutMapping("/{idJuego}")
	public ResponseEntity<?> update(@PathVariable int idJuego, @RequestBody Juego juego) {
		try {
			return ResponseEntity.ok(this.juegoService.update(idJuego, juego));
		} catch (JuegoNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (JuegoException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
		}
	}
	
	//Borrar un juego
	@DeleteMapping("/{idJuego}")
	public ResponseEntity<?> delete(@PathVariable int idJuego) {
		try {
			this.juegoService.delete(idJuego);
			return ResponseEntity.ok("El juego con ID("+ idJuego +") ha sido borrada correctamente. " );
		} catch (JuegoNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}

	}
	
	//Marcar juego como completado
	@PutMapping("/{idJuego}/completar")
	public ResponseEntity<?> completarJuego(@PathVariable int idJuego){
		try {
			return ResponseEntity.ok(this.juegoService.completarJuego(idJuego));
		}catch (JuegoNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()); 
		}catch (JuegoException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
		}
	}
	
	//Desmarcar juego como completado
	@PutMapping("/{idJuego}/descompletar")
	public ResponseEntity<?> descompletarJuego(@PathVariable int idJuego){
		try {
			return ResponseEntity.ok(this.juegoService.descompletarJuego(idJuego));
		}catch (JuegoNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()); 
		}catch (JuegoException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
		}
	}
	
	//Buscar juegos por género
	@GetMapping("/genero")
	public ResponseEntity<List<Juego>> buscarGenero(@RequestParam String genero){
		return ResponseEntity.status(HttpStatus.OK).body(this.juegoService.buscarJuegoGenero(genero));
	}
	
	//Busar juegos por nombre
	@GetMapping("/nombre")
	public ResponseEntity<List<Juego>> buscarNombre(@RequestParam String nombre){
		return ResponseEntity.status(HttpStatus.OK).body(this.juegoService.buscarJuegoNombe(nombre));
	}
	
	//Buscar juegos por plataforma
	@GetMapping("/plataforma")
	public ResponseEntity<List<Juego>> buscarPlataforma(@RequestParam String plataforma){
		return ResponseEntity.status(HttpStatus.OK).body(this.juegoService.buscarJuegoPlataforma(plataforma));
	}
	
	//Obtener las expansiones
	@GetMapping("/expansiones")
	public ResponseEntity<List<Juego>> expansiones() {
		return ResponseEntity.status(HttpStatus.OK).body(this.juegoService.expansiones());
	}
	
	//Obtener los DLCs
	@GetMapping("/dlc")
	public ResponseEntity<List<Juego>> dlc() {
		return ResponseEntity.status(HttpStatus.OK).body(this.juegoService.dlc());
	}
	
	//Obtener los juegos base
	@GetMapping("/base")
	public ResponseEntity<List<Juego>> base() {
		return ResponseEntity.status(HttpStatus.OK).body(this.juegoService.base());
	}
	
	//Juegos en un rango de precio
	@GetMapping("/precio")
	public ResponseEntity<List<Juego>> precio(@RequestParam double precioBajo, double precioAlto) {
		return ResponseEntity.status(HttpStatus.OK).body(this.juegoService.precio(precioBajo, precioAlto));
	}
	
	//Juegos con mas de 1000 descargas
	@GetMapping("/mildescargas")
	public ResponseEntity<List<Juego>> milDescargas() {
		return ResponseEntity.status(HttpStatus.OK).body(this.juegoService.milDescargas());
	}
	
	
	
	
	
	
}
