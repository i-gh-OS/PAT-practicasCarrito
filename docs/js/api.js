const API_URL = "http://localhost:8080/api";
const ID_CARRITO = 1;

const PRODUCTOS = {
    501: { nombre: "Manzanas", precio: 2.40 },
    502: { nombre: "Pan integral", precio: 1.85 },
    503: { nombre: "Leche", precio: 1.15 },
    504: { nombre: "Detergente", precio: 6.20 }
};

async function request(path, options = {}) {
    let response;

    try {
        response = await fetch(`${API_URL}${path}`, {
            headers: {
                Accept: "application/json"
            },
            targetAddressSpace: "local",
            ...options
        });
    } catch (error) {
        throw new Error("No se pudo conectar con el backend en localhost:8080. Comprueba que Spring Boot esta arrancado y que el navegador ha permitido el acceso local.");
    }

    if (!response.ok) {
        let mensaje = `Error ${response.status}`;

        try {
            const texto = await response.text();
            if (texto) {
                mensaje = texto;
            }
        } catch (error) {
            console.error("No se pudo leer el error de la API", error);
        }

        throw new Error(mensaje);
    }

    if (response.status === 204) {
        return null;
    }

    return response.json();
}

async function getCarrito() {
    return request(`/carrito/${ID_CARRITO}`);
}

async function addProducto(idArticulo, precioUnitario, unidades = 1) {
    const params = new URLSearchParams({
        idArticulo: String(idArticulo),
        precioUnitario: String(precioUnitario),
        unidades: String(unidades)
    });

    return request(`/carrito/${ID_CARRITO}/lineas?${params.toString()}`, {
        method: "POST"
    });
}

async function borrarLinea(idLinea) {
    return request(`/carrito/${ID_CARRITO}/lineas/${idLinea}`, {
        method: "DELETE"
    });
}

async function confirmarCompra(datosCompra) {
    return request(`/carrito/${ID_CARRITO}/confirmar`, {
        method: "POST",
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json"
        },
        body: JSON.stringify(datosCompra)
    });
}
