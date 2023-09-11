const http = require('http');
const fs = require('fs');
const path = require('path');

const server = http.createServer((req, res) => {
  if (req.url === '/') {
    // Redirige al cliente a n1_inicio_sesion.html cuando se accede al directorio raíz
    res.writeHead(302, { 'Location': '/n1_inicio_sesion.html' });
    res.end();
    return;
  }

  // Ruta completa del archivo solicitado
  const filePath = path.join(__dirname, req.url);

  fs.readFile(filePath, (err, data) => {
    if (err) {
      // Manejo de errores
      res.writeHead(404, { 'Content-Type': 'text/plain' });
      res.end('404 Not Found');
      return;
    }

    // Define el tipo de contenido según la extensión del archivo
    const extension = path.extname(filePath);
    let contentType = 'text/html';
    if (extension === '.css') {
      contentType = 'text/css';
    } else if (extension === '.js') {
      contentType = 'application/javascript';
    }

    // Establece las cabeceras de respuesta
    res.writeHead(200, { 'Content-Type': contentType });

    // Envía el contenido del archivo como respuesta
    res.end(data);
  });
});

const PORT = 8081;
server.listen(PORT, () => {
  console.log(`Servidor en funcionamiento en el puerto ${PORT}`);
});
