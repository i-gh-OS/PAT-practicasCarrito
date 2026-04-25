async function agregar(idArticulo, precioUnitario) {
    await addProducto(idArticulo, precioUnitario, 1);
    alert("Producto añadido al carrito");
}