package edu.comillas.icai.gitt.pat.spring.mvc.service;

import edu.comillas.icai.gitt.pat.spring.mvc.modelo.Carrito;
import edu.comillas.icai.gitt.pat.spring.mvc.modelo.LineaCarrito;
import edu.comillas.icai.gitt.pat.spring.mvc.repository.CarritoRepository;
import edu.comillas.icai.gitt.pat.spring.mvc.repository.LineaCarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarritoService {

    @Autowired
    CarritoRepository carritoRepository;
    @Autowired
    LineaCarritoRepository lineaCarritoRepository;

    public Carrito crea(Carrito carritoNuevo) {
        if (carritoNuevo.getIdCarrito() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "idCarrito es obligatorio");
        }
        if (carritoRepository.existsById(carritoNuevo.getIdCarrito())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un carrito con ese idCarrito");
        }
        if (carritoNuevo.getTotalPrecio() == null) carritoNuevo.setTotalPrecio(BigDecimal.ZERO);
        return carritoRepository.save(carritoNuevo);
    }

    public Carrito lee(Long idCarrito) {
        return carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado"));
    }

    public void borra(Long idCarrito) {
        if (!carritoRepository.existsById(idCarrito)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado");
        }
        carritoRepository.deleteById(idCarrito);
    }

    /**
     * Operación multi-entidad: toca LINEA_CARRITO y también actualiza total en CARRITO
     * => debe ir en la MISMA transacción
     */
    @Transactional
    public Carrito addLinea(Long idCarrito, Long idArticulo, BigDecimal precioUnitario, Integer unidades) {
        Carrito carrito = lee(idCarrito);

        if (idArticulo == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "idArticulo es obligatorio");
        if (precioUnitario == null || precioUnitario.compareTo(BigDecimal.ZERO) < 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "precioUnitario debe ser >= 0");
        if (unidades == null || unidades < 1)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "unidades debe ser >= 1");

        LineaCarrito linea = lineaCarritoRepository.findByCarrito_IdCarritoAndIdArticulo(idCarrito, idArticulo).orElse(null);

        if (linea == null) {
            linea = new LineaCarrito();
            linea.setCarrito(carrito);
            linea.setIdArticulo(idArticulo);
            linea.setPrecioUnitario(precioUnitario);
            linea.setUnidades(unidades);
            carrito.getLineas().add(linea);
        } else {
            linea.setUnidades(linea.getUnidades() + unidades);
            linea.setPrecioUnitario(precioUnitario); // decisión simple para práctica
        }

        linea.setCosteLinea(linea.getPrecioUnitario().multiply(BigDecimal.valueOf(linea.getUnidades())));
        lineaCarritoRepository.save(linea);

        recalculaTotal(carrito);
        carritoRepository.save(carrito);

        return carrito;
    }

    @Transactional
    public Carrito borraLinea(Long idCarrito, Long idLinea) {
        Carrito carrito = lee(idCarrito);

        LineaCarrito linea = lineaCarritoRepository.findByIdLineaAndCarrito_IdCarrito(idLinea, idCarrito)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Línea no encontrada en ese carrito"));

        carrito.getLineas().removeIf(l -> idLinea.equals(l.getIdLinea()));
        lineaCarritoRepository.delete(linea);

        recalculaTotal(carrito);
        carritoRepository.save(carrito);

        return carrito;
    }

    @Transactional
    public Map<String, Object> confirmarCompra(Long idCarrito, Map<String, String> datosCompra) {
        Carrito carrito = lee(idCarrito);
        List<LineaCarrito> lineas = new ArrayList<>(carrito.getLineas());

        String nombre = valorTexto(datosCompra, "nombre");
        String correo = valorTexto(datosCompra, "correo");
        String direccion = valorTexto(datosCompra, "direccion");
        String fecha = valorTexto(datosCompra, "fecha");
        String pago = valorTexto(datosCompra, "pago");
        String comentarios = valorTexto(datosCompra, "comentarios");

        if (nombre == null || correo == null || direccion == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "nombre, correo y direccion son obligatorios");
        }
        if (lineas.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El carrito esta vacio");
        }

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("mensaje", "Compra confirmada correctamente");
        respuesta.put("idCarrito", carrito.getIdCarrito());
        respuesta.put("nombre", nombre);
        respuesta.put("correo", correo);
        respuesta.put("direccion", direccion);
        respuesta.put("fecha", fecha);
        respuesta.put("pago", pago);
        respuesta.put("comentarios", comentarios);
        respuesta.put("totalPrecio", carrito.getTotalPrecio());
        respuesta.put("numeroLineas", lineas.size());

        lineaCarritoRepository.deleteAll(lineas);
        carrito.getLineas().clear();
        carrito.setTotalPrecio(BigDecimal.ZERO);
        carritoRepository.save(carrito);

        return respuesta;
    }

    private void recalculaTotal(Carrito carrito) {
        BigDecimal total = BigDecimal.ZERO;
        for (LineaCarrito l : carrito.getLineas()) {
            if (l.getCosteLinea() != null) total = total.add(l.getCosteLinea());
        }
        carrito.setTotalPrecio(total);
    }

    private String valorTexto(Map<String, String> datosCompra, String clave) {
        if (datosCompra == null) {
            return null;
        }

        String valor = datosCompra.get(clave);
        if (valor == null) {
            return null;
        }

        String texto = valor.trim();
        return texto.isEmpty() ? null : texto;
    }
}
