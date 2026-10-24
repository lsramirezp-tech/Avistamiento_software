package co.edu.unbosque.avistamientos.avistamientos_api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.edu.unbosque.avistamientos.avistamientos_api.model.Avistamiento;
import co.edu.unbosque.avistamientos.avistamientos_api.repository.AvistamientoRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class AvistamientoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AvistamientoRepository repository;

    @Autowired
    private ObjectMapper objectMapper; // Para convertir objetos a JSON

    private Avistamiento avistamientoBase;

    @BeforeEach
    void limpiarPreparar() {
        repository.deleteAll(); // Limpiar antes de cada prueba
        
        avistamientoBase = new Avistamiento();
        avistamientoBase.setEspecie("Colibri");
        avistamientoBase.setLugar("Jardin Botanico");
        avistamientoBase.setFecha(LocalDate.now().minusDays(2));
        avistamientoBase.setObservador("Laura");
    }

    @Test
    void post_Exito201_Y_VerificarEfectoGet() throws Exception {
        String jsonPost = objectMapper.writeValueAsString(avistamientoBase);

        // Prueba POST (201)
        String respuesta = mockMvc.perform(post("/avistamientos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPost))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.especie", is("Colibri")))
                .andReturn().getResponse().getContentAsString();

        // Extraer el ID generado y probar GET verificando el efecto
        Avistamiento guardado = objectMapper.readValue(respuesta, Avistamiento.class);
        
        mockMvc.perform(get("/avistamientos/" + guardado.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.especie", is("Colibri")));
    }

    @Test
    void post_Error400_DatosInvalidos() throws Exception {
        avistamientoBase.setEspecie("A"); // Especie muy corta
        String jsonPost = objectMapper.writeValueAsString(avistamientoBase);

        mockMvc.perform(post("/avistamientos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPost))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Especie inválida"));
    }

    @Test
    void get_ListarTodos200() throws Exception {
        repository.save(avistamientoBase);

        mockMvc.perform(get("/avistamientos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].especie", is("Colibri")));
    }

    @Test
    void get_Error404_NoEncontrado() throws Exception {
        mockMvc.perform(get("/avistamientos/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void put_Exito200() throws Exception {
        Avistamiento guardado = repository.save(avistamientoBase);
        
        guardado.setLugar("Monserrate");
        String jsonPut = objectMapper.writeValueAsString(guardado);

        mockMvc.perform(put("/avistamientos/" + guardado.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPut))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lugar", is("Monserrate")));
    }

    @Test
    void put_Error404_NoEncontrado() throws Exception {
        String jsonPut = objectMapper.writeValueAsString(avistamientoBase);

        mockMvc.perform(put("/avistamientos/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPut))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_Exito204_Y_VerificarEfecto404() throws Exception {
        Avistamiento guardado = repository.save(avistamientoBase);

        // Prueba DELETE (204)
        mockMvc.perform(delete("/avistamientos/" + guardado.getId()))
                .andExpect(status().isNoContent());

        // Verificar el efecto: Un GET posterior debe ser 404
        mockMvc.perform(get("/avistamientos/" + guardado.getId()))
                .andExpect(status().isNotFound());
        
        // Verificar en base de datos
        assertFalse(repository.findById(guardado.getId()).isPresent());
    }
}