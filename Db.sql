-- --------------------------------------------------------
-- Host:                         193.196.38.185
-- Server Version:               10.3.18-MariaDB-0+deb10u1 - Debian 10
-- Server Betriebssystem:        debian-linux-gnu
-- HeidiSQL Version:             10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Exportiere Datenbank Struktur für pseDb
CREATE DATABASE IF NOT EXISTS `pseDb` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `pseDb`;

-- Exportiere Struktur von Tabelle pseDb.comment
CREATE TABLE IF NOT EXISTS `comment` (
  `comment_Id` int(11) NOT NULL AUTO_INCREMENT,
  `user_Id` varchar(255) NOT NULL DEFAULT '0',
  `recipe_Id` int(11) NOT NULL DEFAULT 0,
  `comment` varchar(255) NOT NULL DEFAULT '0',
  `date` date NOT NULL DEFAULT curdate(),
  `mark_As_Evil` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`comment_Id`),
  KEY `FK_comment_user` (`user_Id`),
  KEY `FK_comment_publicrecipe` (`recipe_Id`),
  CONSTRAINT `FK_comment_publicrecipe` FOREIGN KEY (`recipe_Id`) REFERENCES `public_recipe` (`recipe_Id`),
  CONSTRAINT `FK_comment_user` FOREIGN KEY (`user_Id`) REFERENCES `user` (`user_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Daten Export vom Benutzer nicht ausgewählt

-- Exportiere Struktur von Tabelle pseDb.favourites
CREATE TABLE IF NOT EXISTS `favourites` (
  `recipe_Id` int(11) NOT NULL,
  `user_Id` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`recipe_Id`,`user_Id`),
  KEY `FK_favourites_user` (`user_Id`),
  CONSTRAINT `FK_favourites_publicrecipe` FOREIGN KEY (`recipe_Id`) REFERENCES `public_recipe` (`recipe_Id`),
  CONSTRAINT `FK_favourites_user` FOREIGN KEY (`user_Id`) REFERENCES `user` (`user_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Daten Export vom Benutzer nicht ausgewählt

-- Exportiere Struktur von Tabelle pseDb.follow
CREATE TABLE IF NOT EXISTS `follow` (
  `subscriber_Id` varchar(255) NOT NULL DEFAULT '',
  `follower_Id` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`subscriber_Id`,`follower_Id`),
  KEY `FK_follow_user_2` (`follower_Id`),
  CONSTRAINT `FK_follow_user` FOREIGN KEY (`subscriber_Id`) REFERENCES `user` (`user_Id`),
  CONSTRAINT `FK_follow_user_2` FOREIGN KEY (`follower_Id`) REFERENCES `user` (`user_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Daten Export vom Benutzer nicht ausgewählt

-- Exportiere Struktur von Tabelle pseDb.group
CREATE TABLE IF NOT EXISTS `group` (
  `group_Id` int(11) NOT NULL AUTO_INCREMENT,
  `main_User` varchar(255) NOT NULL DEFAULT '0',
  `name` varchar(255) NOT NULL DEFAULT '0',
  `mark_As_Evil` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`group_Id`),
  KEY `FK_group_user` (`main_User`),
  CONSTRAINT `FK_group_user` FOREIGN KEY (`main_User`) REFERENCES `user` (`user_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- Daten Export vom Benutzer nicht ausgewählt

-- Exportiere Struktur von Tabelle pseDb.ingredient_amount
CREATE TABLE IF NOT EXISTS `ingredient_amount` (
  `chapter_Id` int(11) NOT NULL,
  `name_Ingredient` varchar(255) NOT NULL DEFAULT '',
  `amount` double NOT NULL DEFAULT 0,
  `unit` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`chapter_Id`,`name_Ingredient`),
  CONSTRAINT `FK_ingredientamount_ingredientchapter` FOREIGN KEY (`chapter_Id`) REFERENCES `ingredient_chapter` (`chapter_Id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Daten Export vom Benutzer nicht ausgewählt

-- Exportiere Struktur von Tabelle pseDb.ingredient_chapter
CREATE TABLE IF NOT EXISTS `ingredient_chapter` (
  `chapter_Id` int(11) NOT NULL AUTO_INCREMENT,
  `recipe_Id` int(11) NOT NULL DEFAULT 0,
  `chapter_Name` varchar(255) NOT NULL DEFAULT '0',
  PRIMARY KEY (`chapter_Id`),
  KEY `FK_ingredientchapter_publicrecipe` (`recipe_Id`),
  CONSTRAINT `FK_ingredientchapter_publicrecipe` FOREIGN KEY (`recipe_Id`) REFERENCES `public_recipe` (`recipe_Id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4;

-- Daten Export vom Benutzer nicht ausgewählt

-- Exportiere Struktur von Tabelle pseDb.private_recipe_tag
CREATE TABLE IF NOT EXISTS `private_recipe_tag` (
  `tag_Id` varchar(255) NOT NULL,
  `recipe_Id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`tag_Id`),
  KEY `FK_privaterecipetag_sharedprivaterecipe` (`recipe_Id`),
  CONSTRAINT `FK_privaterecipetag_sharedprivaterecipe` FOREIGN KEY (`recipe_Id`) REFERENCES `shared_private_recipe` (`recipe_Id`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Daten Export vom Benutzer nicht ausgewählt

-- Exportiere Struktur von Tabelle pseDb.public_recipe
CREATE TABLE IF NOT EXISTS `public_recipe` (
  `recipe_Id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `ingredients_Text` varchar(255) NOT NULL DEFAULT '0',
  `preparation_Description` varchar(5000) NOT NULL,
  `picture` varchar(500) DEFAULT NULL,
  `cooking_Time` int(11) NOT NULL DEFAULT 0,
  `preparation_Time` int(11) NOT NULL DEFAULT 0,
  `user_Id` varchar(50) DEFAULT '0',
  `creation_Date` date NOT NULL,
  `portions` int(11) NOT NULL DEFAULT 0,
  `mark_As_Evil` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`recipe_Id`),
  KEY `FK_publicrecipe_user` (`user_Id`),
  CONSTRAINT `FK_publicrecipe_user` FOREIGN KEY (`user_Id`) REFERENCES `user` (`user_Id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb4;

-- Daten Export vom Benutzer nicht ausgewählt

-- Exportiere Struktur von Tabelle pseDb.recipe_rating
CREATE TABLE IF NOT EXISTS `recipe_rating` (
  `recipe_Id` int(11) NOT NULL,
  `user_Id` varchar(255) NOT NULL,
  `points` int(11) NOT NULL,
  PRIMARY KEY (`recipe_Id`,`user_Id`),
  KEY `FK_reciperating_user` (`user_Id`),
  CONSTRAINT `FK_reciperating_publicrecipe` FOREIGN KEY (`recipe_Id`) REFERENCES `public_recipe` (`recipe_Id`),
  CONSTRAINT `FK_reciperating_user` FOREIGN KEY (`user_Id`) REFERENCES `user` (`user_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Daten Export vom Benutzer nicht ausgewählt

-- Exportiere Struktur von Tabelle pseDb.recipe_tag
CREATE TABLE IF NOT EXISTS `recipe_tag` (
  `tag_Id` varchar(255) NOT NULL DEFAULT '',
  `recipe_Id` int(11) NOT NULL,
  KEY `FK_recipeTag_publicrecipe` (`recipe_Id`),
  CONSTRAINT `FK_recipeTag_publicrecipe` FOREIGN KEY (`recipe_Id`) REFERENCES `public_recipe` (`recipe_Id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Daten Export vom Benutzer nicht ausgewählt

-- Exportiere Struktur von Tabelle pseDb.shared_private_recipe
CREATE TABLE IF NOT EXISTS `shared_private_recipe` (
  `recipe_Id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `ingredients_Text` varchar(255) NOT NULL,
  `preparation_Description` varchar(500) NOT NULL,
  `picture` blob DEFAULT NULL,
  `cooking_Time` int(11) NOT NULL DEFAULT 0,
  `preparation_Time` int(11) NOT NULL DEFAULT 0,
  `user_Id` varchar(50) NOT NULL DEFAULT '0',
  `creation_Date` date NOT NULL,
  `mark_As_Evil` tinyint(1) DEFAULT NULL,
  `portions` int(11) NOT NULL DEFAULT 0,
  `group_Id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`recipe_Id`),
  KEY `FK_sharedprivaterecipe_user` (`user_Id`),
  KEY `FK_sharedprivaterecipe_group` (`group_Id`),
  CONSTRAINT `FK_sharedprivaterecipe_group` FOREIGN KEY (`group_Id`) REFERENCES `group` (`group_Id`),
  CONSTRAINT `FK_sharedprivaterecipe_user` FOREIGN KEY (`user_Id`) REFERENCES `user` (`user_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Daten Export vom Benutzer nicht ausgewählt

-- Exportiere Struktur von Tabelle pseDb.user
CREATE TABLE IF NOT EXISTS `user` (
  `user_Id` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `description` varchar(255) NOT NULL,
  `mark_As_Evil` tinyint(1) DEFAULT NULL,
  `isAdmin` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`user_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Daten Export vom Benutzer nicht ausgewählt

-- Exportiere Struktur von Tabelle pseDb.user_group
CREATE TABLE IF NOT EXISTS `user_group` (
  `group_Id` int(11) NOT NULL,
  `user_Id` varchar(255) NOT NULL,
  PRIMARY KEY (`group_Id`,`user_Id`),
  KEY `FK__user` (`user_Id`),
  CONSTRAINT `FK__group` FOREIGN KEY (`group_Id`) REFERENCES `group` (`group_Id`),
  CONSTRAINT `FK__user` FOREIGN KEY (`user_Id`) REFERENCES `user` (`user_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Daten Export vom Benutzer nicht ausgewählt

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
