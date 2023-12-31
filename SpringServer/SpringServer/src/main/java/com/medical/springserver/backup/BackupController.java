package com.medical.springserver.backup;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class BackupController {
	private String outputFile;

    @GetMapping("/backup")
    public ResponseEntity<FileSystemResource> realizarCopiaDeSeguridad() {
        String dbUsername = "root";
        String dbPassword = "root";
        String dbName = "springserverdb";
        
        // Genera un nombre de archivo basado en la fecha y hora actual
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        outputFile = "Copia_seguridad_BD_V" + dateFormat.format(new Date()) + ".sql";

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
    
    @Scheduled(fixedRate = 86400000) // 24 horas en milisegundos
    public void realizarCopiaDeSeguridadProgramada() {
    	 String dbUsername = "root";
         String dbPassword = "root";
         String dbName = "springserverdb";
        // Obtén el nombre del día de la semana actual (por ejemplo, "lunes")
        SimpleDateFormat dayOfWeekFormat = new SimpleDateFormat("EEEE");
        String dayOfWeek = dayOfWeekFormat.format(new Date());

        // Genera el nombre del archivo basado en el día de la semana
        String outputFile = "Copia_seguridad_BD_" + dayOfWeek + ".sql";

        try {
            boolean success = DatabaseUtil.backup(dbUsername, dbPassword, dbName, outputFile);
            if (success) {
                System.out.println("Backup realizado con éxito");
            } else {
                System.out.println("Error al realizar el backup");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("Error al realizar el backup");
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
            byte[] sqlBytes = sqlFile.getBytes();
            String sqlScript = new String(sqlBytes, "UTF-8");

            // Establecer la conexión con la base de datos (MySQL en este caso)
            String url = "jdbc:mysql://localhost:3306/SpringServerDB";
            String username = "root";
            String password = "root";

            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                String[] sqlStatements = sqlScript.split(";");
                for (String sqlStatement : sqlStatements) {
                    if (!sqlStatement.trim().isEmpty()) {
                        try (Statement statement = connection.createStatement()) {
                            statement.execute(sqlStatement); // Usar execute() en lugar de executeUpdate()
                        } catch (SQLException e) {
                            e.printStackTrace();
                            return "Error al ejecutar el script SQL: " + e.getMessage();
                        }
                    }
                }
                return "Script SQL ejecutado con éxito";
            } catch (SQLException e) {
                e.printStackTrace();
                return "Error al ejecutar el script SQL: " + e.getMessage();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error al procesar el archivo SQL";
        }
    }
}




