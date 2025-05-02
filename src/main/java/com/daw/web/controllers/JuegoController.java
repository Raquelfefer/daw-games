package com.daw.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daw.persistence.entities.Juego;
import com.daw.services.JuegoService;
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
	@GetMapping("{/idJuego")
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
}
