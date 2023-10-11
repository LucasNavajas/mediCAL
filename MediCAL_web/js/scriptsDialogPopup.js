 // JavaScript aquí
        function openDialog(id) {
            var overlay = document.getElementById("popup-overlay");
            var dialog = document.getElementById(id);
            overlay.style.display = "block";
            overlay.style.opacity = "1";
            dialog.style.display = "block";
            var spans = document.querySelectorAll('span[id^="contador"]');
            for (var i = 0; i < spans.length; i++) {
                var span = spans[i];
                // Por ejemplo, puedes establecer el contenido del span en su valor original.
                span.textContent = "300 caracteres restantes";
            }
            var inputElements = dialog.querySelectorAll('input, textarea');

            // Itera sobre los elementos y establece su valor en una cadena vacía
            inputElements.forEach(function(input) {
                input.value = '';
            });
        }

        function openDialogModificar(id, index) {
            var overlay = document.getElementById("popup-overlay");
            var dialog = document.getElementById(id);
            setearValoresAModificar(id, index);
            overlay.style.display = "block";
            overlay.style.opacity = "1";
            dialog.style.display = "block";
        }

        function openDialogBajaTodos(id){
          var overlay = document.getElementById("popup-overlay");
          var dialog = document.getElementById(id);
          var aceptar = dialog.querySelector(".accept-button");
          var aceptarNuevo = aceptar.cloneNode(true);
          aceptar.parentNode.replaceChild(aceptarNuevo, aceptar);
          function handleAceptarClick() {
              eliminarCalendariosSeleccionados();
          }

          // Agrega el nuevo controlador de eventos
          aceptarNuevo.addEventListener("click", handleAceptarClick);
          overlay.style.display = "block";
          overlay.style.opacity = "1";
          dialog.style.display = "block";
        }

        function openDialogBaja(id, idInstancia, index, esto) {
          var overlay = document.getElementById("popup-overlay");
          var dialog = document.getElementById(id);
          var aceptar = dialog.querySelector(".accept-button");
          var inputElements = dialog.querySelectorAll('input, textarea');

            // Itera sobre los elementos y establece su valor en una cadena vacía
            inputElements.forEach(function(input) {
                input.value = '';
            });
          var aceptarNuevo = aceptar.cloneNode(true);
          aceptar.parentNode.replaceChild(aceptarNuevo, aceptar);

          if(window.location.href == "http://localhost:8081/n22_gestionar_perfiles.html"){
            function agregarMotivo(){
              openDialogMotivo('popup-dialog-agregar-motivo', idInstancia, index, esto);
            }
            aceptarNuevo.addEventListener("click", agregarMotivo);
          }
          else if(window.location.href== "http://localhost:8081/n26_gestionar_usuarios.html"){

             function agregarMotivoUsuario(){
              openDialogMotivoUsuario('popup-dialog-motivo', idInstancia, index, esto);
            }
            aceptarNuevo.addEventListener("click", agregarMotivoUsuario);
          }

          else{
          // Define el nuevo controlador de eventos para el botón "aceptar"
          function handleAceptarClick() {
              eliminarInstancia(idInstancia);
              toggleDeleteRow(index, esto);
          }

          // Agrega el nuevo controlador de eventos
          aceptarNuevo.addEventListener("click", handleAceptarClick);
          }

          overlay.style.display = "block";
          overlay.style.opacity = "1";
          dialog.style.display = "block";
      }

      function openDialogMotivoUsuario(id, idInstancia, index, esto) {
            var overlay = document.getElementById("popup-overlay");
            var dialog = document.getElementById(id);
            var usuario = document.getElementById("campo_usuario_baja");
            var motivo = document.getElementById("campo_motivo_baja");
            var spans = document.querySelectorAll('span[id^="contador"]');
            for (var i = 0; i < spans.length; i++) {
                var span = spans[i];
                // Por ejemplo, puedes establecer el contenido del span en su valor original.
                span.textContent = "300 caracteres restantes";
            }
            const tabla = document.querySelector("table");
            const fila = tabla.rows[index+1];
            usuario.value = fila.cells[2].textContent;

            var aceptar = dialog.querySelector("#eliminar-button-motivo");
            var aceptarNuevo = aceptar.cloneNode(true);
            function handleAceptarClick() {
              eliminarUsuario(fila.cells[1].textContent, motivo.value);
              toggleDeleteRow(index, esto);
              closeDialog(id);
          }

          // Agrega el nuevo controlador de eventos
          aceptarNuevo.addEventListener("click", handleAceptarClick);
            aceptar.parentNode.replaceChild(aceptarNuevo, aceptar);
            overlay.style.display = "block";
            overlay.style.opacity = "1";
            dialog.style.display = "block";
        }


        function openDialogMotivo(id, idInstancia, index, esto) {
            var overlay = document.getElementById("popup-overlay");
            var dialog = document.getElementById(id);
            var perfil = document.getElementById("campo_perfil");
            var motivo = document.getElementById("campo_motivo");
            var spans = document.querySelectorAll('span[id^="contador"]');
            for (var i = 0; i < spans.length; i++) {
                var span = spans[i];
                // Por ejemplo, puedes establecer el contenido del span en su valor original.
                span.textContent = "300 caracteres restantes";
            }
            const tabla = document.querySelector("table");
            const fila = tabla.rows[index+1];
            console.log(fila);
            perfil.value = fila.cells[6].textContent;

            var aceptar = dialog.querySelector("#eliminar-button-motivo");
            var aceptarNuevo = aceptar.cloneNode(true);
            function handleAceptarClick() {

              crearJSONPerfilBaja(idInstancia, motivo.value);
              toggleDeleteRow(index, esto);
          }

          // Agrega el nuevo controlador de eventos
          aceptarNuevo.addEventListener("click", handleAceptarClick);
            aceptar.parentNode.replaceChild(aceptarNuevo, aceptar);
            overlay.style.display = "block";
            overlay.style.opacity = "1";
            dialog.style.display = "block";
        }

        function closeDialog(id) {
            var overlay = document.getElementById("popup-overlay");
            var dialog = document.getElementById(id);
            overlay.style.display = "none";
            overlay.style.opacity = "0";
            dialog.style.display = "none";
        }

        function cancelDialog(id) {
            var overlay = document.getElementById("popup-overlay");
            var dialog = document.getElementById(id);
            overlay.style.display = "none";
            overlay.style.opacity = "0";
            dialog.style.display = "none";
        }