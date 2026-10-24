package co.edu.unbosque.avistamientos.avistamientos_api.controller;

import co.edu.unbosque.avistamientos.avistamientos_api.model.Avistamiento;
import co.edu.unbosque.avistamientos.avistamientos_api.repository.AvistamientoRepository;

import java.time.LocalDate;
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
	public ResponseEntity<?> registrar(@RequestBody Avistamiento nuevo) {
		try {
			// Regla de negocio: Validar antes de guardar
			ValidadorDeAvistamiento.validar(nuevo, LocalDate.now());
			Avistamiento guardado = repository.save(nuevo);
			return ResponseEntity.status(201).body(guardado); // (201 creado)
		} catch (IllegalArgumentException e) {
			// Si no es válido, responde 400 con el mensaje que falló
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Avistamiento datos) {
		return repository.findById(id).map(avistamiento -> {
			try {
				// Regla de negocio: Validar datos entrantes
				ValidadorDeAvistamiento.validar(datos, LocalDate.now());

				avistamiento.setEspecie(datos.getEspecie());
				avistamiento.setLugar(datos.getLugar());
				avistamiento.setFecha(datos.getFecha());
				avistamiento.setObservador(datos.getObservador());
				repository.save(avistamiento);

				return ResponseEntity.ok((Object) avistamiento); // (200 actualizado)
			} catch (IllegalArgumentException e) {
				// Si no es válido, responde 400 con el mensaje que falló
				return ResponseEntity.badRequest().body((Object) e.getMessage());
			}
		}).orElse(ResponseEntity.notFound().build()); // (404 si no existe)
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		return repository.findById(id).map(avistamiento -> {
			repository.delete(avistamiento);
			return ResponseEntity.noContent().<Void>build();
		}).orElse(ResponseEntity.notFound().build());
	}

}