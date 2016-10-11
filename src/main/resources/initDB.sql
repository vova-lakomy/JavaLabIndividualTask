DROP DATABASE IF EXISTS contacts_vladimir_lakomy;
CREATE DATABASE contacts_vladimir_lakomy  CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

USE contacts_vladimir_lakomy;

CREATE TABLE contacts_vladimir_lakomy.contact
(
  id                  INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  first_name          VARCHAR(20),
  second_name         VARCHAR(20),
  last_name           VARCHAR(20),
  date_of_birth       DATE,
  sex                 VARCHAR(6),
  nationality         VARCHAR(20),
  marital_status      VARCHAR(17),
  web_site            VARCHAR(100),
  e_mail              VARCHAR(50),
  current_job         VARCHAR(30),
  photo_link          VARCHAR(200),
  country             VARCHAR(30),
  town                VARCHAR(30),
  street              VARCHAR(30),
  house_number        INT(11),
  flat_number         INT(11),
  zip_code            INT(11),
  personal_link       VARCHAR(40)

) ENGINE = InnoDB;

CREATE TABLE contacts_vladimir_lakomy.contact_attachment
(
  id                  INT PRIMARY KEY  NOT NULL AUTO_INCREMENT,
  attachment_name     VARCHAR(70),
  attachment_link     VARCHAR(250),
  attachment_comment  VARCHAR(1000),
  date_of_upload      DATE,
  contact_id          INT(11),
  FOREIGN KEY (contact_id)
    REFERENCES contacts_vladimir_lakomy.contact(id)
    ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE contacts_vladimir_lakomy.phone_number
(
  id                  INT PRIMARY KEY  NOT NULL AUTO_INCREMENT,
  country_code        INT(11),
  operator_code       INT(11),
  phone_number        INT(11),
  phone_type          VARCHAR(10),
  phone_comment       VARCHAR(1000),
  contact_id          INT,
  FOREIGN KEY (contact_id)
    REFERENCES contacts_vladimir_lakomy.contact(id)
    ON DELETE CASCADE
) ENGINE = InnoDB;