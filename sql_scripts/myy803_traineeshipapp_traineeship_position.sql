CREATE DATABASE  IF NOT EXISTS `myy803_traineeshipapp` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `myy803_traineeshipapp`;
-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: myy803_traineeshipapp
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `traineeship_position`
--

DROP TABLE IF EXISTS `traineeship_position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `traineeship_position` (
  `id` int NOT NULL AUTO_INCREMENT,
  `mark` tinyint DEFAULT NULL,
  `company_id` varchar(255) DEFAULT NULL,
  `logbook` text,
  `professor_id` varchar(255) DEFAULT NULL,
  `student_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK587ldsm8cd8v1vsd1223x1y40` (`company_id`),
  KEY `FKfd5ay7by2eqfjdjcai59ffah7` (`professor_id`),
  KEY `FK4btgtp1sd36rby2vgb71b281t` (`student_id`),
  CONSTRAINT `FK4btgtp1sd36rby2vgb71b281t` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
  CONSTRAINT `FK587ldsm8cd8v1vsd1223x1y40` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `FKfd5ay7by2eqfjdjcai59ffah7` FOREIGN KEY (`professor_id`) REFERENCES `professor` (`id`),
  CONSTRAINT `traineeship_position_chk_1` CHECK ((`mark` between 0 and 2))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-26 22:29:26
