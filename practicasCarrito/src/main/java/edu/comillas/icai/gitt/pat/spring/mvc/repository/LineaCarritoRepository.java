package edu.comillas.icai.gitt.pat.spring.mvc.repository;

import edu.comillas.icai.gitt.pat.spring.mvc.modelo.LineaCarrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LineaCarritoRepository extends JpaRepository<LineaCarrito, Long> {

    Optional<LineaCarrito> findByCarrito_IdCarritoAndIdArticulo(Long idCarrito, Long idArticulo);

    Optional<LineaCarrito> findByIdLineaAndCarrito_IdCarrito(Long idLinea, Long idCarrito);
}