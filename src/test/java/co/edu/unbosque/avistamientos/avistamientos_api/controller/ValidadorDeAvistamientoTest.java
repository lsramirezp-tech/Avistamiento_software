package co.edu.unbosque.avistamientos.avistamientos_api.controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import co.edu.unbosque.avistamientos.avistamientos_api.model.Avistamiento;

public class ValidadorDeAvistamientoTest {

    private Avistamiento avistamiento;
    // Stub de Reloj Fijo: Congelamos el tiempo en una fecha específica
    private final LocalDate HOY_FIJO = LocalDate.of(2026, 10, 23); 

    @BeforeEach
    void prepararDatos() {
        // AAA: Arrange (Preparar) un objeto válido base para cada prueba
        avistamiento = new Avistamiento();
        avistamiento.setEspecie("Loro");
        avistamiento.setFecha(HOY_FIJO.minusDays(1));
        avistamiento.setLugar("Humedal Córdoba");
        avistamiento.setObservador("David");
    }

    @Test
    void especieCon2CaracteresDebeFallarLimiteInferior() {
        avistamiento.setEspecie("Pi");
        
        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            ValidadorDeAvistamiento.validar(avistamiento, HOY_FIJO);
        });
        assertEquals("Especie inválida", excepcion.getMessage());
    }

    @Test
    void especieCon3CaracteresDebePasarLimiteInferior() {
        avistamiento.setEspecie("Pez");
        assertDoesNotThrow(() -> ValidadorDeAvistamiento.validar(avistamiento, HOY_FIJO));
    }

    @Test
    void especieCon4CaracteresDebePasarRepresentativo() {
        avistamiento.setEspecie("Loro");
        assertDoesNotThrow(() -> ValidadorDeAvistamiento.validar(avistamiento, HOY_FIJO));
    }

    @Test
    void especieCon50CaracteresDebePasarLimiteSuperior() {
        avistamiento.setEspecie("A".repeat(50));
        assertDoesNotThrow(() -> ValidadorDeAvistamiento.validar(avistamiento, HOY_FIJO));
    }

    @Test
    void especieCon51CaracteresDebeFallarLimiteSuperior() {
        avistamiento.setEspecie("A".repeat(51));
        
        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            ValidadorDeAvistamiento.validar(avistamiento, HOY_FIJO);
        });
        assertEquals("Especie inválida", excepcion.getMessage());
    }

    @Test
    void fechaAyerDebePasarRepresentativo() {
        avistamiento.setFecha(HOY_FIJO.minusDays(1));
        assertDoesNotThrow(() -> ValidadorDeAvistamiento.validar(avistamiento, HOY_FIJO));
    }

    @Test
    void fechaHoyDebePasarLimite() {
        avistamiento.setFecha(HOY_FIJO);
        assertDoesNotThrow(() -> ValidadorDeAvistamiento.validar(avistamiento, HOY_FIJO));
    }

    @Test
    void fechaMananaDebeFallarLimiteInvalido() {
        avistamiento.setFecha(HOY_FIJO.plusDays(1));
        
        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            ValidadorDeAvistamiento.validar(avistamiento, HOY_FIJO);
        });
        assertEquals("Fecha inválida", excepcion.getMessage());
    }
}