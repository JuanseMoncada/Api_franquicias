// Crear sucursal

const formSucursal = document.getElementById("formSucursal");

formSucursal.addEventListener("submit", async function (event) {
    event.preventDefault();

    const nombreSucursal = document.getElementById("nombreSucursal").value;
    const idFranquicia = document.getElementById("franquiciaId").value;

    const sucursal = {
        nombreSucursal: nombreSucursal,
        idFranquicia: Number(idFranquicia)
    };

    try {
        const response = await fetch("/crearSucursales", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(sucursal)
        });

        if (!response.ok) {
            throw new Error("No se pudo crear la sucursal");
        }

        alert("Sucursal creada correctamente");

        formSucursal.reset();

        window.location.reload();

    } catch (error) {
        alert("Error al crear la sucursal");
        console.error(error);
    }
});

// Eliinar sucursal
async function eliminarSucursal(idSucursal) {

    const confirmar = confirm("¿Está seguro de eliminar esta sucursal?");

    if (!confirmar) {
        return;
    }

    try {
        const response = await fetch(`/eliminarSucursal/${idSucursal}`, {
            method: "DELETE"
        });

        if (!response.ok) {
            throw new Error("No se pudo eliminar la sucursal");
        }

        alert("Sucursal eliminada correctamente");

        window.location.reload();

    } catch (error) {
        alert("Error al eliminar la sucursal");
        console.error(error);
    }
}

// Actualizar

function activarEdicion(boton) {

    const fila = boton.closest("tr");

    const nombreTexto = fila.querySelector(".nombre-texto");
    const nombreInput = fila.querySelector(".nombre-input");

    nombreTexto.style.display = "none";
    nombreInput.style.display = "inline-block";

    fila.querySelector(".btn-editar").style.display = "none";
    fila.querySelector(".btn-guardar").style.display = "inline-block";
    fila.querySelector(".btn-cancelar").style.display = "inline-block";
}


async function guardarEdicion(boton) {

    const fila = boton.closest("tr");
    const idSucursal = boton.dataset.id;

    const nombreInput = fila.querySelector(".nombre-input");
    const nombreSucursal = nombreInput.value.trim();

    if (!nombreSucursal) {
        alert("El nombre de la sucursal no puede estar vacío");
        return;
    }

    const sucursal = {
        nombreSucursal: nombreSucursal
    };

    try {

        const response = await fetch(`/actualizarSucursal/${idSucursal}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(sucursal)
        });

        if (!response.ok) {
            throw new Error("No se pudo actualizar la sucursal");
        }

        alert("Sucursal actualizada correctamente");

        window.location.reload();

    } catch (error) {
        alert("Error al actualizar la sucursal");
        console.error(error);
    }
}


function cancelarEdicion(boton) {

    const fila = boton.closest("tr");

    const nombreTexto = fila.querySelector(".nombre-texto");
    const nombreInput = fila.querySelector(".nombre-input");

    nombreInput.value = nombreTexto.textContent.trim();

    nombreTexto.style.display = "inline";
    nombreInput.style.display = "none";

    fila.querySelector(".btn-editar").style.display = "inline-block";
    fila.querySelector(".btn-guardar").style.display = "none";
    fila.querySelector(".btn-cancelar").style.display = "none";
}