package co.edu.unbosque.avistamientos.avistamientos_api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.avistamientos.avistamientos_api.domain.model.Avistamiento;

public interface AvistamientoRepository extends JpaRepository<Avistamiento, Long> {

}
