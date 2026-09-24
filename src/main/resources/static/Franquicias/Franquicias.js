
// Funcion para agregar
const formulario = document.getElementById("formFranquicia");

formulario.addEventListener("submit", async function (event) {

    // Evita que el formulario se envíe de la manera tradicional
    event.preventDefault();

    // Obtenemos el nombre escrito por el usuario
    const nombre = document.getElementById("nombreFranquicia").value;

    // Creamos el objeto que espera nuestro Request
    const franquicia = {
        nombreFranquicia: nombre
    };

    // Enviamos el JSON al Controller
    const response = await fetch("/creacionFranquicia", {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(franquicia)
    });

    if (response.ok) {

        alert("Franquicia creada correctamente");

        // Recargamos para actualizar la tabla
        window.location.reload();

    } else {

        alert("No se pudo crear la franquicia");

    }

});


//    Funcion para eliminar
async function eliminarFranquicia(id) {

    const confirmar = confirm(
        "¿Está seguro de eliminar esta franquicia?"
    );

    if (!confirmar) {
        return;
    }

    const response = await fetch(
        "/franquicias/" + id,
        {
            method: "DELETE"
        }
    );

    if (response.ok) {

        alert("Franquicia eliminada correctamente");

        window.location.reload();

    } else {

        alert("No se pudo eliminar la franquicia");

    }
}


// Actualizar

function activarEdicion(boton) {

    const fila = boton.closest("tr");

    const texto = fila.querySelector(".nombre-texto");
    const input = fila.querySelector(".nombre-input");

    const btnEditar = fila.querySelector(".btn-editar");
    const btnGuardar = fila.querySelector(".btn-guardar");
    const btnCancelar = fila.querySelector(".btn-cancelar");

    // Ocultamos el nombre normal
    texto.style.display = "none";

    // Mostramos el input
    input.style.display = "inline-block";

    // Cambiamos los botones
    btnEditar.style.display = "none";
    btnGuardar.style.display = "inline-block";
    btnCancelar.style.display = "inline-block";

    // Colocamos el cursor en el input
    input.focus();
}

async function guardarEdicion(boton) {

    const fila = boton.closest("tr");

    const id = boton.dataset.id;

    const input = fila.querySelector(".nombre-input");

    const nuevoNombre = input.value.trim();

    if (nuevoNombre === "") {
        alert("El nombre no puede estar vacío");
        return;
    }

    const franquicia = {
        nombreFranquicia: nuevoNombre
    };

    const response = await fetch(
        "/franquicias/" + id,
        {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(franquicia)
        }
    );

    if (response.ok) {

        window.location.reload();

    } else {

        alert("No se pudo actualizar la franquicia");
    }
}

function cancelarEdicion(boton) {

    const fila = boton.closest("tr");

    const texto = fila.querySelector(".nombre-texto");
    const input = fila.querySelector(".nombre-input");

    const btnEditar = fila.querySelector(".btn-editar");
    const btnGuardar = fila.querySelector(".btn-guardar");
    const btnCancelar = fila.querySelector(".btn-cancelar");

    // Volvemos a colocar el nombre original
    input.value = texto.textContent.trim();

    // Mostramos nuevamente el texto
    texto.style.display = "inline";

    // Ocultamos el input
    input.style.display = "none";

    // Restauramos los botones
    btnEditar.style.display = "inline-block";
    btnGuardar.style.display = "none";
    btnCancelar.style.display = "none";
}