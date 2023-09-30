 // JavaScript aquí
        function openDialog(id) {
            var overlay = document.getElementById("popup-overlay");
            var dialog = document.getElementById(id);
            overlay.style.display = "block";
            overlay.style.opacity = "1";
            dialog.style.display = "block";
        }

        function openDialogModificar(id, index) {
            var overlay = document.getElementById("popup-overlay");
            var dialog = document.getElementById(id);
            setearValoresAModificar(id, index);
            overlay.style.display = "block";
            overlay.style.opacity = "1";
            dialog.style.display = "block";
        }

        function openDialogBaja(id, idInstancia, index, esto) {
          var overlay = document.getElementById("popup-overlay");
          var dialog = document.getElementById(id);
          var aceptar = dialog.querySelector(".accept-button");
          var aceptarNuevo = aceptar.cloneNode(true);
          aceptar.parentNode.replaceChild(aceptarNuevo, aceptar);

          // Define el nuevo controlador de eventos para el botón "aceptar"
          function handleAceptarClick() {
              eliminarInstancia(idInstancia);
              toggleDeleteRow(index, esto);
          }

          // Agrega el nuevo controlador de eventos
          aceptarNuevo.addEventListener("click", handleAceptarClick);

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