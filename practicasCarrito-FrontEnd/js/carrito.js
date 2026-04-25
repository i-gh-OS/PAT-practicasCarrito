async function cargarCarrito() {
    const carrito = await getCarrito();

    const tabla = document.getElementById("tabla-carrito");
    tabla.innerHTML = "";

    carrito.lineas.forEach(l => {
        const fila = `
        <tr>
            <td>${l.idArticulo}</td>
            <td>${l.unidades}</td>
            <td>${l.precioUnitario}</td>
            <td>${l.costeLinea}</td>
            <td><button onclick="eliminar(${l.idLinea})">X</button></td>
        </tr>
        `;
        tabla.innerHTML += fila;
    });

    document.getElementById("total").innerText = carrito.totalPrecio;
}

async function eliminar(idLinea) {
    await borrarLinea(idLinea);
    cargarCarrito();
}

cargarCarrito();