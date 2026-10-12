package co.edu.unbosque.avistamientos.avistamientos_api.controller;

import co.edu.unbosque.avistamientos.avistamientos_api.model.Avistamiento;
import co.edu.unbosque.avistamientos.avistamientos_api.repository.AvistamientoRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avistamientos")
public class AvistamientoController {

	@Autowired
	private AvistamientoRepository repository;

	@GetMapping
	public List<Avistamiento> listar() {
		return repository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Avistamiento> ver(@PathVariable Long id) {
		return repository.findById(id).map(avistamiento -> ResponseEntity.ok(avistamiento))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Avistamiento> registrar(@RequestBody Avistamiento nuevo) {
		if (nuevo.getEspecie() == null || nuevo.getLugar() == null || nuevo.getFecha() == null
				|| nuevo.getObservador() == null) {
			return ResponseEntity.badRequest().build(); // (400 si faltan datos)
		}
		Avistamiento guardado = repository.save(nuevo);
		return ResponseEntity.status(201).body(guardado); // (201 creado)
	}

	@PutMapping("/{id}")
	public ResponseEntity<Avistamiento> actualizar(@PathVariable Long id, @RequestBody Avistamiento datos) {
		return repository.findById(id).map(avistamiento -> {
			avistamiento.setEspecie(datos.getEspecie());
			avistamiento.setLugar(datos.getLugar());
			avistamiento.setFecha(datos.getFecha());
			avistamiento.setObservador(datos.getObservador());
			repository.save(avistamiento);
			return ResponseEntity.ok(avistamiento); // (200 actualizado)
		}).orElse(ResponseEntity.notFound().build()); //(404 si no existe)
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		return repository.findById(id).map(avistamiento -> {
			repository.delete(avistamiento);
			return ResponseEntity.noContent().<Void>build(); 
		}).orElse(ResponseEntity.notFound().build());
	}

}