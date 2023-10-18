package com.medical.springserver.backup;

import java.io.IOException;

public final class DatabaseUtil {
	    public static boolean backup(String dbUsername, String dbPassword, String dbName, String outputFile)
	            throws IOException, InterruptedException {
	    	String mysqldumpPath = "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump"; // Ruta completa al ejecutable mysqldump
	    	String command = String.format("%s -u%s -p%s --add-drop-table --databases %s -r %s",
	    	        mysqldumpPath, dbUsername, dbPassword, dbName, outputFile);
	        Process process = Runtime.getRuntime().exec(command);
	        int processComplete = process.waitFor();
	        return processComplete == 0;
	    }
	}
