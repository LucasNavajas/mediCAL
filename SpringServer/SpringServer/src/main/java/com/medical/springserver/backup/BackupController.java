package com.medical.springserver.backup;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class BackupController {

    @GetMapping("/backup")
    public ResponseEntity<FileSystemResource> realizarCopiaDeSeguridad() {
        String dbUsername = "root";
        String dbPassword = "root";
        String dbName = "springserverdb";
        
        // Genera un nombre de archivo basado en la fecha y hora actual
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String outputFile = "copia_de_seguridad_" + dateFormat.format(new Date()) + ".sql";

        try {
            boolean success = DatabaseUtil.backup(dbUsername, dbPassword, dbName, outputFile);
            if (success) {
                // Configura los encabezados para indicar que se trata de una descarga
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Disposition", "attachment; filename=" + outputFile);

                // Lee el archivo de copia de seguridad y lo envía como respuesta
                FileSystemResource resource = new FileSystemResource(outputFile);
                return ResponseEntity.ok()
                        .headers(headers)
                        .contentLength(resource.contentLength())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            } else {
                return ResponseEntity.status(500).body(null); // Error en la copia de seguridad
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null); // Error en la copia de seguridad
        }
    }
    
    @PostMapping("/upload-sql")
    public String uploadSQL(@RequestParam("sqlFile") MultipartFile sqlFile) {
        if (sqlFile.isEmpty()) {
            return "Archivo SQL no válido";
        }

        try {
            // Leer el archivo SQL
            InputStream inputStream = sqlFile.getInputStream();
            byte[] sqlBytes = inputStream.readAllBytes();
            String sqlScript = new String(sqlBytes);

            // Establecer la conexión con la base de datos (MySQL en este caso)
            String url = "jdbc:mysql://localhost:3306/SpringServerDB";
            String username = "root";
            String password = "root";
            Connection connection = DriverManager.getConnection(url, username, password);

            // Ejecutar el script SQL
            Statement statement = connection.createStatement();
            statement.execute(sqlScript);

            // Cerrar la conexión
            statement.close();
            connection.close();

            return "Script SQL ejecutado con éxito";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error al procesar el archivo SQL";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al ejecutar el script SQL";
        }
    }
}


