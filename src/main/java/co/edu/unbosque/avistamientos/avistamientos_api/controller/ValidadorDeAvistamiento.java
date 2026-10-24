package co.edu.unbosque.avistamientos.avistamientos_api.controller;

import java.time.LocalDate;


import co.edu.unbosque.avistamientos.avistamientos_api.model.Avistamiento;

public class ValidadorDeAvistamiento {
	private LocalDate fecha;

	public static void validar(Avistamiento a, LocalDate hoy) {
		if (a.getEspecie() == null || a.getEspecie().length() < 3 || a.getEspecie().length() > 50) {
			throw new IllegalArgumentException("Especie inválida");
		}
		if (a.getFecha() == null || a.getFecha().isAfter(hoy)) {
		    throw new IllegalArgumentException("Fecha inválida");
	
	
		}
		if (a.getLugar() == null || a.getLugar().isBlank()) {
			throw new IllegalArgumentException("Lugar vacío");
		}
		if (a.getObservador() == null || a.getObservador().isBlank()) {
			throw new IllegalArgumentException("Observador vacío");
		}
	}

}
