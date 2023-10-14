-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: springserverdb
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `springserverdb`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `springserverdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `springserverdb`;

--
-- Table structure for table `administracion_med`
--

DROP TABLE IF EXISTS `administracion_med`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `administracion_med` (
  `cod_administracion_med` int NOT NULL AUTO_INCREMENT,
  `desc_administracion_med` varchar(255) DEFAULT NULL,
  `fecha_alta_administracion_med` date DEFAULT NULL,
  `fecha_fin_vigenciaam` date DEFAULT NULL,
  `nombre_administracion_med` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cod_administracion_med`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administracion_med`
--

LOCK TABLES `administracion_med` WRITE;
/*!40000 ALTER TABLE `administracion_med` DISABLE KEYS */;
INSERT INTO `administracion_med` VALUES (1,'El medicamento es introducido en el organismo a través de la boca, donde es deglutido, pasa al estómago y al intestino, donde es absorbido y desde donde ejerce su acción terapéutica.','2023-10-13',NULL,'De forma oral'),(2,'El medicamento es introducido en el organismo a través de la naríz, ojos u oídos','2023-10-13',NULL,'Por la naríz / los ojos / los oídos'),(3,'Consiste en la introducción de sustancias medicamentosas en el tejido muscular, usada principalmente en aquellos casos en que se quiere una mayor rapidez, pero no puede ser administrado por la vía venosa, como por ejemplo, las sustancias liposolubles.','2023-10-13',NULL,'De forma inyectable'),(4,'Se refiere a la aplicación de medicamentos, cremas, ungüentos o soluciones directamente sobre la superficie de la piel. Esta vía de administración permite que el medicamento actúe localmente en la piel o en las áreas cercanas al lugar de aplicación.','2023-10-13',NULL,'De manera tópica (sobre la piel)');
/*!40000 ALTER TABLE `administracion_med` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `calendario`
--

DROP TABLE IF EXISTS `calendario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `calendario` (
  `cod_calendario` int NOT NULL AUTO_INCREMENT,
  `fecha_alta_calendario` date DEFAULT NULL,
  `fecha_fin_vigenciac` date DEFAULT NULL,
  `nombre_calendario` varchar(255) DEFAULT NULL,
  `nombre_paciente` varchar(255) DEFAULT NULL,
  `relacion_calendario` varchar(255) DEFAULT NULL,
  `cod_usuario` int DEFAULT NULL,
  PRIMARY KEY (`cod_calendario`),
  KEY `FKka00l4v5ptg8lny7l161n45fg` (`cod_usuario`),
  CONSTRAINT `FKka00l4v5ptg8lny7l161n45fg` FOREIGN KEY (`cod_usuario`) REFERENCES `usuario` (`cod_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calendario`
--

LOCK TABLES `calendario` WRITE;
/*!40000 ALTER TABLE `calendario` DISABLE KEYS */;
/*!40000 ALTER TABLE `calendario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `calendario_medicion`
--

DROP TABLE IF EXISTS `calendario_medicion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `calendario_medicion` (
  `cod_calendario_medicion` int NOT NULL AUTO_INCREMENT,
  `fecha_calendario_medicion` datetime(6) DEFAULT NULL,
  `fecha_fin_vigenciacm` date DEFAULT NULL,
  `valor_calendario_medicion` float NOT NULL,
  `cod_calendario` int DEFAULT NULL,
  `cod_medicion` int DEFAULT NULL,
  PRIMARY KEY (`cod_calendario_medicion`),
  KEY `FKbkkaklmmcu7w61y6e6pj67lbl` (`cod_calendario`),
  KEY `FKjc1g7g99u0xc0wy3tsinmxvwx` (`cod_medicion`),
  CONSTRAINT `FKbkkaklmmcu7w61y6e6pj67lbl` FOREIGN KEY (`cod_calendario`) REFERENCES `calendario` (`cod_calendario`),
  CONSTRAINT `FKjc1g7g99u0xc0wy3tsinmxvwx` FOREIGN KEY (`cod_medicion`) REFERENCES `medicion` (`cod_medicion`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calendario_medicion`
--

LOCK TABLES `calendario_medicion` WRITE;
/*!40000 ALTER TABLE `calendario_medicion` DISABLE KEYS */;
INSERT INTO `calendario_medicion` VALUES (1,'2023-10-13 16:34:44.937911',NULL,0,NULL,1),(2,'2023-10-13 16:34:44.937911',NULL,0,NULL,2),(3,'2023-10-13 16:34:44.937911',NULL,0,NULL,3),(4,'2023-10-13 16:34:44.937911',NULL,0,NULL,4),(5,'2023-10-13 16:34:44.937911',NULL,0,NULL,5),(6,'2023-10-13 16:34:44.937911',NULL,0,NULL,6),(7,'2023-10-13 16:34:44.937911',NULL,0,NULL,7),(8,'2023-10-13 16:34:44.937911',NULL,0,NULL,8),(9,'2023-10-13 16:34:44.937911',NULL,0,NULL,9),(10,'2023-10-13 16:34:44.937911',NULL,0,NULL,10),(11,'2023-10-13 16:34:44.937911',NULL,0,NULL,11);
/*!40000 ALTER TABLE `calendario_medicion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `calendario_sintoma`
--

DROP TABLE IF EXISTS `calendario_sintoma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `calendario_sintoma` (
  `cod_calendario_sintoma` int NOT NULL AUTO_INCREMENT,
  `fecha_calendario_sintoma` datetime(6) DEFAULT NULL,
  `fecha_fin_vigenciacs` date DEFAULT NULL,
  `cod_calendario` int DEFAULT NULL,
  `cod_sintoma` int DEFAULT NULL,
  PRIMARY KEY (`cod_calendario_sintoma`),
  KEY `FKpdahy791qfuorthufusmwxic6` (`cod_calendario`),
  KEY `FKkof59bj6smoxrqtphhgp3ny6i` (`cod_sintoma`),
  CONSTRAINT `FKkof59bj6smoxrqtphhgp3ny6i` FOREIGN KEY (`cod_sintoma`) REFERENCES `sintoma` (`cod_sintoma`),
  CONSTRAINT `FKpdahy791qfuorthufusmwxic6` FOREIGN KEY (`cod_calendario`) REFERENCES `calendario` (`cod_calendario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calendario_sintoma`
--

LOCK TABLES `calendario_sintoma` WRITE;
/*!40000 ALTER TABLE `calendario_sintoma` DISABLE KEYS */;
/*!40000 ALTER TABLE `calendario_sintoma` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `codigo_verificacion`
--

DROP TABLE IF EXISTS `codigo_verificacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `codigo_verificacion` (
  `cod_verificacion` varchar(255) NOT NULL,
  `fecha_generado` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`cod_verificacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `codigo_verificacion`
--

LOCK TABLES `codigo_verificacion` WRITE;
/*!40000 ALTER TABLE `codigo_verificacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `codigo_verificacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `concentracion`
--

DROP TABLE IF EXISTS `concentracion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `concentracion` (
  `cod_concentracion` int NOT NULL AUTO_INCREMENT,
  `unidad_medidac` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cod_concentracion`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concentracion`
--

LOCK TABLES `concentracion` WRITE;
/*!40000 ALTER TABLE `concentracion` DISABLE KEYS */;
INSERT INTO `concentracion` VALUES (1,'g'),(2,'mg'),(3,'ml'),(4,'mcg');
/*!40000 ALTER TABLE `concentracion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `consejo`
--

DROP TABLE IF EXISTS `consejo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `consejo` (
  `nro_consejo` int NOT NULL AUTO_INCREMENT,
  `auspiciante` varchar(255) DEFAULT NULL,
  `desc_consejo` varchar(255) DEFAULT NULL,
  `fecha_alta_consejo` date DEFAULT NULL,
  `fecha_fin_vigencia_consejo` date DEFAULT NULL,
  `foto_consejo` varchar(255) DEFAULT NULL,
  `link_consejo` varchar(255) DEFAULT NULL,
  `nombre_consejo` varchar(255) DEFAULT NULL,
  `nro_tipo_consejo` int DEFAULT NULL,
  PRIMARY KEY (`nro_consejo`),
  KEY `FK3dbxbe2hcdp4hckdulclkov1n` (`nro_tipo_consejo`),
  CONSTRAINT `FK3dbxbe2hcdp4hckdulclkov1n` FOREIGN KEY (`nro_tipo_consejo`) REFERENCES `tipo_consejo` (`nro_tipo_consejo`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consejo`
--

LOCK TABLES `consejo` WRITE;
/*!40000 ALTER TABLE `consejo` DISABLE KEYS */;
INSERT INTO `consejo` VALUES (1,'MediCAL','¡Es fácil añadir un contacto a supervisar!  Acceda al menú desplegable, seleccione “Añadir contacto” y busque por el nombre de usuario. ','2023-10-13',NULL,NULL,'null','Su consejo diario de MediCAL!',3),(2,NULL,'Dormir bien es muy importante. No dormir lo suficiente puede afectar tus hormonas, así como tu salud física y mental.','2023-10-13',NULL,NULL,'https://www.nhlbi.nih.gov/es/salud/sueno/por-que-el-sueno-es-importante','Consejo Diario de Bienestar y Salud',2),(3,'Bagó','7 Cuidados básicos para personas con hipertensión.','2023-10-13',NULL,'https://img.youtube.com/vi/a2EuM2w4LrI/maxresdefault.jpg','https://www.youtube.com/watch?v=a2EuM2w4LrI','Consejo Médico Auspiciado',1),(4,'MediCAL','¡Es fácil añadir un síntoma nuevo!  Pulse en “Más” de la barra inferior de herramientas, seleccione “Mediciones y Síntomas”, luego Agregue un Seguimiento sobre Síntomas y seleccione entre las opciones aquellos síntomas que desee guardar. ','2023-10-13',NULL,NULL,'null','Su consejo diario de MediCAL!',3),(5,NULL,'El agua es esencial para una buena salud. ¿Estás consumiendo lo suficiente? Estas pautas pueden ayudarte a averiguarlo.','2023-10-13',NULL,NULL,'https://www.mayoclinic.org/es/healthy-lifestyle/nutrition-and-healthy-eating/in-depth/water/art-20044256#:~:text=%C2%BFCu%C3%A1les%20son%20los%20beneficios%20para,necesita%20agua%20para%20funcionar%20correctamente.','Consejo Diario de Bienestar y Salud',2),(6,'OSEP','¡Taller de la Memoria! La doctora Paula Fachinelli, del Departamento de Gestión de Políticas Sociales para el Adulto Mayor de OSEP, nos brinda lo mejor del Taller de Estimulación Cognitiva','2023-10-13',NULL,'https://img.youtube.com/vi/TZh2EOK_Hms/maxresdefault.jpg','https://youtu.be/TZh2EOK_Hms','Consejo Médico Auspiciado',1);
/*!40000 ALTER TABLE `consejo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dosis`
--

DROP TABLE IF EXISTS `dosis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dosis` (
  `cod_dosis` int NOT NULL AUTO_INCREMENT,
  `cantidad_dosis` float NOT NULL,
  `valor_concentracion` float NOT NULL,
  `cod_concentraion` int DEFAULT NULL,
  PRIMARY KEY (`cod_dosis`),
  KEY `FKg5m8obopmpaqo7cbf98tfoflu` (`cod_concentraion`),
  CONSTRAINT `FKg5m8obopmpaqo7cbf98tfoflu` FOREIGN KEY (`cod_concentraion`) REFERENCES `concentracion` (`cod_concentracion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dosis`
--

LOCK TABLES `dosis` WRITE;
/*!40000 ALTER TABLE `dosis` DISABLE KEYS */;
/*!40000 ALTER TABLE `dosis` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estado_solicitud`
--

DROP TABLE IF EXISTS `estado_solicitud`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estado_solicitud` (
  `cod_estado_solicitud` int NOT NULL AUTO_INCREMENT,
  `fecha_fin_vigenciaes` date DEFAULT NULL,
  `nombre_estado_solicitud` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cod_estado_solicitud`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estado_solicitud`
--

LOCK TABLES `estado_solicitud` WRITE;
/*!40000 ALTER TABLE `estado_solicitud` DISABLE KEYS */;
INSERT INTO `estado_solicitud` VALUES (1,NULL,'Aceptada'),(2,NULL,'Rechazada'),(3,NULL,'Baja'),(4,NULL,'Pendiente'),(5,NULL,'Aceptado sin visualizar'),(6,NULL,'Rechazado sin visualizar'),(7,NULL,'Desvinculado sin visualizar Supervisor'),(8,NULL,'Desvinculado sin visualizar Supervisado');
/*!40000 ALTER TABLE `estado_solicitud` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faq`
--

DROP TABLE IF EXISTS `faq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `faq` (
  `codfaq` int NOT NULL AUTO_INCREMENT,
  `fecha_actualizacionfaq` date DEFAULT NULL,
  `fecha_fin_vigenciafaq` date DEFAULT NULL,
  `preguntatfaq` varchar(1000) DEFAULT NULL,
  `respuestafaq` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`codfaq`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faq`
--

LOCK TABLES `faq` WRITE;
/*!40000 ALTER TABLE `faq` DISABLE KEYS */;
INSERT INTO `faq` VALUES (1,'2023-10-13',NULL,'¿Cómo puedo crear una cuenta en la aplicación MediCAL?','Para crear una cuenta en MediCAL, abre la aplicación móvil y selecciona el botón \"CREAR CUENTA\" en la pantalla de inicio. Asegúrate de aceptar los Términos y Condiciones de Uso y la Política de Privacidad, y proporciona la información requerida, incluyendo un nombre de usuario único, una contraseña y un correo electrónico válido. Una vez validada la cuenta, podrás comenzar a utilizar la aplicación.'),(2,'2023-10-13',NULL,'¿Qué debo hacer si olvidé mi contraseña para iniciar sesión en MediCAL?','Si olvidaste tu contraseña, selecciona la opción \"¿Olvidaste tu contraseña?\" en la pantalla de inicio de sesión. Ingresa tu correo electrónico asociado a la cuenta y sigue las instrucciones para recibir un código de verificación. Luego, valida el código recibido y establece una nueva contraseña para acceder a tu cuenta.'),(3,'2023-10-13',NULL,'¿Cómo puedo asociar un medicamento y establecer un recordatorio en la aplicación MediCAL?','Para asociar un medicamento y establecer un recordatorio, selecciona el botón \"AGREGAR RECORDATORIO\" desde la pantalla principal. Sigue los pasos para vincular el medicamento, establecer la forma de administración, la frecuencia y el horario de la dosis. También puedes agregar instrucciones, imágenes y configurar recordatorios de recarga si es necesario. Una vez configurado, el recordatorio estará activo en tu calendario.'),(4,'2023-10-13',NULL,'¿Puedo editar o eliminar un recordatorio una vez que lo he establecido en MediCAL?','Sí, puedes editar o eliminar un recordatorio en cualquier momento. Ve a la sección de recordatorios, selecciona el recordatorio que deseas modificar y elige la opción correspondiente para realizar cambios. Si deseas eliminarlo, selecciona la opción de eliminar y confirma la acción. Recuerda que cualquier modificación se reflejará en tu calendario de MediCAL.');
/*!40000 ALTER TABLE `faq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `frecuencia`
--

DROP TABLE IF EXISTS `frecuencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `frecuencia` (
  `cod_frecuencia` int NOT NULL AUTO_INCREMENT,
  `cantidad_frecuencia` int NOT NULL,
  `dias_descansof` int NOT NULL,
  `dias_tomaf` int NOT NULL,
  `nombre_frecuencia` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cod_frecuencia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `frecuencia`
--

LOCK TABLES `frecuencia` WRITE;
/*!40000 ALTER TABLE `frecuencia` DISABLE KEYS */;
/*!40000 ALTER TABLE `frecuencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historial_fin_vigencia`
--

DROP TABLE IF EXISTS `historial_fin_vigencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historial_fin_vigencia` (
  `nro_historialfv` int NOT NULL AUTO_INCREMENT,
  `fecha_desdefv` date DEFAULT NULL,
  `fecha_hastafv` date DEFAULT NULL,
  `motivofv` varchar(255) DEFAULT NULL,
  `cod_usuario` int DEFAULT NULL,
  PRIMARY KEY (`nro_historialfv`),
  KEY `FKk7yjwxhytlbopa8omdl364voa` (`cod_usuario`),
  CONSTRAINT `FKk7yjwxhytlbopa8omdl364voa` FOREIGN KEY (`cod_usuario`) REFERENCES `usuario` (`cod_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historial_fin_vigencia`
--

LOCK TABLES `historial_fin_vigencia` WRITE;
/*!40000 ALTER TABLE `historial_fin_vigencia` DISABLE KEYS */;
/*!40000 ALTER TABLE `historial_fin_vigencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instruccion`
--

DROP TABLE IF EXISTS `instruccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `instruccion` (
  `cod_instruccion` int NOT NULL AUTO_INCREMENT,
  `desc_instruccion` varchar(301) DEFAULT NULL,
  `nombre_instruccion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cod_instruccion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instruccion`
--

LOCK TABLES `instruccion` WRITE;
/*!40000 ALTER TABLE `instruccion` DISABLE KEYS */;
/*!40000 ALTER TABLE `instruccion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventario`
--

DROP TABLE IF EXISTS `inventario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventario` (
  `cod_inventario` int NOT NULL AUTO_INCREMENT,
  `cant_aviso_inventario` int DEFAULT NULL,
  `cant_real_inventario` int DEFAULT NULL,
  PRIMARY KEY (`cod_inventario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventario`
--

LOCK TABLES `inventario` WRITE;
/*!40000 ALTER TABLE `inventario` DISABLE KEYS */;
/*!40000 ALTER TABLE `inventario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicamento`
--

DROP TABLE IF EXISTS `medicamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicamento` (
  `cod_medicamento` int NOT NULL AUTO_INCREMENT,
  `es_particular` bit(1) NOT NULL,
  `fecha_alta_medicamento` date DEFAULT NULL,
  `fecha_fin_vigencia_med` date DEFAULT NULL,
  `marca_medicamento` varchar(255) DEFAULT NULL,
  `nombre_medicamento` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cod_medicamento`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicamento`
--

LOCK TABLES `medicamento` WRITE;
/*!40000 ALTER TABLE `medicamento` DISABLE KEYS */;
INSERT INTO `medicamento` VALUES (1,_binary '\0','2023-10-13',NULL,'Bayer','Ibuprofeno'),(2,_binary '\0','2023-10-13',NULL,'Tylenol','Paracetamol'),(3,_binary '\0','2023-10-13',NULL,'Prilosec','Omeprazol'),(4,_binary '\0','2023-10-13',NULL,'Claritin','Loratadina'),(5,_binary '\0','2023-10-13',NULL,'Zocor','Simvastatina'),(6,_binary '\0','2023-10-13',NULL,'Amoxil','Amoxicilina');
/*!40000 ALTER TABLE `medicamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicion`
--

DROP TABLE IF EXISTS `medicion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicion` (
  `cod_medicion` int NOT NULL AUTO_INCREMENT,
  `fecha_alta_medicion` date DEFAULT NULL,
  `fecha_fin_vigenciam` date DEFAULT NULL,
  `nombre_medicion` varchar(255) DEFAULT NULL,
  `unidad_medida_medicion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cod_medicion`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicion`
--

LOCK TABLES `medicion` WRITE;
/*!40000 ALTER TABLE `medicion` DISABLE KEYS */;
INSERT INTO `medicion` VALUES (1,'2023-10-13',NULL,'Peso','kg'),(2,'2023-10-13',NULL,'Glucosa en sangre','mg/dL'),(3,'2023-10-13',NULL,'Presion arterial','mmHg'),(4,'2023-10-13',NULL,'Capacidad vital','litros'),(5,'2023-10-13',NULL,'Aféresis','Unidad'),(6,'2023-10-13',NULL,'Circunferencia de la cintura','cm'),(7,'2023-10-13',NULL,'Circunferencia del brazo','cm'),(8,'2023-10-13',NULL,'Circunferencia del muslo','cm'),(9,'2023-10-13',NULL,'Circunferencia del torax','cm'),(10,'2023-10-13',NULL,'Ferritina','ng/mL'),(11,'2023-10-13',NULL,'Frecuencia respiratoria','rpm');
/*!40000 ALTER TABLE `medicion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `omision`
--

DROP TABLE IF EXISTS `omision`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `omision` (
  `cod_omision` int NOT NULL AUTO_INCREMENT,
  `nombre_omision` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cod_omision`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `omision`
--

LOCK TABLES `omision` WRITE;
/*!40000 ALTER TABLE `omision` DISABLE KEYS */;
/*!40000 ALTER TABLE `omision` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perfil`
--

DROP TABLE IF EXISTS `perfil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `perfil` (
  `cod_perfil` int NOT NULL AUTO_INCREMENT,
  `desc_perfil` varchar(255) DEFAULT NULL,
  `fecha_alta_perfil` date DEFAULT NULL,
  `fecha_fin_vigenciap` date DEFAULT NULL,
  `motivo_fin_vigenciap` varchar(255) DEFAULT NULL,
  `nombre_perfil` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cod_perfil`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfil`
--

LOCK TABLES `perfil` WRITE;
/*!40000 ALTER TABLE `perfil` DISABLE KEYS */;
INSERT INTO `perfil` VALUES (1,'Usuarios que utilizan la app desde sus celulares','2023-10-13',NULL,NULL,'Usuario Particular'),(2,'Usuario administrador del sistema','2023-10-13',NULL,NULL,'Administrador del Sistema'),(3,'Usuarios administradores de instituciones de salud','2023-10-13',NULL,NULL,'Administrador de Institución'),(4,'Profesionales de la salud que trabajan en una institución','2023-10-13',NULL,NULL,'Profesional de la salud');
/*!40000 ALTER TABLE `perfil` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perfil_permiso`
--

DROP TABLE IF EXISTS `perfil_permiso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `perfil_permiso` (
  `id_perfil_permiso` int NOT NULL AUTO_INCREMENT,
  `cod_perfil` int DEFAULT NULL,
  `cod_permiso` int DEFAULT NULL,
  PRIMARY KEY (`id_perfil_permiso`),
  KEY `FK5dfohnnygs38x9xmadhq3a8ec` (`cod_perfil`),
  KEY `FK6te6q42y49ewqt66iuabuva9m` (`cod_permiso`),
  CONSTRAINT `FK5dfohnnygs38x9xmadhq3a8ec` FOREIGN KEY (`cod_perfil`) REFERENCES `perfil` (`cod_perfil`),
  CONSTRAINT `FK6te6q42y49ewqt66iuabuva9m` FOREIGN KEY (`cod_permiso`) REFERENCES `permiso` (`cod_permiso`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfil_permiso`
--

LOCK TABLES `perfil_permiso` WRITE;
/*!40000 ALTER TABLE `perfil_permiso` DISABLE KEYS */;
INSERT INTO `perfil_permiso` VALUES (1,1,1),(2,1,2),(3,1,3),(4,1,4),(5,1,5),(6,1,6),(7,1,7),(8,1,8),(9,1,12),(10,1,13),(11,1,14),(12,1,15),(13,2,1),(14,2,2),(15,2,3),(16,2,4),(17,2,5),(18,2,6),(19,2,7),(20,2,8),(21,2,9),(22,2,10),(23,2,11),(24,2,12),(25,2,13),(26,2,14),(27,2,15),(28,3,1),(29,3,2),(30,3,3),(31,3,4),(32,3,5),(33,3,6),(34,3,7),(35,3,8),(36,3,9),(37,3,10),(38,3,11),(39,3,12),(40,3,13),(41,3,14),(42,3,15),(43,4,1),(44,4,2),(45,4,3),(46,4,4),(47,4,5),(48,4,6),(49,4,7),(50,4,8),(51,4,12),(52,4,13),(53,4,14),(54,4,15);
/*!40000 ALTER TABLE `perfil_permiso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permiso`
--

DROP TABLE IF EXISTS `permiso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permiso` (
  `cod_permiso` int NOT NULL AUTO_INCREMENT,
  `desc_permiso` varchar(255) DEFAULT NULL,
  `fecha_alta_permiso` date DEFAULT NULL,
  `fecha_fin_vigenciap` date DEFAULT NULL,
  `nombre_permiso` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cod_permiso`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permiso`
--

LOCK TABLES `permiso` WRITE;
/*!40000 ALTER TABLE `permiso` DISABLE KEYS */;
INSERT INTO `permiso` VALUES (1,'Permite al usuario agregar un calendario','2023-10-13',NULL,'Agregar Calendarios'),(2,'Permite al usuario modificar los calendarios existentes','2023-10-13',NULL,'Modificar Calendarios'),(3,'Permite al usuario eliminar un calendario relacionado a su cuenta','2023-10-13',NULL,'Eliminar Calendarios'),(4,'Permite al usuario agregar un síntoma','2023-10-13',NULL,'Agregar Síntomas'),(5,'Permite al usuario eliminar un síntoma relacionado a su cuenta','2023-10-13',NULL,'Eliminar Síntomas'),(6,'Permite al usuario agregar un recordatorio','2023-10-13',NULL,'Agregar Recordatorios'),(7,'Permite al usuario modificar un recordatorio existente','2023-10-13',NULL,'Modificar Recordatorios'),(8,'Permite al usuario eliminar un recordatorio relacionado a su cuenta','2023-10-13',NULL,'Eliminar Recordatorios'),(9,'Permite al usuario crear usuarios','2023-10-13',NULL,'Agregar Usuarios'),(10,'Permite al usuario modificar usuarios existentes','2023-10-13',NULL,'Modificar Usuarios'),(11,'Permite al usuario eliminar usuarios existentes','2023-10-13',NULL,'Eliminar Usuarios'),(12,'Permite al usuario agregar una medición','2023-10-13',NULL,'Agregar Mediciones'),(13,'Permite al usuario modificar una medición existente','2023-10-13',NULL,'Modificar Mediciones'),(14,'Permite al usuario eliminar una medición asociada a su cuenta','2023-10-13',NULL,'Eliminar Mediciones'),(15,'Permite al usuario generar reportes sobre un calendario','2023-10-13',NULL,'Generar reportes');
/*!40000 ALTER TABLE `permiso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `presentacion_med`
--

DROP TABLE IF EXISTS `presentacion_med`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `presentacion_med` (
  `cod_presentacion_med` int NOT NULL AUTO_INCREMENT,
  `desc_presentacion_med` varchar(255) DEFAULT NULL,
  `fecha_alta_presentacion_med` date DEFAULT NULL,
  `fecha_fin_vigenciapm` date DEFAULT NULL,
  `nombre_presentacion_med` varchar(255) DEFAULT NULL,
  `cod_administracion_med` int DEFAULT NULL,
  PRIMARY KEY (`cod_presentacion_med`),
  KEY `FKj8oreg1msx2samj5ru2n458j0` (`cod_administracion_med`),
  CONSTRAINT `FKj8oreg1msx2samj5ru2n458j0` FOREIGN KEY (`cod_administracion_med`) REFERENCES `administracion_med` (`cod_administracion_med`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `presentacion_med`
--

LOCK TABLES `presentacion_med` WRITE;
/*!40000 ALTER TABLE `presentacion_med` DISABLE KEYS */;
INSERT INTO `presentacion_med` VALUES (1,'Porción pequeña de sustancia medicinal. Es la forma coloquial de referirse al comprimido','2023-10-13',NULL,'Pastilla',1),(2,'Es una mezcla del principio activo (medicamento) y una serie de sustancias que permiten que no se modifique su forma ni sus características con el tiempo.','2023-10-13',NULL,'Comprimido',1),(3,'Se trata de un envoltorio inerte de gelatina, que consta de dos partes, llamadas cuerpo y tapa, que se encastran a la perfección. A diferencia de los comprimidos, el contenido en su interior no está comprimido sino suelto.','2023-10-13',NULL,'Cápsula',1),(4,'Medicamento en forma de líquido espeso','2023-10-13',NULL,'Jarabe',1),(5,'Medicamento en forma de líquido de consistencia acuosa','2023-10-13',NULL,'Solución(Líquido)',1),(6,'Las gotas orales tienen mayor concentracion del medicamento respecto a la solucion donde es menor.','2023-10-13',NULL,'Gotas',1),(7,'Forma farmacéutica cuyos componentes están pulverizados y que se presentan dosificados o no, puros o mezclados, con o sin adición de excipientes.','2023-10-13',NULL,'Polvo',1),(8,'Forma de dosificación oral que puede liberar un principio activo o ingrediente funcional gracias a la acción mecánica de masticar.','2023-10-13',NULL,'Chicle',1),(9,'Son dispersiones de partículas muy finas de un líquido o de un sólido en el aire o en un gas','2023-10-13',NULL,'Espray',1),(10,'Soluciones líquidas que se aplican directamente en estas áreas para proporcionar alivio o tratamiento en caso de diversas afecciones.','2023-10-13',NULL,'Gotas',2),(11,'Se administran en forma líquida pulverizada utilizando un dispositivo de pulverización, generalmente una botella que contiene una solución líquida o suspensión finamente atomizada.','2023-10-13',NULL,'Espray',2),(12,'Productos tópicos que se aplican en estas áreas del cuerpo para tratar afecciones específicas. Estas cremas están diseñadas para ser aplicadas en la piel alrededor de la nariz, los oídos o los ojos','2023-10-13',NULL,'Crema',2),(13,'Medicamento en forma de líquido de consistencia acuosa para aplicar en ojos, naríz u oídos','2023-10-13',NULL,'Solución(Líquido)',2),(14,'Dispositivo médico utilizado para suministrar un medicamento en forma de partículas de polvo al organismo a través de los pulmones, y de aquí a los tejidos blandos.','2023-10-13',NULL,'Inhalador',2),(15,'Introducción de medicamento o productos biológicos al sitio de acción mediante la punción a presión en diferentes tejidos corporales a través de una jeringa y una aguja hipodérmica o de inyección.','2023-10-13',NULL,'Inyección',3),(16,'Las ampollas contienen dosis unitarias de medicamento inyectable en forma líquida y están disponibles en diferentes dosis. Las ampollas se fabrican con vidrio y presentan un cuello en embudo prelijado, que debe romperse para obtener el medicamento.','2023-10-13',NULL,'Ampolla',3),(17,'Un suero es la disolución de sales u otras sustancias en agua, que se inyecta con fin curativo.','2023-10-13',NULL,'Suero',3),(18,'Una solución inyectable es una forma farmacéutica que se presenta en forma líquida y está diseñada para ser administrada mediante una inyección, ya sea intramuscular, intravenosa, subcutánea u otra vía.','2023-10-13',NULL,'Solución (Líquido)',3),(19,'Se presenta en forma de crema y se aplica directamente sobre la piel. Estas cremas están diseñadas para ser absorbidas a través de la piel y actuar localmente en el área de aplicación.','2023-10-13',NULL,'Crema',4),(20,'Se presenta en forma de champú y se aplica sobre el cabello y el cuero cabelludo.','2023-10-13',NULL,'Champú',4),(21,'Se presenta en forma de solución líquida y se aplica directamente sobre la piel o la zona afectada.','2023-10-13',NULL,'Solución (Líquido)',4),(22,'Se presenta en forma de parche adhesivo que se aplica sobre la piel para liberar el medicamento gradualmente.','2023-10-13',NULL,'Parche',4),(23,'Se presenta en forma de apósito que se coloca sobre la piel para tratar heridas o afecciones.','2023-10-13',NULL,'Apósito',4),(24,'Se presenta en forma de laca y se aplica sobre la piel para fijar y proteger.','2023-10-13',NULL,'Laca',4),(25,'Se presenta en forma de espuma y se aplica sobre la piel para tratar diversas afecciones.','2023-10-13',NULL,'Espuma',4),(26,'Se presenta en forma de espray y se aplica sobre la piel para un efecto rápido.','2023-10-13',NULL,'Espray',4),(27,'Se presenta en forma de polvo y se aplica sobre la piel para tratar afecciones específicas.','2023-10-13',NULL,'Polvo',4),(28,'Se presenta en forma de barra y se aplica sobre la piel para tratar afecciones específicas.','2023-10-13',NULL,'Barra',4);
/*!40000 ALTER TABLE `presentacion_med` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recordatorio`
--

DROP TABLE IF EXISTS `recordatorio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recordatorio` (
  `cod_recordatorio` int NOT NULL AUTO_INCREMENT,
  `duracion_recordatorio` int NOT NULL,
  `fecha_alta_recordatorio` date DEFAULT NULL,
  `fecha_fin_recordatorio` datetime(6) DEFAULT NULL,
  `fecha_fin_vigenciar` date DEFAULT NULL,
  `fecha_inicio_recordatorio` datetime(6) DEFAULT NULL,
  `horario_recordatorio` int NOT NULL,
  `imagen` longblob,
  `cod_administracion_med` int DEFAULT NULL,
  `cod_calendario` int DEFAULT NULL,
  `cod_dosis` int DEFAULT NULL,
  `cod_frecuencia` int DEFAULT NULL,
  `cod_instruccion` int DEFAULT NULL,
  `cod_inventario` int DEFAULT NULL,
  `cod_medicamento` int DEFAULT NULL,
  `cod_presentacion_med` int DEFAULT NULL,
  PRIMARY KEY (`cod_recordatorio`),
  UNIQUE KEY `UK_qdumbeq6dkrqthmrgqmx7nhnv` (`cod_dosis`),
  UNIQUE KEY `UK_qufl9qoblc7ak21cft8gv4yqu` (`cod_frecuencia`),
  UNIQUE KEY `UK_r4opwglhpqjae7r706p37r4tf` (`cod_instruccion`),
  UNIQUE KEY `UK_rx1jqucmn3es7sdodw44tlalb` (`cod_inventario`),
  KEY `FK2tyski4w7w7mylmdypeqjb7s7` (`cod_administracion_med`),
  KEY `FK1i2cheb49y088odobki9tgxrm` (`cod_calendario`),
  KEY `FK61e5b73dlaev1foi0tfhy77sa` (`cod_medicamento`),
  KEY `FKcb5c45irh78b4rrxxicgai8v3` (`cod_presentacion_med`),
  CONSTRAINT `FK1i2cheb49y088odobki9tgxrm` FOREIGN KEY (`cod_calendario`) REFERENCES `calendario` (`cod_calendario`),
  CONSTRAINT `FK2tyski4w7w7mylmdypeqjb7s7` FOREIGN KEY (`cod_administracion_med`) REFERENCES `administracion_med` (`cod_administracion_med`),
  CONSTRAINT `FK30xdq8v33p063yn9pxw8ay9k` FOREIGN KEY (`cod_frecuencia`) REFERENCES `frecuencia` (`cod_frecuencia`),
  CONSTRAINT `FK61e5b73dlaev1foi0tfhy77sa` FOREIGN KEY (`cod_medicamento`) REFERENCES `medicamento` (`cod_medicamento`),
  CONSTRAINT `FKcb5c45irh78b4rrxxicgai8v3` FOREIGN KEY (`cod_presentacion_med`) REFERENCES `presentacion_med` (`cod_presentacion_med`),
  CONSTRAINT `FKcxrcwfuekb9fy8lwe5w70hy45` FOREIGN KEY (`cod_instruccion`) REFERENCES `instruccion` (`cod_instruccion`),
  CONSTRAINT `FKjh4wh90ehy55ptra771xojcya` FOREIGN KEY (`cod_inventario`) REFERENCES `inventario` (`cod_inventario`),
  CONSTRAINT `FKqbgdxhlbfeujwyoqsnm4h5hts` FOREIGN KEY (`cod_dosis`) REFERENCES `dosis` (`cod_dosis`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recordatorio`
--

LOCK TABLES `recordatorio` WRITE;
/*!40000 ALTER TABLE `recordatorio` DISABLE KEYS */;
/*!40000 ALTER TABLE `recordatorio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registro_recordatorio`
--

DROP TABLE IF EXISTS `registro_recordatorio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registro_recordatorio` (
  `cod_registro_recordatorio` int NOT NULL AUTO_INCREMENT,
  `fecha_fin_vigenciarr` date DEFAULT NULL,
  `fecha_toma_esperada` datetime(6) DEFAULT NULL,
  `fecha_toma_real` datetime(6) DEFAULT NULL,
  `nro_registro` int NOT NULL,
  `toma_registro_recordatorio` bit(1) NOT NULL,
  `cod_omision` int DEFAULT NULL,
  `cod_recordatorio` int DEFAULT NULL,
  PRIMARY KEY (`cod_registro_recordatorio`),
  UNIQUE KEY `UK_2nlbl12kj2d2ptayelyu8rh9a` (`cod_omision`),
  KEY `FKp9hn29jqv6ot70qf8y2s8x90t` (`cod_recordatorio`),
  CONSTRAINT `FKp9hn29jqv6ot70qf8y2s8x90t` FOREIGN KEY (`cod_recordatorio`) REFERENCES `recordatorio` (`cod_recordatorio`),
  CONSTRAINT `FKqwvd98xngr7qpy3pdrwn73nyt` FOREIGN KEY (`cod_omision`) REFERENCES `omision` (`cod_omision`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registro_recordatorio`
--

LOCK TABLES `registro_recordatorio` WRITE;
/*!40000 ALTER TABLE `registro_recordatorio` DISABLE KEYS */;
/*!40000 ALTER TABLE `registro_recordatorio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reporte`
--

DROP TABLE IF EXISTS `reporte`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reporte` (
  `nro_reporte` int NOT NULL AUTO_INCREMENT,
  `fecha_desde` date DEFAULT NULL,
  `fecha_generada` date DEFAULT NULL,
  `fecha_hasta` date DEFAULT NULL,
  `nombre_med` varchar(255) DEFAULT NULL,
  `cod_tipo_reporte` int DEFAULT NULL,
  `cod_usuario` int DEFAULT NULL,
  PRIMARY KEY (`nro_reporte`),
  KEY `FKo5fgwk4xk0c7kp4osbpvo9ahn` (`cod_tipo_reporte`),
  KEY `FK92qs84pg28xe3prmywuebherb` (`cod_usuario`),
  CONSTRAINT `FK92qs84pg28xe3prmywuebherb` FOREIGN KEY (`cod_usuario`) REFERENCES `usuario` (`cod_usuario`),
  CONSTRAINT `FKo5fgwk4xk0c7kp4osbpvo9ahn` FOREIGN KEY (`cod_tipo_reporte`) REFERENCES `tipo_reporte` (`cod_tipo_reporte`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reporte`
--

LOCK TABLES `reporte` WRITE;
/*!40000 ALTER TABLE `reporte` DISABLE KEYS */;
INSERT INTO `reporte` VALUES (1,'2022-01-06','2023-10-13','2023-06-02',NULL,1,NULL);
/*!40000 ALTER TABLE `reporte` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sintoma`
--

DROP TABLE IF EXISTS `sintoma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sintoma` (
  `cod_sintoma` int NOT NULL AUTO_INCREMENT,
  `fecha_alta_sintoma` date DEFAULT NULL,
  `fecha_fin_vigencias` date DEFAULT NULL,
  `nombre_sintoma` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cod_sintoma`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sintoma`
--

LOCK TABLES `sintoma` WRITE;
/*!40000 ALTER TABLE `sintoma` DISABLE KEYS */;
INSERT INTO `sintoma` VALUES (1,'2023-10-13',NULL,'Tos'),(2,'2023-10-13',NULL,'Ansiedad'),(3,'2023-10-13',NULL,'Cansancio'),(4,'2023-10-13',NULL,'Estres'),(5,'2023-10-13',NULL,'Vitalidad'),(6,'2023-10-13',NULL,'Cambios de ánimo'),(7,'2023-10-13',NULL,'Entusiasmo'),(8,'2023-10-13',NULL,'Feliz'),(9,'2023-10-13',NULL,'Triste'),(10,'2023-10-13',NULL,'Sudoración nocturna'),(11,'2023-10-13',NULL,'Dificultad para dormir'),(12,'2023-10-13',NULL,'Cansancio al despertar'),(13,'2023-10-13',NULL,'Exhausta'),(14,'2023-10-13',NULL,'Rabia'),(15,'2023-10-13',NULL,'Irritabilidad'),(16,'2023-10-13',NULL,'Gratitud'),(17,'2023-10-13',NULL,'Indiferencia');
/*!40000 ALTER TABLE `sintoma` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solicitud`
--

DROP TABLE IF EXISTS `solicitud`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `solicitud` (
  `cod_solicitud` int NOT NULL AUTO_INCREMENT,
  `fecha_solicitud` date DEFAULT NULL,
  `cod_estado_solicitud` int DEFAULT NULL,
  `cod_usuario_controlado` int DEFAULT NULL,
  `cod_usuario_controlador` int DEFAULT NULL,
  PRIMARY KEY (`cod_solicitud`),
  KEY `FKa2ol46rpy41x7fo78pue1qiyq` (`cod_estado_solicitud`),
  KEY `FK3t3lg5221xk8sphsfkix1aul` (`cod_usuario_controlado`),
  KEY `FK5totvyn0web8vspj6o3gi5v8e` (`cod_usuario_controlador`),
  CONSTRAINT `FK3t3lg5221xk8sphsfkix1aul` FOREIGN KEY (`cod_usuario_controlado`) REFERENCES `usuario` (`cod_usuario`),
  CONSTRAINT `FK5totvyn0web8vspj6o3gi5v8e` FOREIGN KEY (`cod_usuario_controlador`) REFERENCES `usuario` (`cod_usuario`),
  CONSTRAINT `FKa2ol46rpy41x7fo78pue1qiyq` FOREIGN KEY (`cod_estado_solicitud`) REFERENCES `estado_solicitud` (`cod_estado_solicitud`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solicitud`
--

LOCK TABLES `solicitud` WRITE;
/*!40000 ALTER TABLE `solicitud` DISABLE KEYS */;
/*!40000 ALTER TABLE `solicitud` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_consejo`
--

DROP TABLE IF EXISTS `tipo_consejo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_consejo` (
  `nro_tipo_consejo` int NOT NULL AUTO_INCREMENT,
  `nombre_tipo_consejo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`nro_tipo_consejo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_consejo`
--

LOCK TABLES `tipo_consejo` WRITE;
/*!40000 ALTER TABLE `tipo_consejo` DISABLE KEYS */;
INSERT INTO `tipo_consejo` VALUES (1,'Medico'),(2,'Binestar y Salud'),(3,'Sobre la App');
/*!40000 ALTER TABLE `tipo_consejo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_reporte`
--

DROP TABLE IF EXISTS `tipo_reporte`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_reporte` (
  `cod_tipo_reporte` int NOT NULL AUTO_INCREMENT,
  `nombre_tipo_reporte` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cod_tipo_reporte`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_reporte`
--

LOCK TABLES `tipo_reporte` WRITE;
/*!40000 ALTER TABLE `tipo_reporte` DISABLE KEYS */;
INSERT INTO `tipo_reporte` VALUES (1,'Reporte Medicamentos (Todos)'),(2,'Reporte Medicamento (Uno)'),(3,'Reporte Síntomas'),(4,'Reporte Mediciones'),(5,'Reporte Enfermeros'),(6,'Reporte Pacientes / Calendarios');
/*!40000 ALTER TABLE `tipo_reporte` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `dtype` varchar(31) NOT NULL,
  `cod_usuario` int NOT NULL AUTO_INCREMENT,
  `apellido_usuario` varchar(255) DEFAULT NULL,
  `contrasenia_usuario` varchar(255) DEFAULT NULL,
  `fecha_alta_usuario` date DEFAULT NULL,
  `fecha_nacimiento_usuario` date DEFAULT NULL,
  `genero_usuario` varchar(255) DEFAULT NULL,
  `mail_usuario` varchar(255) DEFAULT NULL,
  `nombre_institucion` varchar(255) DEFAULT NULL,
  `nombre_usuario` varchar(255) DEFAULT NULL,
  `telefono_usuario` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `usuario_unico` varchar(255) DEFAULT NULL,
  `dni` bigint DEFAULT NULL,
  `matricula` int DEFAULT NULL,
  `cod_verificacion` varchar(255) DEFAULT NULL,
  `cod_perfil` int DEFAULT NULL,
  PRIMARY KEY (`cod_usuario`),
  UNIQUE KEY `UK_52kxex4r2m7csl0hehoem5lj5` (`cod_verificacion`),
  KEY `FKm9dxudu60awrs6ij0q1tgdbp1` (`cod_perfil`),
  CONSTRAINT `FK7eijb2c5b4hdxuplvdu5td2j6` FOREIGN KEY (`cod_verificacion`) REFERENCES `codigo_verificacion` (`cod_verificacion`),
  CONSTRAINT `FKm9dxudu60awrs6ij0q1tgdbp1` FOREIGN KEY (`cod_perfil`) REFERENCES `perfil` (`cod_perfil`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES ('Usuario',1,NULL,'{NU6MtAJ9FYQqQuzgtMLP1hJssBUE0T+oGck05I6hMow=}1495f3c7dd7b0521b4ba27da934227c60d44c54f0abf4d2176a4e8fb5d3b647e','2023-10-13',NULL,NULL,'medicalutnfrm@gmail.com',NULL,NULL,NULL,NULL,'Medical',NULL,NULL,NULL,2);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-13 22:34:32
