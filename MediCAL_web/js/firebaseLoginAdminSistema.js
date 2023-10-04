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
        if(usuarioJson.perfil.codPerfil == 2){
            // El usuario ha iniciado sesión, puedes realizar acciones para usuarios autenticados aquí
            console.log("Usuario autenticado:", usuarioJson.usuarioUnico);
        }
        else{
            // El usuario no ha iniciado sesión, puedes redirigirlo a la página de inicio de sesión o realizar otras acciones
            console.log("Usuario no autenticado");
            // Ejemplo de redirección a la página de inicio de sesión
            localStorage.removeItem('usuario');
            window.location.href = "n1_inicio_sesion.html";
        }
      } else {
        // El usuario no ha iniciado sesión, puedes redirigirlo a la página de inicio de sesión o realizar otras acciones
        console.log("Usuario no autenticado");
        // Ejemplo de redirección a la página de inicio de sesión
        window.location.href = "n1_inicio_sesion.html";
      }
    });

    function signIn(email, password) {

  fetch(`http://localhost:8080/usuario/mail/${email}`, {
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
      if (data.perfil && data.perfil.codPerfil === 2) {
        // Iniciar sesión con correo y contraseña
        firebase.auth().signInWithEmailAndPassword(email, password)
          .then(function (userCredential) {
            // Inicio de sesión exitoso, puedes redirigir al usuario a la página deseada
            // Convierte el objeto JSON en una cadena JSON utilizando JSON.stringify
            var usuarioString = JSON.stringify(data);
            // Almacena la cadena JSON en sessionStorage bajo un nombre de clave
            localStorage.setItem('usuario', usuarioString);
            window.location.href = "n18_pantalla_principal_admin_sistema.html";
          })
          .catch(function (error) {
            window.location.href = "n1_inicio_sesion.html";
          });
      }
    })
    .catch(error => {
         window.location.href = "n1_inicio_sesion.html";
    });
}