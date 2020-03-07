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

-- Exportiere Daten aus Tabelle pseDb.comment: ~0 rows (ungefähr)
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;

-- Exportiere Struktur von Tabelle pseDb.favourites
CREATE TABLE IF NOT EXISTS `favourites` (
  `recipe_Id` int(11) NOT NULL,
  `user_Id` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`recipe_Id`,`user_Id`),
  KEY `FK_favourites_user` (`user_Id`),
  CONSTRAINT `FK_favourites_publicrecipe` FOREIGN KEY (`recipe_Id`) REFERENCES `public_recipe` (`recipe_Id`),
  CONSTRAINT `FK_favourites_user` FOREIGN KEY (`user_Id`) REFERENCES `user` (`user_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Exportiere Daten aus Tabelle pseDb.favourites: ~2 rows (ungefähr)
/*!40000 ALTER TABLE `favourites` DISABLE KEYS */;
INSERT INTO `favourites` (`recipe_Id`, `user_Id`) VALUES
	(2, 'admin'),
	(3, 'admin');
/*!40000 ALTER TABLE `favourites` ENABLE KEYS */;

-- Exportiere Struktur von Tabelle pseDb.follow
CREATE TABLE IF NOT EXISTS `follow` (
  `subscriber_Id` varchar(255) NOT NULL DEFAULT '',
  `follower_Id` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`subscriber_Id`,`follower_Id`),
  KEY `FK_follow_user_2` (`follower_Id`),
  CONSTRAINT `FK_follow_user` FOREIGN KEY (`subscriber_Id`) REFERENCES `user` (`user_Id`),
  CONSTRAINT `FK_follow_user_2` FOREIGN KEY (`follower_Id`) REFERENCES `user` (`user_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Exportiere Daten aus Tabelle pseDb.follow: ~0 rows (ungefähr)
/*!40000 ALTER TABLE `follow` DISABLE KEYS */;
/*!40000 ALTER TABLE `follow` ENABLE KEYS */;

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

-- Exportiere Daten aus Tabelle pseDb.group: ~0 rows (ungefähr)
/*!40000 ALTER TABLE `group` DISABLE KEYS */;
/*!40000 ALTER TABLE `group` ENABLE KEYS */;

-- Exportiere Struktur von Tabelle pseDb.ingredient_amount
CREATE TABLE IF NOT EXISTS `ingredient_amount` (
  `chapter_Id` int(11) NOT NULL,
  `name_Ingredient` varchar(255) NOT NULL DEFAULT '',
  `amount` double NOT NULL DEFAULT 0,
  `unit` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`chapter_Id`,`name_Ingredient`),
  CONSTRAINT `FK_ingredientamount_ingredientchapter` FOREIGN KEY (`chapter_Id`) REFERENCES `ingredient_chapter` (`chapter_Id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Exportiere Daten aus Tabelle pseDb.ingredient_amount: ~37 rows (ungefähr)
/*!40000 ALTER TABLE `ingredient_amount` DISABLE KEYS */;
INSERT INTO `ingredient_amount` (`chapter_Id`, `name_Ingredient`, `amount`, `unit`) VALUES
	(2, 'Butter', 80, 'g'),
	(2, 'Eier', 4, 'Stück'),
	(2, 'Mehl', 150, 'g'),
	(2, 'Salz', 1, 'Prise'),
	(2, 'Wasser', 250, 'ml'),
	(2, 'Zartbitter-Kuvertüre', 100, 'g'),
	(3, 'Sahne', 400, 'ml'),
	(3, 'Vollmilch-Schokolade', 100, 'g'),
	(3, 'Zartbitter-Schokolade', 100, 'g'),
	(4, 'braune Champignons', 200, 'g'),
	(4, 'Butter', 2, 'EL'),
	(4, 'Crème fraîche', 200, 'g'),
	(4, 'Essiggurken', 5, 'Stück'),
	(4, 'Mehl', 1, 'EL'),
	(4, 'Öl', 2, 'EL'),
	(4, 'Petersilie', 1, 'Bund'),
	(4, 'Rinderfilet', 250, 'g'),
	(4, 'Rinderfond', 200, 'ml'),
	(4, 'Salz', 1, 'Prise'),
	(4, 'saure Sahne', 200, 'g'),
	(4, 'schwarzer Pfeffer', 1, 'Prise'),
	(4, 'Zwiebel', 1, 'Stück'),
	(5, 'Eier', 2, 'Stück'),
	(5, 'Mehl', 400, 'g'),
	(5, 'Milch', 100, 'ml'),
	(5, 'Öl', 50, 'ml'),
	(5, 'Salz', 2, 'TL'),
	(6, 'Eier', 2, 'Stück'),
	(6, 'Hackfleisch vom Rind', 400, 'g'),
	(6, 'Knoblauchzehen', 2, 'Stück'),
	(6, 'Pfeffer', 1, 'TL'),
	(6, 'Piment', 1, 'Prise'),
	(6, 'Salz', 1, 'TL'),
	(6, 'Zwiebel', 1, 'Stück'),
	(7, 'Öl', 0, ''),
	(7, 'saure Sahne', 200, 'g'),
	(7, 'Zwiebel', 3, 'Stück');
/*!40000 ALTER TABLE `ingredient_amount` ENABLE KEYS */;

-- Exportiere Struktur von Tabelle pseDb.ingredient_chapter
CREATE TABLE IF NOT EXISTS `ingredient_chapter` (
  `chapter_Id` int(11) NOT NULL AUTO_INCREMENT,
  `recipe_Id` int(11) NOT NULL DEFAULT 0,
  `chapter_Name` varchar(255) NOT NULL DEFAULT '0',
  PRIMARY KEY (`chapter_Id`),
  KEY `FK_ingredientchapter_publicrecipe` (`recipe_Id`),
  CONSTRAINT `FK_ingredientchapter_publicrecipe` FOREIGN KEY (`recipe_Id`) REFERENCES `public_recipe` (`recipe_Id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4;

-- Exportiere Daten aus Tabelle pseDb.ingredient_chapter: ~6 rows (ungefähr)
/*!40000 ALTER TABLE `ingredient_chapter` DISABLE KEYS */;
INSERT INTO `ingredient_chapter` (`chapter_Id`, `recipe_Id`, `chapter_Name`) VALUES
	(2, 2, 'Für die Windbeutel'),
	(3, 2, 'Für die Schokofüllung'),
	(4, 3, 'Zutaten'),
	(5, 4, 'Für den Teig'),
	(6, 4, 'Für die Füllung'),
	(7, 4, 'Für die Garnitur');
/*!40000 ALTER TABLE `ingredient_chapter` ENABLE KEYS */;

-- Exportiere Struktur von Tabelle pseDb.private_recipe_tag
CREATE TABLE IF NOT EXISTS `private_recipe_tag` (
  `tag_Id` varchar(255) NOT NULL,
  `recipe_Id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`tag_Id`),
  KEY `FK_privaterecipetag_sharedprivaterecipe` (`recipe_Id`),
  CONSTRAINT `FK_privaterecipetag_sharedprivaterecipe` FOREIGN KEY (`recipe_Id`) REFERENCES `shared_private_recipe` (`recipe_Id`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Exportiere Daten aus Tabelle pseDb.private_recipe_tag: ~0 rows (ungefähr)
/*!40000 ALTER TABLE `private_recipe_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `private_recipe_tag` ENABLE KEYS */;

-- Exportiere Struktur von Tabelle pseDb.public_recipe
CREATE TABLE IF NOT EXISTS `public_recipe` (
  `recipe_Id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `ingredients_Text` varchar(500) NOT NULL DEFAULT '0',
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
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8mb4;

-- Exportiere Daten aus Tabelle pseDb.public_recipe: ~10 rows (ungefähr)
/*!40000 ALTER TABLE `public_recipe` DISABLE KEYS */;
INSERT INTO `public_recipe` (`recipe_Id`, `title`, `ingredients_Text`, `preparation_Description`, `picture`, `cooking_Time`, `preparation_Time`, `user_Id`, `creation_Date`, `portions`, `mark_As_Evil`) VALUES
	(2, 'Windbeutel mit Schokofüllung', '#Für die Windbeutel\r\n250 ml Wasser \r\n1 Prise Salz  \r\n80 g Butter \r\n150 g Mehl \r\n4 Eier \r\n100 g Zartbitter Kuvertüre \r\n#Für die Schokofüllung\r\n100 g Zartbitter Schokolade \r\n100 g Vollmilch Schokolade \r\n400 ml Sahne', 'Windbeutel sind leckere kleine Gebäckteilchen aus Brandteig mit einer wunderbar cremigen Füllung. Wir haben unsere kleinen Windbeutel ganz klassisch mit Sahne gefüllt und unter die Sahne noch jede Menge Schokolade gemischt, denn Schokolade macht selbst die besten Gebäckstücke noch leckerer.\r\nStep 1:\r\nZuerst kochst du das Wasser mit einer Prise Salz und der Butter in einem Topf auf. Den Topf vom Herd nehmen und schnell das Mehl mit einem Holzlöffel so lange mit der Butter-Wasser-Mischung verrühren, bis eine geschmeidige Teigkugel entsteht, die sich vom Topfboden löst. Dann den Teig kurz abkühlen lassen und den Backofen auf 200°C Ober-/Unterhitze vorheizen.\r\nStep 2:\r\nNun kannst du die Eier mit den Knethaken des Handrührgeräts nacheinander unter den Brandteig kneten.\r\nStep 3:\r\nDen Teig in einen Spritzbeutel mit großer Tülle geben und auf ein Backblech mit Backpapier kleine Häufchen spritzen. Am besten lässt du ein wenig Abstand zwischen den Windbeuteln, da diese im Ofen noch aufgehen. Nun lässt du die Windbeutel für 20 Minuten backen. Du darfst den Backofen zwischendurch nicht öffnen.\r\nStep 4:\r\nNach dem Backen die Windbeutel vom Blech nehmen und mit einem Messer oder einer Schere quer halbieren.\r\nStep 5:\r\nFür die Schokofüllung hackst du zuerst die Schokolade klein und gibst sie in eine Schüssel. Dann die Sahne in einem Topf erhitzen und über die Schokolade gießen. Die Sahne mit der Schokolade verrühren, bis eine gleichmäßige glatte Schokoladenmasse entsteht. Die Schokomasse muss nun mindestens drei Stunden im Kühlschrank abkühlen.\r\nStep 6:\r\nNach dem Abkühlen kannst du die Schoko-Sahne mit dem Handrührgerät cremig aufschlagen. Dann in einen Spritzbeutel füllen und in die Windbeutelhälften spritzen. Den Deckel der Windbeutel wieder aufsetzen.\r\nStep 7:\r\nDie Kuvertüre im Wasserbad schmelzen und über die Windbeutel träufeln.\r\nWas ist eigentlich Brandteig?\r\nUnser luftigen kleinen Windbeutel bestehen aus Brandteig. Aber was ist Brandteig eigentlich genau? Der Name kommt vom "Abbrennen" des Teiges. Das heißt, dass der Brandteig im Topf zubereitet wird und solange gegart wird, bis ein glatter Teig entsteht, der sich vom Topfboden löst. Dabei wird das Gluten im Mehl aktiviert, sodass der Teig verklebt. Später beim Backen möchte das Wasser aus dem Teig verdampfen, kann aber dank des verklebten Glutens nicht aus dem Teig. Die Windbeutel plustern sich auf und bekommen so die typischen Hohlräume.\r\nhttp://www.yumtamtam.de/Rezepte/Windbeutel-mit-zweierlei-F%C3%BCllung-Schokosahne-Blaubeer-Cr%C3%A8me.jsp', 'api/images/test/Windbeutel.jpg', 200, 15, 'Max', '2020-01-13', 1, 0),
	(3, 'Rindergeschnetzeltes in Champignon-Sahnesoße', '#Zutaten\r\n200 g Crème fraîche\r\n2 EL Butter\r\n200 ml Rinderfond\r\n1 Zwiebel\r\nSalz\r\n200 g saure Sahne\r\n250 g Rinderfilet\r\n5 Essiggurken\r\n1 Bund Petersilie\r\nschwarzer Pfeffer\r\n1 EL Mehl\r\n200 g braune Champignons\r\n2 EL Öl', 'Du liebst die französische und die russische Küche? Dann wird dir unser Rezept für Bœuf Stroganoff garantiert gefallen! Bœuf Stroganoff ist ein Gericht der russischen Küche und der französischen „Haute Cuisine“ und wurde früher vor allem in Spitzenrestaurants serviert. Aber keine Angst, unser Bœuf Stroganoff ist super einfach zubereitet! In unserem Bœuf Stroganoff treffen zarte Rinderfiletstreifen auf eine super cremige Champignon-Sahnesoße mit Zwiebeln und Cornichons. Zu unserem Bœuf Stroganoff kannst du beispielsweise Kartoffeln, Spätzle oder auch Klöße essen. Wir haben uns diesmal allerdings für Bandnudeln entschieden und finden die Kombination perfekt! Unser Bœuf Stroganoff ist wirklich super lecker und ganz schnell zubereitet. Probiere unser Rezept unbedingt einmal selbst aus, es lohnt sich!\r\nStep 1:\r\nZuerst schälst du die Zwiebeln und würfelst sie fein. Dann putzt du die Pilze und schneidest sie in Scheiben. Die Essiggurken in feine Streifen und das Fleisch in dickere Streifen schneiden. Das Rinderfilet salzen und pfeffern.\r\nStep 2:\r\nJetzt erhitzt du 2 EL Öl in einer Pfanne und brätst das Fleisch darin in zwei Portionen kurz und sehr kräftig an. Dann wieder herausnehmen und abdecken. Jetzt gibst du 2 EL Butter in die Pfanne und brätst die Pilze und die Zwiebeln darin hellbraun an. Jetzt stäubst du das Mehl über die Pilze, verrührst es kurz und löschst alles mit Rinderfond ab. Kurz köcheln lassen, dann die saure Sahne und die Crème fraîche einrühren und alles mit Salz, Pfeffer und Zitronensaft würzen.\r\nStep 3:\r\nDie Petersilienblätter zupfst du und hackst sie fein. Kurz vor Schluss das Fleisch in die heiße Sauce geben und sofort mit Petersilie bestreut servieren.\r\nhttp://www.yumtamtam.de/Rezepte/Beouf-Stroganoff.jsp', '', 45, 45, 'Max', '2020-01-13', 1, 0),
	(4, 'Piroggen', '', 'Für die Herstellung des Nudelteigs Eier, Salz, Öl und Milch in eine Rührschüssel geben, mit den Knethaken der Küchenmaschine auf der niedrigen Stufe verquirlen und langsam das Mehl hinzu geben. Sollte der Teig noch zu dünn sein, vorsichtig etwas mehr Mehl hinzu geben. Sollte er zu fest sein, einen EL kaltes Wasser hinzu geben. https://www.chefkoch.de/rezepte/956621201246732/Piroggen.html', '', 1, 1, 'Max', '2020-01-17', 4, 0),
	(67, 'Indisches Butterhähnchen', '', '#Zutaten\n\n1 Zwiebel, feinst gehackt\n800g Hähnchenbrustfilet, mundgerecht geschnitten\n\n2 Knoblauchzehen, zerdrückt\n2 cm Ingwer, geschält und gerieben\n1 EL mildes oder scharfes Currypulver\n1-2 TL rote oder gelbe Currypaste\n1 TL Kurkuma\n1 EL Garam Masala (hatte selbst gemischtes)\n1/4 TL Salz\n70 g Tomatenmark (1 Döschen)\n400 ml Kokosmilch\n2 EL Joghurt\n2 EL Butter, flüssig\n\nZum Servieren\nReis oder Naanbrot\nFrühlingszwiebeln\n\n\n\n=\nKochzeit\n\n7Std Low/3,5Std High\n\n\n\n#Zutaten\n\n1 Zwiebel, feinst gehackt\n800g Hähnchenbrustfilet, mundgerecht geschnitten\n\n2 Knoblauchzehen, zerdrückt\n2 cm Ingwer, geschält und gerieben\n1 EL mildes oder scharfes Currypulver\n1-2 TL rote oder gelbe Currypaste\n1 TL Kurkuma\n1 EL Garam Masala (hatte selbst gemischtes)\n1/4 TL Salz\n70 g Tomatenmark (1 Döschen)\n400 ml Kokosmilch\n2 EL Joghurt\n2 EL Butter, flüssig\n\nZum Servieren\nReis oder Naanbrot\nFrühlingszwiebeln\n\n\n=====\nZubereitung\n\n1. Die Zwiebel auf den Boden des Slow-Cooker-Einsatzes streuen, darauf das Fleisch schichten. Alle weiteren Zutaten\nverrühren und nach Wunsch abschmecken (wer es nicht so scharf mag, verwendet mildes Currypulver und wenig oder\nkeine Currypaste).\n2. Den Deckel aufsetzen und das Gericht auf LOW 7 Stunden garen (HIGH 3,5 Stunden)\n3. Am Ende einmal gründlich durchrühren und mit gehackten Frühlingszwiebeln bestreut servieren. Dazu Reis oder Naanbrot\nreichen\n\n=====\nInfos\n\nHatte halben Ansatz gemacht, passte gut in 0,75Box, bei 77 Grad in Tücher eingeschlagen gekocht. (und vorher die Sauce erhitzt und dann drübergegeben) \nwar am Ende Durschnittlich 65 Grad heiss (oben ca 60) . Hervorragendes saftiges Fleisch, Zwiebeln waren gleichzeitig noch knackig. \n\n', 'api/images/Dummy/IMG_20200217_002627.jpg', 50, 500, 'Dummy', '2020-02-17', 0, 0),
	(68, 'Gartensalat', '', 'Salat mit Portulak aus dem Garten gepflückt', 'api/images/Dummy/IMG_20190416_181033-03_1.jpeg', 0, 15, 'Dummy', '2020-02-17', 0, 0),
	(69, 'Linzertorte', '', '=====\nZutaten (Boden) \n300g Mehl\n300g Zucker\n300g Butter\n300g geriebene Mandeln\n1 Ei\n1 Teelöffel Kakao\n1 Messerspitze Zimt\n3 Prisen Nelkenpulver\n1 – 2 Esslöffel Kirschwasserl\n\n=====\nZutaten (Belag) \n5 Esslöffel Marmelade \n(Halb Himbermarmelade halb Johannisbeergelee laut Frieder)\nEigelb zum Bestreichen\n\n=====\nZubereitung:\nDie Butter wird in feine Würfel geschnitten und zu den restlichen Zutaten hinzugegeben und zu einem Teig zusammen geknetet. Den fertigen Teig lässt man 1 Stunde ruhen.\nAnschlie­ßend wird der größere Teil des Teiges zu einem dicken Boden ausgerollt und bestreicht diesen mit 4 Esslöffeln Johannisbeer- oder Himbeermarmelade. (Frieder nimmt halb rotes Joharnisbeergelee und Himbeermarmelade)\n\n\nDie zweite Hälfte des Teigs wird ¾ cm dick ausgerollt.. Mit einem Weinglas oder runden Plätzchenformen  werden erst Kreise ausgestochen, welche dann nochmal mit dem Weinglas sozusagen halbiert werden, so dass man zwei längliche Formen hat, die an beiden Enden spitz zulaufen. Diese setzt man auf den Kuchenboden., so dass immer vier in einem Viereck liegen. Wahlweise können auch andere Formen ausgestochen werden.\nDer Kuchen wird mit verrührtem Ei bestrichen und bei 175°C ohne vorheizen 45 Minuten gebacken. Dann wird der Ofen auf 150°C runter gestellt, der Kuchen abgedeckt und weitere 20 Minuten gebacken.\nDer Kuchen sollte zwei bis drei Tage durchziehen, bis er gegessen wird.\n\n=====\nBackanweisungen Gasofen: \nOhne Vorheizen gestartet auf Stufe 7 zweituntersterster Einschub auf dem Rost. Nach 15 Minuten\nBlech eingeschoben, in untersten Einschub und da ein hoches Blech umgekehrt drauf, um die Hitze von unten zu reduzuieren. Nach insgesamt 30 Minuten runtergedreht auf Stufe 2 (Thermometer hat 250 Grad angezeigt). Nach 45 Minuten ausgemacht und noch 20 Minuten drin gelassen. ', 'api/images/Dummy/IMG_20200217_003941.jpg', 0, 0, 'Dummy', '2020-02-17', 0, 0),
	(70, 'Rosemary Gin Fizz Cocktail', '', '=====\nZutaten: \n2 ounces (1/4 cup) gin\n1/2 ounce Zitronensaft\n1/2 ounce Rosmarinsirup (Rezept unten.) (Wir hatten doppelt so viel) \n3 ounces (1/3 cup) Sprudel\n1 Rosmarinzweig, fürs Glas\noptional 1 Zitronenscheibe, fürs Glas\n\n\n=====\nRosmarinsirupzutaten: \nRosemary Simple Syrup\n1/2 cup sugar\n1/2 cup water\n1 Rosmarinzweig \n\nZucker und Wasser mischen erhitzen, dann von der Kochplatte nehmen, wenn es unter 100 Grad ist, Rosmarinzweig reinlegen und am Besten 1 Stunde ziehen, lassen. \n\n\n======\nFazit: \nWar gut, Rohrohrzucker ist nicht so gut, hatten doppelte Menge Sirup drin so wies angegeben war wars zu wenig. Crushed ice könnte man irgendwie noch besser crushen. \n\n', 'api/images/Dummy/IMG_20200217_004147.jpg', 0, 0, 'Dummy', '2020-02-17', 0, 0),
	(71, 'Bohnensuppe mit zweierlei Bohnen', '', 'Zutaten:\n100g getrocknete weisse Bohnen. 45 Minuten in reinem Wasser ohne Salz gekocht. \n4 Kartoffeln, gewürfelt nicht zu grosse Stücke\neine Karotte in halbe Scheibchen geschnitten. \n4 Frische Tomaten.(2 Marzano, und noch ein paar Rutje waren es) \neine grosse Hand voll Feuerbohnen. \n1 grosse Zwiebel gwewürfelt\neine halbe Stange Lauch\n30g Risottoreis.\nGemüsebrühe \nzum Verzieren: \nOregano, Bohnenkraut, Thymian, 2 Blätter Salbei\nbeim Servieren. Kerbel\n\nfür die nicht vegane Version: 100g Speckwürfel und wenn vorhanden Speckschwarte\n\nweise Bohnen werden vorgekocht (wenn sie über Nacht eingeweicht waren brauchen sie vermutlich kürzer als 45 Minuten ) \n\nZwiebel wird mit Lauch und Karotte angebraten. Bohnen werden abgegossen, mit 1 Liter Gemüsebrühe aufgefüllt. Gebratenes Gemüse hinzugegeben zu den Bohnen gegeben, Kräuter bis auf Kerbel hinzu köcheln lassen.  Tomaten würfeln in der Pfanne anbraten, so dass sie etwas karamelisieren und auch hinzugegeben. ggfs. noch ein Löffel Tomatenmark hinzugegeben. Feuerbohnen in 5cm Stücke geschnitten und in Wasser mit Bohnenkraut 15 Minuten gekocht, abgeschreckt und auch hinzugegeben. Dann kommen Feuerbohnen Kartoffeln und Risottoreis in den Topf, vielleicht noch etwas Wasser hinzu. \nUnter gelegentlichem Rühren (brennt mit dem Reis leicht an) auf kleinster Flamme köcheln und mit Salz und Pfeffer  abschmecken. \n', 'api/images/Dummy/IMG_20200217_004605.jpg', 0, 0, 'Dummy', '2020-02-17', 0, 0),
	(72, 'Polenta mit Ofengemüse', '', 'Zutaten für 2 Portionen:\neine kleine Zwiebel\nca 250 g  Süßkartoffeln\nca. 150 g rote Beete\nca. 100 g Fenchel\nca. 40 g Staudensellerie\n1 Gelberübe\nThymian\n1 Schuss Weißwein\n60 g Mangold\n600 ml Fond\n120 g Polenta\n20 g Parmesan\n4 EL Schlagsahne\netwas Crema di Balsamico (süß)\n\nZubereitung:\nGemüse Putzen und ggf. schälen. Zwiebeln und Sellerie fein würfeln, rote Beete, Süßkartoffel, Fenchel, Karotte etwas gröber. In einer ofenfesten Pfanne 4 Minuten in Olivenöl kräftig andünsten. Thymian dazu geben.\nDann im vorgeheizten Ofen 20 Minuten bei 200°C Umluft garen.\nFür die Polenta Wasser, Polenta und Gemüsebrühe mischen und dann erhitzen. 4 min. köcheln lassen und dann 10 Minuten mit Deckel stehen lassen. Dann Sahne und Parmesan unterrühren.\nDas Gemüse aus dem Ofen holen mit einem Schuss Weißwein ablöschen und den Mangold dazu. Abgedeckt kurz stehen lassen.\n', 'api/images/Dummy/IMG_20200217_004922.jpg', 0, 0, 'Dummy', '2020-02-17', 0, 0),
	(73, 'Dampfnudeln neues Rezept', '', 'von dem Plötzblog-Brotauthor)\nsind hervorragend. \n\n=====\nFazit: gutes Rezept in dem kleinen beschichteten 20cm-Topf kann man die hervorragend auch in kleiner Menge backen. 6 Stück gehen da rein. \n\n=====\nVorteig\n\n200 g Weizenmehl 550 /250\n200 g Milch /250\n0,5 g Frischhefe /2\n\nDie Vorteigzutaten mischen und 12-16 Stunden bei Raumtemperatur reifen lassen.\n\n=====\nHauptteig\n\nVorteig\n300 g Weizenmehl 550 /375\n110 g Milch /137\n15 g Frischhefe /19g\n5 g Salz /6\n\n=====\n\nNun Kochen/braten\n\n1 Esslöffel Schmalz\n1/2 Esslöffel Butter\n5 g Salz\nWasser\n\n=====\nZubereitung:\n\nAlle Zutaten 5 Minuten auf niedrigster Stufe zu einem Teig mischen und diesen anschließend 10-15 Minuten auf zweiter Stufe kräftig auskneten.\n\n1 Stunde warm gehen lassen.\n\nDen Teig nochmals durchkneten und 80-90 g-Stücke abstechen. Zu runden Teiglingen formen und abgedeckt 1 Stunde zur Gare stellen.\n\nEine sehr gut beschichtete oder gusseiserne Pfanne (hatte einen beschichten Topf mit Glasdeckel, da passten 6 Stück auf einmal ein) mit Glasdeckel ca. 0,8-1 cm hoch mit Wasser füllen, Schmalz, Butter und Salz zugeben und auf höchster Stufe aufkochen lassen. Sobald das Wasser kocht, die Teiglinge eng anliegend in die Pfanne setzen und den Glasdeckel schließen. 3 Minuten auf höchster Stufe kochen lassen. Weitere 15 Minuten auf 2/3-Hitze kochen lassen. Das Wasser sollte bis dahin komplett verbraucht sein. Auch am Deckel sollte sich kaum mehr Wasser befinden.\n*man hört auch einen Unterschied im Klang wenn das brutzeln langsam wieder aufhört sind sie fertig. \n\nDen Deckel vorsichtig hochheben und schnell umdrehen, sodass keinerlei Wasser vom Deckel auf die Dampfnudeln tropft.\nDie Dampfnudeln sollten an der Unterseite eine knusprige, braune Kruste ausgebildet haben.\n\n=====\nAlternative: Übernachtgare\nIch habe die Dampfnudeln inzwischen auch mit Übernachtgare erfolgreich hergestellt. Dazu habe ich einfach 3 g Frischhefe statt 15 g genommen und nach dem Formen die Teiglinge abgedeckt für ca. 12 Stunden im Kühlschrank bei 6-8°C gelagert.\n\n=====\nAnmerkung:\nRezept reicht für ca. 12 Dampfnudeln. \nDas sind wahrscheinlich gut ausreichtend für 3 Leute als Hauptgericht. Wir waren zu viert. \nDen Schmalz hatte ich weg gelassen. \nDie 1. Gare aus Zeitmangel nur 5 h lang mit etwas mehr Hefe (2g?). Ging auch. \nDazu 1/2l Vanillesoße und Apfelkompott aus 5 großen Boskop mit etwas Vanillezucker, Zitronenschale und einer Zimtstange. \nSehr, sehr leckere Kombination. \n', 'api/images/admin/IMG_20200217_010429.jpg', 0, 0, 'admin', '2020-02-17', 0, 0);
/*!40000 ALTER TABLE `public_recipe` ENABLE KEYS */;

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

-- Exportiere Daten aus Tabelle pseDb.recipe_rating: ~0 rows (ungefähr)
/*!40000 ALTER TABLE `recipe_rating` DISABLE KEYS */;
/*!40000 ALTER TABLE `recipe_rating` ENABLE KEYS */;

-- Exportiere Struktur von Tabelle pseDb.recipe_tag
CREATE TABLE IF NOT EXISTS `recipe_tag` (
  `tag_Id` varchar(255) NOT NULL DEFAULT '',
  `recipe_Id` int(11) NOT NULL,
  KEY `FK_recipeTag_publicrecipe` (`recipe_Id`),
  CONSTRAINT `FK_recipeTag_publicrecipe` FOREIGN KEY (`recipe_Id`) REFERENCES `public_recipe` (`recipe_Id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Exportiere Daten aus Tabelle pseDb.recipe_tag: ~2 rows (ungefähr)
/*!40000 ALTER TABLE `recipe_tag` DISABLE KEYS */;
INSERT INTO `recipe_tag` (`tag_Id`, `recipe_Id`) VALUES
	('fleisch', 3),
	('sweet', 2),
	('vegetarisch', 68);
/*!40000 ALTER TABLE `recipe_tag` ENABLE KEYS */;

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

-- Exportiere Daten aus Tabelle pseDb.shared_private_recipe: ~0 rows (ungefähr)
/*!40000 ALTER TABLE `shared_private_recipe` DISABLE KEYS */;
/*!40000 ALTER TABLE `shared_private_recipe` ENABLE KEYS */;

-- Exportiere Struktur von Tabelle pseDb.user
CREATE TABLE IF NOT EXISTS `user` (
  `user_Id` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `description` varchar(255) NOT NULL,
  `image_Url` varchar(255) DEFAULT '',
  `mark_As_Evil` tinyint(1) DEFAULT NULL,
  `isAdmin` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`user_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Exportiere Daten aus Tabelle pseDb.user: ~6 rows (ungefähr)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`user_Id`, `email`, `description`, `image_Url`, `mark_As_Evil`, `isAdmin`) VALUES
	('admin', 'admin@admin.de', '', '', 0, 1),
	('Dummy', 'whmeier@gmx.de', '', '', 0, 0),
	('KochDummy', 'nutzerohneid@id.de', '', '', 0, 0),
	('Max', 'max.musterman@muster.de', 'Ich bin ein Muster', '', 0, 0),
	('Test', 'test@test.de', '', '', 0, 0),
	('test2', 'test3@test.de', 'Test', 'Test', 0, 0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Exportiere Struktur von Tabelle pseDb.user_group
CREATE TABLE IF NOT EXISTS `user_group` (
  `group_Id` int(11) NOT NULL,
  `user_Id` varchar(255) NOT NULL,
  PRIMARY KEY (`group_Id`,`user_Id`),
  KEY `FK__user` (`user_Id`),
  CONSTRAINT `FK__group` FOREIGN KEY (`group_Id`) REFERENCES `group` (`group_Id`),
  CONSTRAINT `FK__user` FOREIGN KEY (`user_Id`) REFERENCES `user` (`user_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Exportiere Daten aus Tabelle pseDb.user_group: ~0 rows (ungefähr)
/*!40000 ALTER TABLE `user_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_group` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
