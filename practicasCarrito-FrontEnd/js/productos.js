async function agregar(id, precio) {
    await addProducto(id, precio, 1);
    alert("Producto añadido");
}