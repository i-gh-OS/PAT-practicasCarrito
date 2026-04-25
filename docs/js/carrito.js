function getNombreProducto(idArticulo) {
    const producto = PRODUCTOS[idArticulo];
    return producto ? producto.nombre : `Articulo ${idArticulo}`;
}

function formatearImporte(valor) {
    const numero = Number(valor || 0);
    return `${numero.toFixed(2)} EUR`;
}

function pintarTabla(carrito) {
    const tabla = document.getElementById("tabla-carrito");
    const total = document.getElementById("total");

    tabla.innerHTML = "";

    if (!carrito.lineas || carrito.lineas.length === 0) {
        tabla.innerHTML = `
            <tr>
                <td colspan="5">El carrito esta vacio.</td>
            </tr>
        `;
        total.textContent = formatearImporte(0);
        return;
    }

    carrito.lineas.forEach((linea) => {
        const fila = document.createElement("tr");

        fila.innerHTML = `
            <td>${getNombreProducto(linea.idArticulo)}</td>
            <td>${linea.unidades}</td>
            <td>${formatearImporte(linea.precioUnitario)}</td>
            <td>${formatearImporte(linea.costeLinea)}</td>
            <td><button type="button" onclick="eliminar(${linea.idLinea})">Eliminar</button></td>
        `;

        tabla.appendChild(fila);
    });

    total.textContent = formatearImporte(carrito.totalPrecio);
}

async function cargarCarrito() {
    const tabla = document.getElementById("tabla-carrito");
    const total = document.getElementById("total");

    tabla.innerHTML = `
        <tr>
            <td colspan="5">Cargando carrito...</td>
        </tr>
    `;
    total.textContent = "...";

    try {
        const carrito = await getCarrito();
        pintarTabla(carrito);
    } catch (error) {
        console.error(error);
        tabla.innerHTML = `
            <tr>
                <td colspan="5">No se pudo cargar el carrito.</td>
            </tr>
        `;
        total.textContent = "--";
    }
}

async function eliminar(idLinea) {
    try {
        await borrarLinea(idLinea);
        await cargarCarrito();
    } catch (error) {
        console.error(error);
        alert("No se pudo eliminar la linea del carrito.");
    }
}

window.eliminar = eliminar;

document.addEventListener("DOMContentLoaded", cargarCarrito);
