const firebaseConfig = {
    apiKey: "AIzaSyCgcpZYePxoQHoPsQNiJ-Xfk4StvJ8x26g",
    authDomain: "medicalauth-9419f.firebaseapp.com",
    projectId: "medicalauth-9419f",
    storageBucket: "medicalauth-9419f.appspot.com",
    messagingSenderId: "42219694888",
    appId: "1:42219694888:web:0f9007b08d4654eb24be1d",
    measurementId: "G-3NMHDZCCK1"
  };

    // Inicializa Firebase
    firebase.initializeApp(firebaseConfig);

    // Comprueba el estado de autenticación al cargar la página
    firebase.auth().onAuthStateChanged(function(user) {
      if (user) {
        var usuario = localStorage.getItem('usuario');

        // Convierte la cadena JSON nuevamente en un objeto JavaScript utilizando JSON.parse
        var usuarioJson = JSON.parse(usuario);
        if(usuarioJson.perfil.codPerfil == 3){
            // El usuario ha iniciado sesión, puedes realizar acciones para usuarios autenticados aquí
        console.log("Usuario autenticado:", usuarioJson.usuarioUnico);
        }
        else{
            // El usuario no ha iniciado sesión, puedes redirigirlo a la página de inicio de sesión o realizar otras acciones
            console.log("Usuario no autenticado");
            // Ejemplo de redirección a la página de inicio de sesión
            localStorage.removeItem('usuario');
            openDialogPermisos('popup-dialog-no-permisos');
        }
      } else {
        // El usuario no ha iniciado sesión, puedes redirigirlo a la página de inicio de sesión o realizar otras acciones
        console.log("Usuario no autenticado");
        // Ejemplo de redirección a la página de inicio de sesión
        openDialogPermisos('popup-dialog-no-permisos');
      }
    });