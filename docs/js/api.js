const API_URL = "http://localhost:8080/api";
const ID_CARRITO = 1;

async function getCarrito() {
    const res = await fetch(`${API_URL}/carrito/${ID_CARRITO}`);
    return await res.json();
}

async function addProducto(idArticulo, precio, unidades) {
    await fetch(`${API_URL}/carrito/${ID_CARRITO}/lineas?idArticulo=${idArticulo}&precioUnitario=${precio}&unidades=${unidades}`, {
        method: "POST"
    });
}

async function borrarLinea(idLinea) {
    await fetch(`${API_URL}/carrito/${ID_CARRITO}/lineas/${idLinea}`, {
        method: "DELETE"
    });
}