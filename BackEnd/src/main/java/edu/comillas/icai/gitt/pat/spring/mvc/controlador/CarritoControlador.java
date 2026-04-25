package edu.comillas.icai.gitt.pat.spring.mvc.controlador;

import edu.comillas.icai.gitt.pat.spring.mvc.modelo.Carrito;
import edu.comillas.icai.gitt.pat.spring.mvc.repository.CarritoRepository;
import edu.comillas.icai.gitt.pat.spring.mvc.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class CarritoControlador {

    @Autowired
    CarritoRepository repoCarrito;
    @Autowired
    CarritoService servicio;

    @GetMapping("/api/carrito")
    public Iterable<Carrito> getCarritos() {
        return repoCarrito.findAll();
    }

    @PostMapping("/api/carrito")
    @ResponseStatus(HttpStatus.CREATED)
    public Carrito creaCarrito(@RequestBody Carrito carritoNuevo) {
        return servicio.crea(carritoNuevo);
    }

    @GetMapping("/api/carrito/{idCarrito}")
    public Carrito getCarrito(@PathVariable Long idCarrito) {
        return servicio.lee(idCarrito);
    }

    @DeleteMapping("/api/carrito/{idCarrito}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrarCarrito(@PathVariable Long idCarrito) {
        servicio.borra(idCarrito);
    }

    @PostMapping("/api/carrito/{idCarrito}/lineas")
    @ResponseStatus(HttpStatus.CREATED)
    public Carrito addLinea(@PathVariable Long idCarrito,
                            @RequestParam Long idArticulo,
                            @RequestParam Double precioUnitario,
                            @RequestParam Integer unidades) {
        //uso double en requestParam y convierto
        return servicio.addLinea(idCarrito, idArticulo, java.math.BigDecimal.valueOf(precioUnitario), unidades);
    }

    @DeleteMapping("/api/carrito/{idCarrito}/lineas/{idLinea}")
    public Carrito borraLinea(@PathVariable Long idCarrito, @PathVariable Long idLinea) {
        return servicio.borraLinea(idCarrito, idLinea);
    }
}
