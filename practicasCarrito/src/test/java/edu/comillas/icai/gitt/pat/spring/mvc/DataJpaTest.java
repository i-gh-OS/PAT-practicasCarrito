package edu.comillas.icai.gitt.pat.spring.mvc;

import edu.comillas.icai.gitt.pat.spring.mvc.modelo.LineaCarrito;
import edu.comillas.icai.gitt.pat.spring.mvc.repository.LineaCarritoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class LineaCarritoRepositoryTest {

    @Autowired
    LineaCarritoRepository lineaCarritoRepository;

    @Test
    void lineaSinCarritoDebeFallarPorIntegridad() {
        // Given
        LineaCarrito linea = new LineaCarrito();
        linea.setIdArticulo(10L);
        linea.setPrecioUnitario(java.math.BigDecimal.valueOf(2.5));
        linea.setUnidades(2);
        linea.setCosteLinea(java.math.BigDecimal.valueOf(5.0));
        // NO seteamos carrito

        // When
        DataIntegrityViolationException error = null;
        try {
            lineaCarritoRepository.save(linea);
        } catch (DataIntegrityViolationException e) {
            error = e;
        }

        // Then
        assertNotNull(error);
    }
}