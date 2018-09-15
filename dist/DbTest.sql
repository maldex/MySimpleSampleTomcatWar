DROP DATABASE IF EXISTS DbTest;
CREATE DATABASE DbTest;
USE DbTest;
CREATE TABLE `TblTest` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text CHARACTER SET latin1 COLLATE latin1_german1_ci NOT NULL,
  `note` text CHARACTER SET latin1 COLLATE latin1_german1_ci,
  PRIMARY KEY (`id`)
);
INSERT INTO `TblTest` VALUES (1,'hans','isst ne wurst'),(2,'fritz','liebt fisch'),(3,'peter','\'\''),(4,'that guy','\'\'');

SELECT * FROM `TblTest`;
