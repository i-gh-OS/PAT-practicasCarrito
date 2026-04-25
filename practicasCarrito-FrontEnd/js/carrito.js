async function cargarCarrito() {
    const carrito = await getCarrito();

    const tabla = document.getElementById("tabla-carrito");
    const total = document.getElementById("total");

    tabla.innerHTML = "";

    carrito.lineas.forEach(linea => {
        const fila = `
            <tr>
                <td>${linea.idArticulo}</td>
                <td>${linea.unidades}</td>
                <td>${linea.precioUnitario} EUR</td>
                <td>${linea.costeLinea} EUR</td>
                <td>
                    <button onclick="eliminar(${linea.idLinea})">Eliminar</button>
                </td>
            </tr>
        `;

        tabla.innerHTML += fila;
    });

    total.textContent = `${carrito.totalPrecio} EUR`;
}

async function eliminar(idLinea) {
    await borrarLinea(idLinea);
    cargarCarrito();
}

cargarCarrito();