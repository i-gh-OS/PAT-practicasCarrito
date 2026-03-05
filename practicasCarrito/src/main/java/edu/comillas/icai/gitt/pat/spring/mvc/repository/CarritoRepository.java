package edu.comillas.icai.gitt.pat.spring.mvc.repository;

import edu.comillas.icai.gitt.pat.spring.mvc.modelo.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
}