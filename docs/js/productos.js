async function agregar(idArticulo, precioUnitario) {
    const producto = PRODUCTOS[idArticulo];

    try {
        await addProducto(idArticulo, precioUnitario, 1);
        const nombre = producto ? producto.nombre : `Articulo ${idArticulo}`;
        alert(`${nombre} se ha anadido al carrito.`);
    } catch (error) {
        console.error(error);
        alert("No se pudo anadir el producto al carrito.");
    }
}

window.agregar = agregar;
