package edu.comillas.icai.gitt.pat.spring.mvc.modelo;

import edu.comillas.icai.gitt.pat.spring.mvc.modelo.LineaCarrito;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carrito")
public class Carrito {

    @Id
    @Column(name = "id_carrito")
    private Long idCarrito;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "correo", nullable = false)
    private String correo;

    @Column(name = "total_precio", nullable = false)
    private BigDecimal totalPrecio = BigDecimal.ZERO;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LineaCarrito> lineas = new ArrayList<>();

    public Carrito() {
    }

    public Carrito(Long idCarrito, Long idUsuario, String correo) {
        this.idCarrito = idCarrito;
        this.idUsuario = idUsuario;
        this.correo = correo;
        this.totalPrecio = BigDecimal.ZERO;
    }

    public Long getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(Long idCarrito) {
        this.idCarrito = idCarrito;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public BigDecimal getTotalPrecio() {
        return totalPrecio;
    }

    public void setTotalPrecio(BigDecimal totalPrecio) {
        this.totalPrecio = totalPrecio;
    }

    public List<LineaCarrito> getLineas() {
        return lineas;
    }

    public void setLineas(List<LineaCarrito> lineas) {
        this.lineas = lineas;
    }
}