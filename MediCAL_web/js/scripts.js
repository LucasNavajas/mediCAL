 	function redirigir(directorio){
      window.location.href = directorio;
    }
      function signOut(){
      // Cerrar sesión con Firebase Authentication
      firebase.auth().signOut().then(function() {
        // Cierre de sesión exitoso
        console.log("Cierre de sesión exitoso");
        window.location.href = "n1_inicio_sesion.html";
      }).catch(function(error) {
        // Ocurrió un error durante el cierre de sesión
        console.error("Error durante el cierre de sesión:", error);
      });
    }

     function agregarCabeceras(tabla, tbody, dataJSON) {
    // Crea una fila para las cabeceras
    var cabecera = document.createElement("tr");

    // Crea la celda del checkbox de selección
    var checkboxHeaderCell = document.createElement("th");
    var checkboxHeader = document.createElement("input");
    checkboxHeader.type = "checkbox";
    checkboxHeader.id = "selectAll";
    checkboxHeader.onclick = function () {
        toggleSelectAll();
    };
    checkboxHeaderCell.appendChild(checkboxHeader);
    cabecera.appendChild(checkboxHeaderCell);

    if (dataJSON && dataJSON.length > 0) {
        // Obtén las propiedades del primer objeto del arreglo como cabeceras
        var primerObjeto = dataJSON[0];
        var propiedadesCabecera = Object.keys(primerObjeto);

        // Agrega las cabeceras basadas en las propiedades del objeto
        propiedadesCabecera.forEach(function (nombreCabecera, index) {
            var cabeceraCell = document.createElement("th");

            // Capitaliza y separa las palabras en la cabecera
            var palabras = nombreCabecera.split(/(?=[A-Z])/);
            var cabeceraTexto = palabras.map(function (palabra) {
                return palabra.charAt(0).toUpperCase() + palabra.slice(1);
            }).join(" ");

            cabeceraCell.textContent = cabeceraTexto;
            cabeceraCell.style.minWidth = "150px"; // Puedes ajustar este valor según tus necesidades
            cabeceraCell.style.whiteSpace = "nowrap"

            // Ajusta la función onclick de acuerdo a la posición de la cabecera
            cabeceraCell.onclick = function () {
                toggleSearch(propiedadesCabecera.indexOf(nombreCabecera));
            };

            cabecera.appendChild(cabeceraCell);
        });
    }

    tbody.appendChild(cabecera);
}

// Función para reemplazar las filas de la tabla con datos JSON
    function reemplazarFilasConJSON(datosJSON) {

      var tabla = document.querySelector("table"); // Selecciona la tabla
      var tbody = tabla.querySelector("tbody"); // Selecciona el cuerpo de la tabla
      // Limpia el contenido actual del cuerpo de la tabla
      tbody.innerHTML = "";
      agregarCabeceras(tabla, tbody, datosJSON);
      // Recorre los datos JSON y crea filas para cada elemento
      for (var i = 0; i < datosJSON.length; i++) {
        var medicamento = datosJSON[i];
        var fila = document.createElement("tr");

        // Celda para el checkbox
        var checkboxCell = document.createElement("td");
        var checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkbox.className = "rowCheckbox";
        checkbox.onclick = function () {
          toggleRowSelection(this);
        };
        checkboxCell.appendChild(checkbox);
        fila.appendChild(checkboxCell);

        var primerObjeto = datosJSON[0];
        var propiedades = Object.keys(primerObjeto);



        for (var j = 0; j < propiedades.length; j++) {


          var propiedad = propiedades[j];
          var celda = document.createElement("td");

          // Si la propiedad no contiene la subcadena "fecha" y el valor no es nulo ni falso, muestra el primer atributo del objeto
        if (!propiedad.includes("fecha") && typeof medicamento[propiedad] === "object" && medicamento[propiedad] !== null && medicamento[propiedad] !== false) {
          celda.textContent = getFirstAttribute(medicamento[propiedad]);
        } else if (propiedad.includes("fecha") && medicamento[propiedad] !== null) {
          var fecha = new Date(
            medicamento[propiedad][0],
            medicamento[propiedad][1] - 1,
            medicamento[propiedad][2]
          );
          celda.textContent = fecha.toLocaleDateString();
        } else {
            // Verifica si la propiedad existe y no es nula ni falsa, de lo contrario, muestra "-"
            celda.textContent = medicamento[propiedad] !== null && medicamento[propiedad] !== false
              ? medicamento[propiedad]
              : "-";
          }

          fila.appendChild(celda);
        }

        // Celda con iconos
        var iconosCell = document.createElement("td");
        var iconosDiv = document.createElement("div");
        iconosDiv.className = "edit-delete-revert-icons";

        // Icono de edición
        var editarIcono = document.createElement("i");
        editarIcono.className = "bi bi-pencil edit-icon";
        editarIcono.onclick = function () {
          toggleProfileRow(i, this); // Ajusta el índice del botón de edición
        };
        iconosDiv.appendChild(editarIcono);

        // Icono de eliminación
        var eliminarIcono = document.createElement("i");
        eliminarIcono.className = "bi bi-trash delete-icon";
        eliminarIcono.onclick = function () {
          toggleDeleteRow(i, this); // Ajusta el índice del botón de eliminación
        };
        iconosDiv.appendChild(eliminarIcono);

        // Icono de reversión
        var revertirIcono = document.createElement("i");
        revertirIcono.className = "bi bi-arrow-return-left revert-icon hide";
        revertirIcono.onclick = function () {
          toggleRestoreRow(i, this); // Ajusta el índice del botón de reversión
        };
        iconosDiv.appendChild(revertirIcono);

        iconosCell.appendChild(iconosDiv);
        fila.appendChild(iconosCell);

        // Agrega la fila al cuerpo de la tabla
        tbody.appendChild(fila);
      }
    }

function getFirstAttribute(obj) {
  for (var key in obj) {
    if (obj.hasOwnProperty(key)) {
      return obj[key];
    }
  }
  return "-";
}