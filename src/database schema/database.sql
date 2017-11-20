-- MySQL Script generated by MySQL Workbench
-- Mon Nov 20 14:37:10 2017
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Task`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Task` (
  `TaskID` INT NOT NULL AUTO_INCREMENT,
  `TaskName` VARCHAR(250) NOT NULL,
  `TaskPriority` INT NOT NULL,
  `TaskDate` DATE NOT NULL,
  `TaskCategory` ENUM('Personal', 'Work') NOT NULL,
  `user_UserID` INT NOT NULL,
  PRIMARY KEY (`TaskID`));

CREATE INDEX `UserID` ON `mydb`.`Task` (`user_UserID` ASC);


-- -----------------------------------------------------
-- Table `mydb`.`Team`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Team` (
  `TeamName` VARCHAR(200) NULL DEFAULT CURRENT_TIMESTAMP,
  `TeamID` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`TeamID`));

CREATE UNIQUE INDEX `TeamID_UNIQUE` ON `mydb`.`Team` (`TeamID` ASC);


-- -----------------------------------------------------
-- Table `mydb`.`TeamMembers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`TeamMembers` (
  `Team_TeamID` INT NOT NULL,
  `User_id` INT NOT NULL,
  PRIMARY KEY (`Team_TeamID`, `User_id`),
  CONSTRAINT `fk_TeamMembers_Team1`
    FOREIGN KEY (`Team_TeamID`)
    REFERENCES `mydb`.`Team` (`TeamID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_id`
    FOREIGN KEY (`User_id`)
    REFERENCES `mydb`.`Task` (`user_UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE INDEX `fk_TeamMembers_Team1_idx` ON `mydb`.`TeamMembers` (`Team_TeamID` ASC);

CREATE INDEX `fk_user_id_idx` ON `mydb`.`TeamMembers` (`User_id` ASC);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;