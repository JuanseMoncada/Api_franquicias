// Crear productos

const formProducto = document.getElementById("formProducto");

formProducto.addEventListener("submit", async function (event) {

    event.preventDefault();

    const productoNombre = document.getElementById("productoNombre").value;
    const cantidadStock = document.getElementById("productosCantidadStock").value;
    const sucursalId = document.getElementById("sucursalId").value;

    const producto = {
        nombreProducto: productoNombre,
        cantidadProductoStock: Number(cantidadStock),
        idSucursal: Number(sucursalId)
    };

    try {

        const response = await fetch("/crearProductos", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(producto)
        });

        if (!response.ok) {
            throw new Error("No se pudo crear el producto");
        }

        alert("Producto creado correctamente");

        formProducto.reset();

        window.location.reload();

    } catch (error) {

        alert("Error al crear el producto");
        console.error(error);
    }
});

async function eliminarProducto(idProducto) {

    const confirmar = confirm("¿Está seguro de eliminar este producto?");

    if (!confirmar) {
        return;
    }

    try {

        const response = await fetch(`/eliminarProducto/${idProducto}`, {
            method: "DELETE"
        });

        if (!response.ok) {
            throw new Error("No se pudo eliminar el producto");
        }

        alert("Producto eliminado correctamente");

        window.location.reload();

    } catch (error) {

        alert("Error al eliminar el producto");
        console.error(error);
    }
}


function activarEdicion(boton) {

    const fila = boton.closest("tr");

    const productoTexto = fila.querySelector(".producto-texto");
    const productoInput = fila.querySelector(".producto-input");

    const stockTexto = fila.querySelector(".stock-texto");
    const stockInput = fila.querySelector(".stock-input");

    productoTexto.style.display = "none";
    productoInput.style.display = "block";

    stockTexto.style.display = "none";
    stockInput.style.display = "block";

    fila.querySelector(".btn-editar").style.display = "none";
    fila.querySelector(".btn-guardar").style.display = "inline-block";
    fila.querySelector(".btn-cancelar").style.display = "inline-block";
}


async function guardarEdicion(boton) {

    const fila = boton.closest("tr");
    const idProducto = boton.dataset.id;

    const nombreProducto = fila.querySelector(".producto-input").value.trim();
    const cantidadProductoStock = fila.querySelector(".stock-input").value;

    if (!nombreProducto) {
        alert("El nombre del producto no puede estar vacío");
        return;
    }

    if (cantidadProductoStock === "" || Number(cantidadProductoStock) < 0) {
        alert("Ingrese una cantidad válida");
        return;
    }

    const producto = {
        nombreProducto: nombreProducto,
        cantidadProductoStock: Number(cantidadProductoStock)
    };

    try {

        const response = await fetch(`/actualizarProducto/${idProducto}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(producto)
        });

        if (!response.ok) {
            throw new Error("No se pudo actualizar el producto");
        }

        alert("Producto actualizado correctamente");

        window.location.reload();

    } catch (error) {

        alert("Error al actualizar el producto");
        console.error(error);
    }
}


function cancelarEdicion(boton) {

    const fila = boton.closest("tr");

    const productoTexto = fila.querySelector(".producto-texto");
    const productoInput = fila.querySelector(".producto-input");

    const stockTexto = fila.querySelector(".stock-texto");
    const stockInput = fila.querySelector(".stock-input");

    productoInput.value = productoTexto.textContent.trim();
    stockInput.value = stockTexto.textContent.trim();

    productoTexto.style.display = "inline";
    productoInput.style.display = "none";

    stockTexto.style.display = "inline";
    stockInput.style.display = "none";

    fila.querySelector(".btn-editar").style.display = "inline-block";
    fila.querySelector(".btn-guardar").style.display = "none";
    fila.querySelector(".btn-cancelar").style.display = "none";
}


//FUNCIONALIDAD PARA LA FUNCION DE VISUALIZAR CANTIDAD DE PRODUCTOS MAXIMOS EN UNA SUCURSAL TENIENDO PRESENTE LA FRANQUICIA

async function consultarMayorStock() {

    const idFranquicia =
        document.getElementById("franquiciaStock").value;

    if (!idFranquicia) {
        alert("Seleccione una franquicia");
        return;
    }

    try {

        const response = await fetch(
            `/franquicias/${idFranquicia}/productos-mayor-stock`
        );

        if (!response.ok) {
            throw new Error("No se pudo realizar la consulta");
        }

        const productos = await response.json();

        const tabla = document.getElementById("tablaMayorStock");
        const resultado = document.getElementById("resultadoMayorStock");

        tabla.innerHTML = "";

        if (productos.length === 0) {

            tabla.innerHTML = `
                <tr>
                    <td colspan="3">
                        No se encontraron productos
                    </td>
                </tr>
            `;

        } else {

            productos.forEach(function (producto) {

                const fila = document.createElement("tr");

                fila.innerHTML = `
                    <td>${producto.nombreProducto}</td>
                    <td>${producto.cantidadProductoStock}</td>
                    <td>${producto.nombreSucursal}</td>
                `;

                tabla.appendChild(fila);
            });
        }

        resultado.style.display = "block";

    } catch (error) {

        alert("Error al consultar los productos");
        console.error(error);
    }
}