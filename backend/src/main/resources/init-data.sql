-- MySQL dump 10.13  Distrib 8.0.25, for macos11 (x86_64)
--
-- Host: 127.0.0.1    Database: 3sem-exam
-- ------------------------------------------------------
-- Server version	8.0.25

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE=''+00:00'' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE=''NO_AUTO_VALUE_ON_ZERO'' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `boat_owners`
--

DROP TABLE IF EXISTS `boat_owners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `boat_owners`
(
    `boat_id`  bigint NOT NULL,
    `owner_id` bigint NOT NULL,
    PRIMARY KEY (`boat_id`, `owner_id`),
    KEY        `FK_boat_owners_owner_id` (`owner_id`),
    CONSTRAINT `FK_boat_owners_boat_id` FOREIGN KEY (`boat_id`) REFERENCES `boats` (`id`),
    CONSTRAINT `FK_boat_owners_owner_id` FOREIGN KEY (`owner_id`) REFERENCES `owners` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `boat_owners`
--

LOCK
TABLES `boat_owners` WRITE;
/*!40000 ALTER TABLE `boat_owners` DISABLE KEYS */;
INSERT INTO `boat_owners`
VALUES (2, 1),
       (4, 2),
       (3, 4),
       (2, 5),
       (2, 6),
       (3, 7),
       (1, 8);
/*!40000 ALTER TABLE `boat_owners` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `boats`
--

DROP TABLE IF EXISTS `boats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `boats`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `brand`      varchar(255) DEFAULT NULL,
    `image`      varchar(255) DEFAULT NULL,
    `make`       varchar(255) DEFAULT NULL,
    `name`       varchar(255) DEFAULT NULL,
    `harbour_id` bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY          `FK_boats_harbour_id` (`harbour_id`),
    CONSTRAINT `FK_boats_harbour_id` FOREIGN KEY (`harbour_id`) REFERENCES `harbours` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `boats`
--

LOCK
TABLES `boats` WRITE;
/*!40000 ALTER TABLE `boats` DISABLE KEYS */;
INSERT INTO `boats`
VALUES (1, ''Some new brand3'', ''nonewimg.png'', ''Some new make3'', ''HMS new 3'', 2),
       (2, ''Some brand4'', ''noimg.png'', ''Some make4'', ''HMS 4'', 1),
       (3, ''Some brand2'', ''noimg.png'', ''Some make2'', ''HMS 2'', 1),
       (4, ''Some brand'', ''noimg.png'', ''Some make'', ''HMS 1'', 1);
/*!40000 ALTER TABLE `boats` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `harbours`
--

DROP TABLE IF EXISTS `harbours`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `harbours`
(
    `id`       bigint      NOT NULL AUTO_INCREMENT,
    `address`  varchar(45) DEFAULT NULL,
    `capacity` int         DEFAULT NULL,
    `name`     varchar(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `harbours`
--

LOCK
TABLES `harbours` WRITE;
/*!40000 ALTER TABLE `harbours` DISABLE KEYS */;
INSERT INTO `harbours`
VALUES (1, ''Havnevej 1, 4600 Køge'', 250, ''Køge Havn''),
       (2, ''Søvej 4, 2665 Vallensbæk Strand'', 150, ''Vallensbæk Havn'');
/*!40000 ALTER TABLE `harbours` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `owners`
--

DROP TABLE IF EXISTS `owners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `owners`
(
    `id`      bigint NOT NULL AUTO_INCREMENT,
    `address` varchar(255) DEFAULT NULL,
    `name`    varchar(255) DEFAULT NULL,
    `phone`   int          DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `owners`
--

LOCK
TABLES `owners` WRITE;
/*!40000 ALTER TABLE `owners` DISABLE KEYS */;
INSERT INTO `owners`
VALUES (1, ''Vejnavn 6'', ''Tester 6'', 12345678),
       (2, ''Vejnavn 1'', ''Tester 1'', 12345678),
       (3, ''Vejnavn 4'', ''Tester 4'', 12345678),
       (4, ''Vejnavn 2'', ''Tester 2'', 12345678),
       (5, ''Vejnavn 7'', ''Tester 7'', 12345678),
       (6, ''Vejnavn 5'', ''Tester 5'', 12345678),
       (7, ''Vejnavn 3'', ''Tester 3'', 12345678),
       (8, ''Vejnavn 4'', ''Tester 4'', 12345678);
/*!40000 ALTER TABLE `owners` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles`
(
    `role_name` varchar(20) NOT NULL,
    PRIMARY KEY (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK
TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles`
VALUES (''admin''),
       (''user'');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles`
(
    `role_name` varchar(20) NOT NULL,
    `user_name` varchar(25) NOT NULL,
    PRIMARY KEY (`role_name`, `user_name`),
    KEY         `FK_user_roles_user_name` (`user_name`),
    CONSTRAINT `FK_user_roles_role_name` FOREIGN KEY (`role_name`) REFERENCES `roles` (`role_name`),
    CONSTRAINT `FK_user_roles_user_name` FOREIGN KEY (`user_name`) REFERENCES `users` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK
TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles`
VALUES (''admin'', ''admin''),
       (''user'', ''user'');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users`
(
    `user_name`      varchar(25) NOT NULL,
    `user_pass`      varchar(255) DEFAULT NULL,
    `user_pass_salt` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK
TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users`
VALUES (''admin'', ''$2a$10$tbwvy0cErRflE2Tgm/KCquGMxXmDaG3gGuI7sWnwzROK5f/pRmRqy'', ''$2a$10$tbwvy0cErRflE2Tgm/KCqu''),
       (''user'', ''$2a$10$SKICvBLbnlZALfwS4RhgBOqi0bTL/1T.6T68Rez77FOrB94GZ.z2O'', ''$2a$10$SKICvBLbnlZALfwS4RhgBO'');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK
TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-15 23:52:50
