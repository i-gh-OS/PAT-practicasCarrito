package edu.comillas.icai.gitt.pat.spring.mvc.controlador;

import edu.comillas.icai.gitt.pat.spring.mvc.modelo.Carrito;
import edu.comillas.icai.gitt.pat.spring.mvc.service.CarritoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarritoControlador {

    private final CarritoService carritoService;

    public CarritoControlador(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @GetMapping("/api/carrito")
    public List<Carrito> getCarritos() {
        return carritoService.getCarritos();
    }

    @PostMapping("/api/carrito")
    @ResponseStatus(HttpStatus.CREATED)
    public Carrito creaCarrito(@RequestBody Carrito carrito) {
        return carritoService.crearCarrito(carrito);
    }

    @GetMapping("/api/carrito/{idCarrito}")
    public Carrito getCarrito(@PathVariable long idCarrito) {
        return carritoService.getCarrito(idCarrito);
    }

    @DeleteMapping("/api/carrito/{idCarrito}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrarCarrito(@PathVariable long idCarrito) {
        carritoService.borrarCarrito(idCarrito);
    }

    @PutMapping("/api/carrito/{idCarrito}")
    public Carrito modificaCarrito(@PathVariable long idCarrito, @RequestBody Carrito carrito) {
        return carritoService.modificarCarrito(idCarrito, carrito);
    }
}