package edu.comillas.icai.gitt.pat.spring.mvc.repository;

import edu.comillas.icai.gitt.pat.spring.mvc.modelo.Carrito;
import edu.comillas.icai.gitt.pat.spring.mvc.modelo.LineaCarrito;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonIgnore;


public interface LineaCarritoRepository extends CrudRepository<LineaCarrito, Long> {
    Optional<LineaCarrito> findByCarrito_IdCarritoAndIdArticulo(Long idCarrito, Long idArticulo);
    Optional<LineaCarrito> findByIdLineaAndCarrito_IdCarrito(Long idLinea, Long idCarrito);
    /*@JsonIgnore
    @ManyToOne(optional=false, fetch = FetchType.LAZY)
    @JoinColumn(name="id_carrito", nullable=false)
    private Carrito carrito;
    */
}