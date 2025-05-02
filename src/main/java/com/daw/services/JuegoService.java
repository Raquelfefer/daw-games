package com.daw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.Juego;
import com.daw.persistence.repositories.JuegoRepository;
import com.daw.services.exceptions.JuegoNotFoundException;

@Service
public class JuegoService {

	@Autowired 
	private JuegoRepository juegoRepository;
	
	
	//Obtener todos los juegos
	public List<Juego> findAll(){
		return this.juegoRepository.findAll();
	}
	
	//Obtener los juegos por su ID
	public Juego findById(int idJuego) {
		return this.juegoRepository.findById(idJuego)
				.orElseThrow(() -> new JuegoNotFoundException("No existe el juego con ID: " + idJuego));
	}
	
	//Crear un juego
	public Juego create(Juego juego) {
		
		
		
	}
}
