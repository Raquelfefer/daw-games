package com.daw.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.Juego;
import com.daw.persistence.entities.enums.Tipo;
import com.daw.persistence.repositories.JuegoRepository;
import com.daw.services.exceptions.JuegoException;
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
		
		if(juego.getFechaLanzamiento() == null) {
			juego.setFechaLanzamiento(LocalDate.now());
		}
		juego.setCompletado(false);
		
		return this.juegoRepository.save(juego);
	}
	
	//Modificar un juego
	public Juego update(int idJuego, Juego juego) {
		if(idJuego != juego.getId()) {
			throw new JuegoException("El ID del path ("+ idJuego +") y el id del body ("+ juego.getId() +") no coinciden");
		}
		if(!this.juegoRepository.existsById(idJuego)) {
			throw new JuegoNotFoundException("No existe el juego con ID: " + idJuego);
		}
		if(juego.getCompletado() != null) {
			throw new JuegoException("No se permite modificar el completado del juego.");
		}
		
		Juego juegoBD = this.findById(juego.getId());
		juegoBD.setNombre(juego.getNombre());
		juegoBD.setGenero(juego.getGenero());
		juegoBD.setPlataformas(juego.getPlataformas());
		juegoBD.setPrecio(juego.getPrecio());
		juegoBD.setDescargas(juego.getDescargas());
		juegoBD.setFechaLanzamiento(juego.getFechaLanzamiento());
		
		return this.juegoRepository.save(juegoBD);
	}
	
	//Borrar un juego
	public boolean delete(int idJuego) {
		boolean result = false;
		
		if(this.juegoRepository.existsById(idJuego)) {
			this.juegoRepository.deleteById(idJuego);
			result = true;
		}
		
		return result;
	}
	
	//Completar juego
	public Juego completarJuego (int idJuego) {
		if(!this.juegoRepository.existsById(idJuego)) {
			throw new JuegoNotFoundException("No existe el juego con ID: " + idJuego);
		}
		if(this.juegoRepository.findById(idJuego).get().getCompletado()) {
			throw new JuegoException("Este juego ya está como completado.");
		}
		Juego juego = this.findById(idJuego);
		juego.setCompletado(true);
		
		return this.juegoRepository.save(juego);
	}
	
	//Descompletar juego
	public Juego descompletarJuego (int idJuego) {
		if(!this.juegoRepository.existsById(idJuego)) {
			throw new JuegoNotFoundException("No existe el juego con ID: " + idJuego);
		}
		if(this.juegoRepository.findById(idJuego).get().getCompletado() == false) {
			throw new JuegoException("Este juego ya está sin completar.");
		}
		Juego juego = this.findById(idJuego);
		juego.setCompletado(false);
		
		return this.juegoRepository.save(juego);
	}
	
	//Buscar juego por género
	public List<Juego> buscarJuegoGenero(String genero){
		return this.juegoRepository.findAll().stream()
				.filter(t -> t.getGenero().contains(genero))
				.collect(Collectors.toList());
	}
	
	//Buscar juego por nombre
	public List<Juego> buscarJuegoNombe(String nombre){
		return this.juegoRepository.findAll().stream()
				.filter(t -> t.getNombre().contains(nombre))
				.collect(Collectors.toList());
	}
	
	//Buscar juegos por plataforma
	public List<Juego> buscarJuegoPlataforma(String plataforma){
		return this.juegoRepository.findAll().stream()
				.filter(t-> t.getPlataformas().contains(plataforma))
				.collect(Collectors.toList());
	}
	
	//Obtener las expansiones
	public List<Juego> expansiones(){
		return this.juegoRepository.findAll().stream()
				.filter(t -> t.getTipo() == (Tipo.EXPANSION))
				.collect(Collectors.toList());
	}
	
	//Obtener los DLCs
	public List<Juego> dlc(){
		return this.juegoRepository.findAll().stream()
				.filter(t -> t.getTipo() == (Tipo.DLC))
				.collect(Collectors.toList());
	}
	
	//Obtener los juegos base
	public List<Juego> base(){
		return this.juegoRepository.findAll().stream()
				.filter(t -> t.getTipo() == (Tipo.BASE))
				.collect(Collectors.toList());
	}
	
	//Juegos en un rango de precio
	public List<Juego> precio(double precioBajo, double precioAlto){
		return this.juegoRepository.findAll().stream()
				.filter(t -> t.getPrecio() >= precioBajo && t.getPrecio() <= precioAlto)
				.collect(Collectors.toList());
	}
	
	//Juegos con mas de 1000 descargas
	public List<Juego> milDescargas(){
		return this.juegoRepository.findAll().stream()
				.filter(t -> t.getDescargas() >= 1000)
				.collect(Collectors.toList());
	}
	
	
	
	
	
}
