-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema famous_quotes
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `famous_quotes` ;

-- -----------------------------------------------------
-- Schema famous_quotes
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `famous_quotes` DEFAULT CHARACTER SET utf8 ;
USE `famous_quotes` ;

-- -----------------------------------------------------
-- Table `famous_quotes`.`author`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `famous_quotes`.`author` ;

CREATE TABLE IF NOT EXISTS `famous_quotes`.`author` (
  `idauthor` VARCHAR(36) NOT NULL,
  `author` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idauthor`),
  UNIQUE INDEX `idauthor_UNIQUE` (`idauthor` ASC) VISIBLE,
  UNIQUE INDEX `authorcol_UNIQUE` (`author` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `famous_quotes`.`quote`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `famous_quotes`.`quote` ;

CREATE TABLE IF NOT EXISTS `famous_quotes`.`quote` (
  `idquote` VARCHAR(36) NOT NULL,
  `quote` MEDIUMTEXT NOT NULL,
  `idauthor` VARCHAR(36) NOT NULL,
  PRIMARY KEY (`idquote`),
  UNIQUE INDEX `idquote_UNIQUE` (`idquote` ASC) VISIBLE,
  INDEX `fk_quote_author_idx` (`idauthor` ASC) VISIBLE,
  CONSTRAINT `fk_quote_author`
    FOREIGN KEY (`idauthor`)
    REFERENCES `famous_quotes`.`author` (`idauthor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SET SQL_MODE = '';
GRANT USAGE ON *.* TO qtmgr;
 DROP USER qtmgr;
SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
CREATE USER 'qtmgr' IDENTIFIED BY 'qtmgradm99';

GRANT SELECT, INSERT, TRIGGER ON TABLE `famous_quotes`.* TO 'qtmgr';
GRANT ALL ON `famous_quotes`.* TO 'qtmgr';
GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE `famous_quotes`.* TO 'qtmgr';
SET SQL_MODE = '';
GRANT USAGE ON *.* TO qtmgr@localhost;
 DROP USER qtmgr@localhost;
SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
CREATE USER 'qtmgr'@'localhost' IDENTIFIED BY 'qtmgradm99';

GRANT ALL ON `famous_quotes`.* TO 'qtmgr'@'localhost';
GRANT SELECT, INSERT, TRIGGER ON TABLE `famous_quotes`.* TO 'qtmgr'@'localhost';
GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE `famous_quotes`.* TO 'qtmgr'@'localhost';
SET SQL_MODE = '';
GRANT USAGE ON *.* TO qtmgr@127.0.0.1;
 DROP USER qtmgr@127.0.0.1;
SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
CREATE USER 'qtmgr'@'127.0.0.1' IDENTIFIED BY 'qtmgradm99';

GRANT ALL ON `famous_quotes`.* TO 'qtmgr'@'127.0.0.1';
GRANT SELECT, INSERT, TRIGGER ON TABLE `famous_quotes`.* TO 'qtmgr'@'127.0.0.1';
GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE `famous_quotes`.* TO 'qtmgr'@'127.0.0.1';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- begin attached script 'script'
insert into author (idauthor, author) values ('831f601c-6dae-11e9-a4c5-3497f699471c', 'Anonymous');
insert into quote (idquote, quote, idauthor) values 
(UUID(), 'Never say, "oops!".', '831f601c-6dae-11e9-a4c5-3497f699471c'),
(UUID(), 'A mistake should be your teacher, not your attacker.', '831f601c-6dae-11e9-a4c5-3497f699471c'),
(UUID(), 'So many good things come in pairs, like ears, socks and panda bears.', '831f601c-6dae-11e9-a4c5-3497f699471c'),
(UUID(), 'A good neighbor will babysit.', '831f601c-6dae-11e9-a4c5-3497f699471c'),
(UUID(), 'All the talent in the world won\'t take you anywhere without your teammates.', '831f601c-6dae-11e9-a4c5-3497f699471c');
-- end attached script 'script'