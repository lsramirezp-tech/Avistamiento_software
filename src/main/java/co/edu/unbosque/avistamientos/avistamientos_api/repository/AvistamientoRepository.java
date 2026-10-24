package co.edu.unbosque.avistamientos.avistamientos_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.avistamientos.avistamientos_api.model.Avistamiento;

public interface AvistamientoRepository extends JpaRepository<Avistamiento, Long> {

}
