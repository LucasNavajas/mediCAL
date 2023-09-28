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
        		toggleSearch(index); // Pasa el índice como argumento
    		};

				    // Crear div con input de búsqueda dentro de la celda de cabecera
		    var searchContainer = document.createElement("div");
		    searchContainer.id = "searchContainer" + index; // Asigna un ID único
		    searchContainer.className = "search-container";
		    searchContainer.style.alignItems = "center";

		    var searchInput = document.createElement("input");
		    searchInput.type = "search";
		    searchInput.id = "search" + index; // Asigna un ID único
		    searchInput.className = "search-input";
		    searchInput.placeholder = "Buscar por " + cabeceraTexto;

		    searchContainer.appendChild(searchInput);
		    cabeceraCell.appendChild(searchContainer);
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
		  celda.setAttribute("data-propiedad", propiedad);
          // Si la propiedad no contiene la subcadena "fecha" y el valor no es nulo ni falso, muestra el primer atributo del objeto
        if (!propiedad.includes("fecha") && typeof medicamento[propiedad] === "object" && medicamento[propiedad] !== null && medicamento[propiedad] !== false) {
          celda.textContent = getFirstAttribute(medicamento[propiedad]);
        } else if (propiedad.includes("fecha") && medicamento[propiedad] !== null) {
            var partesFecha = medicamento[propiedad].split('-'); // Divide la cadena en partes

		  // Crea un objeto de fecha usando las partes divididas
		  var fecha = new Date(
		    parseInt(partesFecha[0]), // Año
		    parseInt(partesFecha[1]) - 1, // Mes (restamos 1 porque los meses en JavaScript son de 0 a 11)
		    parseInt(partesFecha[2]) // Día
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

        var editarIcono = document.createElement("i");
		editarIcono.className = "bi bi-pencil edit-icon";
		editarIcono.onclick = (function (index, icono) {
		  return function () {
		    editarInstancia(index); // Ajusta el índice del botón de edición
		  };
		})(i, editarIcono); // Pasar el valor actual de i y el elemento icono a la IIFE
		iconosDiv.appendChild(editarIcono);


        // Icono de eliminación
        var eliminarIcono = document.createElement("i");
		eliminarIcono.className = "bi bi-trash delete-icon";
		(function (index, fila) {
		  eliminarIcono.onclick = function () {
		    // Obtener el valor de la segunda columna de la fila
		    var idInstancia = fila.cells[1].textContent;
		    eliminarInstancia(idInstancia);
		    toggleDeleteRow(index, this);
		  };
		})(i, fila); // Pasar el valor actual de i a la IIFE
        iconosDiv.appendChild(eliminarIcono);

        // Icono de reversión
        var revertirIcono = document.createElement("i");
		revertirIcono.className = "bi bi-arrow-return-left revert-icon hide";

		(function (index, fila) {
		  revertirIcono.onclick = function () {
		  	var idInstancia = fila.cells[1].textContent;
		    recuperarInstancia(idInstancia);
		    toggleRestoreRow(index, this); // Ajusta el índice del botón de reversión
		  };
		})(i, fila); // Pasar el valor actual de i a la IIFE

		iconosDiv.appendChild(revertirIcono);

        iconosCell.appendChild(iconosDiv);
        fila.appendChild(iconosCell);

        // Agrega la fila al cuerpo de la tabla
        tbody.appendChild(fila);

        for (var j = 0; j < propiedades.length; j++) {
          var propiedad = propiedades[j];
		  if (propiedad.includes("fechaFin") && medicamento[propiedad] !== null && medicamento[propiedad] <= new Date().toISOString().slice(0, 10)) {
		    var iconosBorrar = document.querySelectorAll(".bi.bi-trash.delete-icon");
		    toggleDeleteRow(i, iconosBorrar[i]);
		    break; // Detiene el bucle una vez que se ha ejecutado toggleDeleteRow
		  }
		}
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


function toggleSearch(index) {
  var container = document.getElementById('searchContainer' + index);
  container.classList.toggle('active');
  var input = container.querySelector('.search-input');
  if (container.classList.contains('active')) {
    input.focus();
  } else {
    input.value = '';
    filterTable();
  }
}

function toggleDeleteButtonVisibility() {
    var checkboxes = document.querySelectorAll('tr:not(.deleted-row) .rowCheckbox:checked');
    var eliminarCalendariosBtn = document.getElementById('eliminarCalendariosBtn');
    eliminarCalendariosBtn.style.display = checkboxes.length > 0 ? 'inline-block' : 'none';
}

function toggleRevertButtonVisibility() {
    var checkboxes = document.querySelectorAll('tr.deleted-row .rowCheckbox:checked');
    var revertInstanciasBtn = document.getElementById('revertInstanciasBtn');
    revertInstanciasBtn.style.display = checkboxes.length > 0 ? 'inline-block' : 'none';
}


    function toggleRowSelection(checkbox) {
        var tableRow = checkbox.closest('tr');
        if (checkbox.checked) {
            tableRow.classList.add('clicked');
        } else {
            tableRow.classList.remove('clicked');
        }
        toggleDeleteButtonVisibility();
        toggleRevertButtonVisibility();
        //toggleDeletedRowsVisibility();
    }

    function toggleSelectAll() {
        var checkboxes = document.querySelectorAll('tr:not(.deleted-row) .rowCheckbox');
        var selectAllCheckbox = document.getElementById('selectAll');

        for (var i = 0; i < checkboxes.length; i++) {
            checkboxes[i].checked = selectAllCheckbox.checked;
            toggleRowSelection(checkboxes[i]);
        }
    }

    
    
    // Función para restaurar fila
    function toggleRestoreRow(row, icon) {
        var revertIcon= icon;
        var deleteIcon = revertIcon.previousElementSibling;
        //var editIcon = deleteIcon.previousElementSibling;

        revertIcon.classList.toggle('hide');
        deleteIcon.classList.toggle('hide');
        //editIcon.classList.toggle('hide');

        var tableRow = revertIcon.closest('tr');
        tableRow.classList.remove('deleted-row');

        var checkbox = tableRow.querySelector('.rowCheckbox');
        checkbox.style.display = 'inline-block';

        toggleDeleteButtonVisibility();
        toggleRevertButtonVisibility();
        //toggleDeletedRowsVisibility();
    }
    
    
    // Función para restaurar varias filas
    function restaurarFilasSeleccionadas() {
        var checkboxes = document.querySelectorAll('tr.deleted-row .rowCheckbox:checked');
        //var checkboxes = document.querySelectorAll('tr.deleted-row .rowCheckbox');

        for (var i = 0; i < checkboxes.length; i++) {
            var checkbox = checkboxes[i];
            var tableRow = checkbox.closest('tr');
            var revertIcon = tableRow.querySelector('.revert-icon');
            var deleteIcon = tableRow.querySelector('.delete-icon');
            //var editIcon = tableRow.querySelector('.edit-icon');

            deleteIcon.classList.remove('hide');
            //editIcon.classList.remove('hide');
            revertIcon.classList.add('hide');
            tableRow.classList.remove('deleted-row');
            //checkbox.style.display = 'inline-block';

            // Buscar el atributo "data-propiedad" que contiene "cod"
		    var tds = tableRow.querySelectorAll('td[data-propiedad*="cod"]');
		    
		    if (tds.length > 0) {
		      // Tomar el valor del primer td encontrado con "data-propiedad" que contiene "cod"
		      var idInstancia = tds[0].textContent;
		      
		      // Llamar a eliminarInstancia con idInstancia
		      recuperarInstancia(idInstancia);
		    }
        }

        toggleDeleteButtonVisibility();
        toggleRevertButtonVisibility();
        //toggleDeletedRowsVisibility();
    }


    // Función para eliminar fila
    function toggleDeleteRow(row, icon) {
        var deleteIcon = icon;
        //var editIcon = deleteIcon.previousElementSibling;
        var revertIcon = deleteIcon.nextElementSibling;

        deleteIcon.classList.toggle('hide');
        //editIcon.classList.toggle('hide');
        revertIcon.classList.toggle('hide');

        var tableRow = deleteIcon.closest('tr');
        tableRow.classList.toggle('deleted-row');

        //var checkbox = tableRow.querySelector('.rowCheckbox');
        //checkbox.style.display = tableRow.classList.contains('deleted-row') ? 'none' : 'inline-block';

        toggleDeleteButtonVisibility();
        toggleRevertButtonVisibility();
        //toggleDeletedRowsVisibility();
    }
    

    // Función para eliminar varias filas
    function eliminarCalendariosSeleccionados() {
	  var checkboxes = document.querySelectorAll('tr:not(.deleted-row) .rowCheckbox:checked');

	  for (var i = 0; i < checkboxes.length; i++) {
	    var checkbox = checkboxes[i];
	    var tableRow = checkbox.closest('tr');
	    var deleteIcon = tableRow.querySelector('.delete-icon');
	    var revertIcon = tableRow.querySelector('.revert-icon');
	    
	    // Buscar el atributo "data-propiedad" que contiene "cod"
	    var tds = tableRow.querySelectorAll('td[data-propiedad*="cod"]');
	    
	    if (tds.length > 0) {
	      // Tomar el valor del primer td encontrado con "data-propiedad" que contiene "cod"
	      var idInstancia = tds[0].textContent;
	      
	      // Llamar a eliminarInstancia con idInstancia
	      eliminarInstancia(idInstancia);
	    }

	    deleteIcon.classList.add('hide');
	    revertIcon.classList.remove('hide');
	    tableRow.classList.add('deleted-row');
	  }

	  // Eliminar el fondo gris de las filas eliminadas
	  var deletedRows = document.querySelectorAll('.deleted-row');
	  for (var j = 0; j < deletedRows.length; j++) {
	    deletedRows[j].classList.remove('clicked');
	  }

	  toggleDeleteButtonVisibility();
	  toggleRevertButtonVisibility();
	}



    


    window.onload = function () {
      var mainElement = document.querySelector('main'); // Obtener el elemento <main>
	  var cells = mainElement.querySelectorAll('td'); 
      for (var i = 0; i < cells.length; i++) {
        cells[i].addEventListener('click', function () {
          this.setAttribute('tabindex', '0');
          this.focus();
        });
        cells[i].addEventListener('blur', function () {
          this.removeAttribute('tabindex');
        });
      }

      var inputs = document.getElementsByClassName('search-input');
      for (var j = 0; j < inputs.length; j++) {
        inputs[j].addEventListener('input', function () {
          filterTable();
        });
      }

      toggleDeleteButtonVisibility();
      toggleRevertButtonVisibility();
      //toggleDeletedRowsVisibility();
    };

    function activarBusqueda(){
    var mainElement = document.querySelector('main'); // Obtener el elemento <main>
	var cells2 = mainElement.querySelectorAll('td'); 
      for (var i = 0; i < cells2.length; i++) {
        cells2[i].addEventListener('click', function () {
          this.setAttribute('tabindex', '0');
          this.focus();
        });
        cells2[i].addEventListener('blur', function () {
          this.removeAttribute('tabindex');
        });
      }

      var inputs2 = document.getElementsByClassName('search-input');
      for (var j = 0; j < inputs2.length; j++) {
        inputs2[j].addEventListener('input', function () {
          filterTable();
        });
      }

      toggleDeleteButtonVisibility();
      toggleRevertButtonVisibility();
      //toggleDeletedRowsVisibility();
    }

    function filterTable() {
      var table = document.getElementsByTagName('table')[0];
      var rows = table.getElementsByTagName('tr');

      for (var i = 1; i < rows.length; i++) {
        var row = rows[i];
        var shouldDisplayRow = true;

        var cells = row.getElementsByTagName('td');
        for (var j = 1; j < cells.length - 1; j++) { // Start from 1 to skip the checkbox column
          var cell = cells[j];
          var searchInput = document.getElementById('search' + (j - 1)); // Adjust the search input index

            if (searchInput) {
                var searchText = searchInput.value.toUpperCase();
                var cellText = cell.textContent || cell.innerText;

                // Si se está buscando por fecha, formatear la fecha de la misma manera que se muestra en el campo de entrada
                if (j === 4 && searchInput.type === "text" && searchInput.classList.contains("datepicker")) {
                // Obtener el valor del campo de fecha
                var selectedDate = $(searchInput).datepicker('getDate');

                // Formatear la fecha en el formato "dd/mm/yyyy"
                searchText = selectedDate ? selectedDate.toLocaleDateString('es-ES', { day: '2-digit', month: '2-digit', year: 'numeric' }) : '';
                }

                if (cellText.toUpperCase().indexOf(searchText) === -1) {
                shouldDisplayRow = false;
                break;
                }
            }
            
        }

        row.style.display = shouldDisplayRow ? '' : 'none';
      }
    }

    function eliminarInstancia(idInstancia){

    var lista = document.getElementById("lista_usuarios");
    var valorSeleccionado = lista.value;

    switch (valorSeleccionado) {
        case "Medicamento":
                fetch(`http://localhost:8080/medicamento/baja/${idInstancia}`, {
                method: 'PUT',
                headers: {
                  'Content-Type': 'application/json'
                },
              })
                .then(response => {
                  if (!response.ok) {
                    throw new Error('Error de red.'); // Manejo de errores si la respuesta no es exitosa (puedes personalizar esto)
                  }
                  return response.json(); // Parsear la respuesta a JSON si es una respuesta JSON
                })
                .then(data => {
                  // Hacer algo con los datos de la respuesta
                  actualizar(idInstancia, data);
                })
                .catch(error => {
                  // Manejar errores generales
                  console.error('Error:', error);
                });
            break;
        case "Consejo":
            fetch(`http://localhost:8080/consejo/baja/${idInstancia}`, {
                method: 'PUT',
                headers: {
                  'Content-Type': 'application/json'
                },
              })
                .then(response => {
                  if (!response.ok) {
                    throw new Error('Error de red.'); // Manejo de errores si la respuesta no es exitosa (puedes personalizar esto)
                  }
                  return response.json(); // Parsear la respuesta a JSON si es una respuesta JSON
                })
                .then(data => {
                  // Hacer algo con los datos de la respuesta
                  actualizar(idInstancia, data);
                })
                .catch(error => {
                  // Manejar errores generales
                  console.error('Error:', error);
                });
            break;
        case "FAQ":
            fetch(`http://localhost:8080/FAQ/get-all`, {
                method: 'GET',
                headers: {
                  'Content-Type': 'application/json'
                },
              })
                .then(response => {
                  if (!response.ok) {
                    throw new Error('Error de red.'); // Manejo de errores si la respuesta no es exitosa (puedes personalizar esto)
                  }
                  return response.json(); // Parsear la respuesta a JSON si es una respuesta JSON
                })
                .then(data => {
                  // Hacer algo con los datos de la respuesta
                  console.log(data);
                  reemplazarFilasConJSON(data);
                  activarBusqueda();

                })
                .catch(error => {
                  // Manejar errores generales
                  console.error('Error:', error);
                });
            break;
        case "Medicion":
            fetch(`http://localhost:8080/medicion/get-all`, {
                method: 'GET',
                headers: {
                  'Content-Type': 'application/json'
                },
              })
                .then(response => {
                  if (!response.ok) {
                    throw new Error('Error de red.'); // Manejo de errores si la respuesta no es exitosa (puedes personalizar esto)
                  }
                  return response.json(); // Parsear la respuesta a JSON si es una respuesta JSON
                })
                .then(data => {
                  // Hacer algo con los datos de la respuesta
                  console.log(data);
                  reemplazarFilasConJSON(data);
                  activarBusqueda();

                })
                .catch(error => {
                  // Manejar errores generales
                  console.error('Error:', error);
                });
            break;
        case "Sintoma":
            fetch(`http://localhost:8080/sintoma/get-all`, {
                method: 'GET',
                headers: {
                  'Content-Type': 'application/json'
                },
              })
                .then(response => {
                  if (!response.ok) {
                    throw new Error('Error de red.'); // Manejo de errores si la respuesta no es exitosa (puedes personalizar esto)
                  }
                  return response.json(); // Parsear la respuesta a JSON si es una respuesta JSON
                })
                .then(data => {
                  // Hacer algo con los datos de la respuesta
                  console.log(data);
                  reemplazarFilasConJSON(data);
                  activarBusqueda();

                })
                .catch(error => {
                  // Manejar errores generales
                  console.error('Error:', error);
                });
            break;
        case "AM":
            fetch(`http://localhost:8080/administracionmed/get-all`, {
                method: 'GET',
                headers: {
                  'Content-Type': 'application/json'
                },
              })
                .then(response => {
                  if (!response.ok) {
                    throw new Error('Error de red.'); // Manejo de errores si la respuesta no es exitosa (puedes personalizar esto)
                  }
                  return response.json(); // Parsear la respuesta a JSON si es una respuesta JSON
                })
                .then(data => {
                  // Hacer algo con los datos de la respuesta
                  console.log(data);
                  reemplazarFilasConJSON(data);
                  activarBusqueda();

                })
                .catch(error => {
                  // Manejar errores generales
                  console.error('Error:', error);
                });
            break;
        case "PM":
            fetch(`http://localhost:8080/presentacionMed/get-all`, {
                method: 'GET',
                headers: {
                  'Content-Type': 'application/json'
                },
              })
                .then(response => {
                  if (!response.ok) {
                    throw new Error('Error de red.'); // Manejo de errores si la respuesta no es exitosa (puedes personalizar esto)
                  }
                  return response.json(); // Parsear la respuesta a JSON si es una respuesta JSON
                })
                .then(data => {
                  // Hacer algo con los datos de la respuesta
                  console.log(data);
                  reemplazarFilasConJSON(data);
                  activarBusqueda();

                })
                .catch(error => {
                  // Manejar errores generales
                  console.error('Error:', error);
                });
            break;
        default:
            fetch(`http://localhost:8080/medicamento/get-all-genericos`, {
                method: 'GET',
                headers: {
                  'Content-Type': 'application/json'
                },
              })
                .then(response => {
                  if (!response.ok) {
                    throw new Error('Error de red.'); // Manejo de errores si la respuesta no es exitosa (puedes personalizar esto)
                  }
                  return response.json(); // Parsear la respuesta a JSON si es una respuesta JSON
                })
                .then(data => {
                  // Hacer algo con los datos de la respuesta
                  console.log(data);
                  reemplazarFilasConJSON(data);
                  activarBusqueda();

                })
                .catch(error => {
                  // Manejar errores generales
                  console.error('Error:', error);
                });
                    
            break;
    }

}

function recuperarInstancia(idInstancia){

    var lista = document.getElementById("lista_usuarios");
    var valorSeleccionado = lista.value;

    switch (valorSeleccionado) {
        case "Medicamento":
                fetch(`http://localhost:8080/medicamento/recuperar/${idInstancia}`, {
                method: 'PUT',
                headers: {
                  'Content-Type': 'application/json'
                },
              })
                .then(response => {
                  if (!response.ok) {
                    throw new Error('Error de red.'); // Manejo de errores si la respuesta no es exitosa (puedes personalizar esto)
                  }
                  return response.json(); // Parsear la respuesta a JSON si es una respuesta JSON
                })
                .then(data => {
                  // Hacer algo con los datos de la respuesta
                  actualizar(idInstancia, data);
                })
                .catch(error => {
                  // Manejar errores generales
                  console.error('Error:', error);
                });
            break;
        case "Consejo":
            fetch(`http://localhost:8080/consejo/recuperar/${idInstancia}`, {
                method: 'PUT',
                headers: {
                  'Content-Type': 'application/json'
                },
              })
                .then(response => {
                  if (!response.ok) {
                    throw new Error('Error de red.'); // Manejo de errores si la respuesta no es exitosa (puedes personalizar esto)
                  }
                  return response.json(); // Parsear la respuesta a JSON si es una respuesta JSON
                })
                .then(data => {
                  // Hacer algo con los datos de la respuesta
                  actualizar(idInstancia, data);
                })
                .catch(error => {
                  // Manejar errores generales
                  console.error('Error:', error);
                });
            break;
        case "FAQ":
            fetch(`http://localhost:8080/FAQ/get-all`, {
                method: 'GET',
                headers: {
                  'Content-Type': 'application/json'
                },
              })
                .then(response => {
                  if (!response.ok) {
                    throw new Error('Error de red.'); // Manejo de errores si la respuesta no es exitosa (puedes personalizar esto)
                  }
                  return response.json(); // Parsear la respuesta a JSON si es una respuesta JSON
                })
                .then(data => {
                  // Hacer algo con los datos de la respuesta
                  console.log(data);
                  reemplazarFilasConJSON(data);
                  activarBusqueda();

                })
                .catch(error => {
                  // Manejar errores generales
                  console.error('Error:', error);
                });
            break;
        case "Medicion":
            fetch(`http://localhost:8080/medicion/get-all`, {
                method: 'GET',
                headers: {
                  'Content-Type': 'application/json'
                },
              })
                .then(response => {
                  if (!response.ok) {
                    throw new Error('Error de red.'); // Manejo de errores si la respuesta no es exitosa (puedes personalizar esto)
                  }
                  return response.json(); // Parsear la respuesta a JSON si es una respuesta JSON
                })
                .then(data => {
                  // Hacer algo con los datos de la respuesta
                  console.log(data);
                  reemplazarFilasConJSON(data);
                  activarBusqueda();

                })
                .catch(error => {
                  // Manejar errores generales
                  console.error('Error:', error);
                });
            break;
        case "Sintoma":
            fetch(`http://localhost:8080/sintoma/get-all`, {
                method: 'GET',
                headers: {
                  'Content-Type': 'application/json'
                },
              })
                .then(response => {
                  if (!response.ok) {
                    throw new Error('Error de red.'); // Manejo de errores si la respuesta no es exitosa (puedes personalizar esto)
                  }
                  return response.json(); // Parsear la respuesta a JSON si es una respuesta JSON
                })
                .then(data => {
                  // Hacer algo con los datos de la respuesta
                  console.log(data);
                  reemplazarFilasConJSON(data);
                  activarBusqueda();

                })
                .catch(error => {
                  // Manejar errores generales
                  console.error('Error:', error);
                });
            break;
        case "AM":
            fetch(`http://localhost:8080/administracionmed/get-all`, {
                method: 'GET',
                headers: {
                  'Content-Type': 'application/json'
                },
              })
                .then(response => {
                  if (!response.ok) {
                    throw new Error('Error de red.'); // Manejo de errores si la respuesta no es exitosa (puedes personalizar esto)
                  }
                  return response.json(); // Parsear la respuesta a JSON si es una respuesta JSON
                })
                .then(data => {
                  // Hacer algo con los datos de la respuesta
                  console.log(data);
                  reemplazarFilasConJSON(data);
                  activarBusqueda();

                })
                .catch(error => {
                  // Manejar errores generales
                  console.error('Error:', error);
                });
            break;
        case "PM":
            fetch(`http://localhost:8080/presentacionMed/get-all`, {
                method: 'GET',
                headers: {
                  'Content-Type': 'application/json'
                },
              })
                .then(response => {
                  if (!response.ok) {
                    throw new Error('Error de red.'); // Manejo de errores si la respuesta no es exitosa (puedes personalizar esto)
                  }
                  return response.json(); // Parsear la respuesta a JSON si es una respuesta JSON
                })
                .then(data => {
                  // Hacer algo con los datos de la respuesta
                  console.log(data);
                  reemplazarFilasConJSON(data);
                  activarBusqueda();

                })
                .catch(error => {
                  // Manejar errores generales
                  console.error('Error:', error);
                });
            break;
        default:
            fetch(`http://localhost:8080/medicamento/get-all-genericos`, {
                method: 'GET',
                headers: {
                  'Content-Type': 'application/json'
                },
              })
                .then(response => {
                  if (!response.ok) {
                    throw new Error('Error de red.'); // Manejo de errores si la respuesta no es exitosa (puedes personalizar esto)
                  }
                  return response.json(); // Parsear la respuesta a JSON si es una respuesta JSON
                })
                .then(data => {
                  // Hacer algo con los datos de la respuesta
                  console.log(data);
                  reemplazarFilasConJSON(data);
                  activarBusqueda();

                })
                .catch(error => {
                  // Manejar errores generales
                  console.error('Error:', error);
                });
                    
            break;
    }

}

function actualizar(idInstancia, data){
  var tabla = document.querySelector("table");
  var filas = tabla.querySelectorAll("tbody tr");
	for (var i = 0; i < filas.length; i++) {
	      var idFila = filas[i].cells[1].textContent; // Suponiendo que el segundo td de cada fila contiene el id
	      if (idFila === idInstancia) {
	        actualizarFila(filas[i], data); // Llama a una función para actualizar la fila
	        break; // Deja de buscar después de encontrar la fila
	      }
	}
}

function actualizarFila(fila, nuevosDatos) {
  var propiedades = Object.keys(nuevosDatos);

  for (var j = 0; j < propiedades.length; j++) {
    var propiedad = propiedades[j];
    var celda = fila.querySelector(`td[data-propiedad="${propiedad}"]`);

    if (celda) {
      if (!propiedad.includes("fecha") && typeof nuevosDatos[propiedad] === "object" && nuevosDatos[propiedad] !== null && nuevosDatos[propiedad] !== false) {
        celda.textContent = getFirstAttribute(nuevosDatos[propiedad]);
      } else if (propiedad.includes("fecha") && nuevosDatos[propiedad] !== null) {
        var partesFecha = nuevosDatos[propiedad].split('-'); // Divide la cadena en partes

		  // Crea un objeto de fecha usando las partes divididas
		  var fecha = new Date(
		    parseInt(partesFecha[0]), // Año
		    parseInt(partesFecha[1]) - 1, // Mes (restamos 1 porque los meses en JavaScript son de 0 a 11)
		    parseInt(partesFecha[2]) // Día
		  );

		  celda.textContent = fecha.toLocaleDateString();
      } else {
        celda.textContent = nuevosDatos[propiedad] !== null && nuevosDatos[propiedad] !== false
          ? nuevosDatos[propiedad]
          : "-";
      }
    }
  }
}

