-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: keaproject.ca7yxxdktyyl.eu-west-1.rds.amazonaws.com    Database: KeaProject
-- ------------------------------------------------------
-- Server version	8.0.17

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '';

--
-- Table structure for table `Address`
--

DROP TABLE IF EXISTS `Address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Address` (
  `address_id` int(11) NOT NULL AUTO_INCREMENT,
  `country` varchar(20) NOT NULL,
  `city` varchar(40) DEFAULT NULL,
  `street` varchar(40) DEFAULT NULL,
  `houseNumber` varchar(10) DEFAULT NULL,
  `zipcode` varchar(10) NOT NULL,
  `customer_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`address_id`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `Address_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `Customer` (`customer_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Address`
--

LOCK TABLES `Address` WRITE;
/*!40000 ALTER TABLE `Address` DISABLE KEYS */;
INSERT INTO `Address` VALUES (1,'Argentina','Ningún citio','Calle de los Gangstas','2A','99999',1),(2,'North America','Detroit','8 Mile St.','50C','48127',2),(3,'North America','Cole World LA','Hills Ave.','214','90001',3),(4,'North America','Weed City','Green Street','420','51420',4),(5,'North America','Los Angeles','Compton Walk','112A','90220',5),(6,'West World','Kanye City','Ye Blvd.','1','55512',6),(7,'North America','Los Angeles','Compton Walk','112B','90220',7);
/*!40000 ALTER TABLE `Address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CarInfo`
--

DROP TABLE IF EXISTS `CarInfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CarInfo` (
  `licencePlate` varchar(10) NOT NULL,
  `registration` date NOT NULL,
  `odometer` int(11) NOT NULL,
  `specs_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`licencePlate`),
  KEY `specs_id` (`specs_id`),
  CONSTRAINT `CarInfo_ibfk_1` FOREIGN KEY (`specs_id`) REFERENCES `Specs` (`specs_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CarInfo`
--

LOCK TABLES `CarInfo` WRITE;
/*!40000 ALTER TABLE `CarInfo` DISABLE KEYS */;
INSERT INTO `CarInfo` VALUES ('ab54543','2020-01-01',2800,1),('bg99778','2019-04-01',10653,2),('cp34213','2019-10-01',7983,5),('kl77863','2018-12-01',19000,3),('KW55548','0000-00-00',188333,9),('LM68643','0000-00-00',20047,10),('LW98554','0000-00-00',156933,12),('NT78544','0000-00-00',151517,7),('PV44669','2020-02-01',600,4),('QF34765','0000-00-00',568901,13),('TT77342','0000-00-00',144966,11),('xy98762','2020-01-01',10023,6);
/*!40000 ALTER TABLE `CarInfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ClassType`
--

DROP TABLE IF EXISTS `ClassType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ClassType` (
  `className_id` int(11) NOT NULL AUTO_INCREMENT,
  `className` varchar(15) DEFAULT NULL,
  `pricePerDay` int(11) DEFAULT NULL,
  PRIMARY KEY (`className_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ClassType`
--

LOCK TABLES `ClassType` WRITE;
/*!40000 ALTER TABLE `ClassType` DISABLE KEYS */;
INSERT INTO `ClassType` VALUES (1,'Luxury',619),(2,'Family',439),(3,'Sport',559);
/*!40000 ALTER TABLE `ClassType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Customer`
--

DROP TABLE IF EXISTS `Customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Customer` (
  `customer_id` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(25) NOT NULL,
  `lastName` varchar(25) NOT NULL,
  `email` varchar(45) NOT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Customer`
--

LOCK TABLES `Customer` WRITE;
/*!40000 ALTER TABLE `Customer` DISABLE KEYS */;
INSERT INTO `Customer` VALUES (1,'Tupac','Shakur','tupacshakur@alleyezon.me'),(2,'Slim','Shady','slimshady@loseyourself.com'),(3,'J','Cole','jcole@letnas.down'),(4,'snoop','dogg','snoopdogg@dropitlikeits.hot'),(5,'Ice','Cube','icecube@itwasagood.day'),(6,'Kanye','West','kanyewest@nomorepartiesin.la'),(7,'Dr','dre','dr.dre@still.dre');
/*!40000 ALTER TABLE `Customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DriverInfo`
--

DROP TABLE IF EXISTS `DriverInfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `DriverInfo` (
  `driverLicenceNumber` varchar(20) NOT NULL,
  `driverFirstName` varchar(45) NOT NULL,
  `driverLastName` varchar(45) NOT NULL,
  `driverSinceDate` date NOT NULL,
  `customer_id` int(11) NOT NULL,
  PRIMARY KEY (`driverLicenceNumber`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `DriverInfo_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `Customer` (`customer_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DriverInfo`
--

LOCK TABLES `DriverInfo` WRITE;
/*!40000 ALTER TABLE `DriverInfo` DISABLE KEYS */;
INSERT INTO `DriverInfo` VALUES ('110852332','Slim','Shady','2004-09-22',2),('1201668105','Dr','Dre','1988-02-28',7),('1212712287','Snoop','Dogg','1989-01-20',4),('1310754422','Tupac','Shakur','1995-05-12',1),('2104705573','Kanye','West','1988-08-07',6),('3001654383','J','Cole','1987-10-10',3),('903675674','Ice','Cube','1985-04-19',5);
/*!40000 ALTER TABLE `DriverInfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Phone`
--

DROP TABLE IF EXISTS `Phone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Phone` (
  `phone_id` int(11) NOT NULL AUTO_INCREMENT,
  `phoneNumber` varchar(30) NOT NULL,
  `phoneType` varchar(20) NOT NULL,
  `customer_id` int(11) NOT NULL,
  PRIMARY KEY (`phone_id`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `Phone_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `Customer` (`customer_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Phone`
--

LOCK TABLES `Phone` WRITE;
/*!40000 ALTER TABLE `Phone` DISABLE KEYS */;
INSERT INTO `Phone` VALUES (1,'3123432231','MobilePhone',1),(2,'3123213231','HomePhone',1),(3,'5234521345','MobilePhone',2),(4,'34567134346','HomePhone',2),(5,'99867134346','MobilePhone',3),(6,'9877465346545','HomePhone',4),(7,'11231233887','MobilePhone',5),(8,'34457134','HomePhone',5),(9,'9977867765','MobilePhone',7),(10,'212341235','HomePhone',7);
/*!40000 ALTER TABLE `Phone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RentalContract`
--

DROP TABLE IF EXISTS `RentalContract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `RentalContract` (
  `rentalContract_id` int(11) NOT NULL AUTO_INCREMENT,
  `startDate` datetime NOT NULL,
  `endDate` datetime NOT NULL,
  `maxKm` int(11) DEFAULT NULL,
  `startKm` int(11) DEFAULT NULL,
  `totalPrice` int(11) DEFAULT NULL,
  `licencePlate` varchar(15) DEFAULT NULL,
  `customer_id` int(11) NOT NULL,
  PRIMARY KEY (`rentalContract_id`),
  KEY `licencePlate` (`licencePlate`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `RentalContract_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `Customer` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RentalContract`
--

LOCK TABLES `RentalContract` WRITE;
/*!40000 ALTER TABLE `RentalContract` DISABLE KEYS */;
INSERT INTO `RentalContract` VALUES (1,'2020-04-01 10:30:00','2020-04-14 18:30:00',1200,10653,NULL,'bg99778',3),(2,'2020-12-20 15:45:00','2021-01-04 12:00:00',NULL,2800,NULL,'ab54543',7),(3,'2020-06-29 05:30:00','2020-07-09 23:15:00',700,7983,NULL,'cp34213',5),(4,'2020-09-11 16:15:00','2020-09-15 20:00:00',300,19000,NULL,'kl77863',1),(5,'2020-05-09 10:45:00','2020-05-19 13:00:00',1500,10023,NULL,'xy98762',4);
/*!40000 ALTER TABLE `RentalContract` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Specs`
--

DROP TABLE IF EXISTS `Specs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Specs` (
  `specs_id` int(11) NOT NULL AUTO_INCREMENT,
  `brand` varchar(25) DEFAULT NULL,
  `model` varchar(10) DEFAULT NULL,
  `ccm` int(11) DEFAULT NULL,
  `gear` varchar(15) DEFAULT NULL,
  `airCondition` tinyint(4) DEFAULT NULL,
  `cruiseControl` tinyint(4) DEFAULT NULL,
  `leatherSeat` tinyint(4) DEFAULT NULL,
  `seatNumber` int(11) DEFAULT NULL,
  `horsePower` int(11) DEFAULT NULL,
  `fuelType` varchar(15) DEFAULT NULL,
  `className_id` int(11) NOT NULL,
  PRIMARY KEY (`specs_id`),
  KEY `className_id` (`className_id`),
  CONSTRAINT `Specs_ibfk_1` FOREIGN KEY (`className_id`) REFERENCES `ClassType` (`className_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Specs`
--

LOCK TABLES `Specs` WRITE;
/*!40000 ALTER TABLE `Specs` DISABLE KEYS */;
INSERT INTO `Specs` VALUES (1,'Mercedes','B-Class',1900,'manual',1,0,1,7,190,NULL,2),(2,'chevrolet','orlando',2200,'manual',1,1,0,7,200,NULL,2),(3,'audi','A6',2000,'automatic',1,1,1,5,240,NULL,1),(4,'porche','panamera',3900,'automatic',1,1,1,4,550,NULL,1),(5,'Alfa Romeo','4C',1700,'manual',1,1,1,2,240,NULL,3),(6,'Porche','911',2000,'manual',1,1,1,2,420,NULL,3),(7,'Bentley','Continenta',6000,'dual-clutch',1,1,1,4,626,'petrol',1),(9,'Hyundai','Kona Elect',17,'no gears',1,1,0,4,201,'electricity',2),(10,'Porsche','911 GT3 RS',3600,'manual',1,1,1,2,520,'petrol',3),(11,'Audi','A8',2967,'auto',1,1,1,4,443,'petrol',1),(12,'Toyota','Prius',1800,'power-split',1,1,1,4,121,'petrol',2),(13,'McLaren','720S',3994,'sequential',1,1,1,4,710,'petrol',3);
/*!40000 ALTER TABLE `Specs` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-03 15:29:20
