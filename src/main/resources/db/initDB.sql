DROP DATABASE IF EXISTS contacts_vladimir_lakomy;
CREATE DATABASE contacts_vladimir_lakomy;

USE contacts_vladimir_lakomy;

CREATE TABLE contacts_vladimir_lakomy.contact_address
(
  id                  INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  country             VARCHAR(30),
  town                VARCHAR(30),
  street              VARCHAR(30),
  house_number        INT,
  flat_number         INT,
  zap_code            INT
);

CREATE TABLE contacts_vladimir_lakomy.contact_attachment
(
  id                  INT PRIMARY KEY  NOT NULL AUTO_INCREMENT,
  attachment_link     VARCHAR(200),
  attachment_comment  VARCHAR(255),
  contact_id          INT
);

CREATE TABLE contacts_vladimir_lakomy.phone_number
(
  id                  INT PRIMARY KEY  NOT NULL AUTO_INCREMENT,
  country_code        INT,
  operator_code       INT,
  phone_number        INT,
  phone_type          VARCHAR(10),
  phone_comment       VARCHAR(100)
);


CREATE TABLE contacts_vladimir_lakomy.contact
(
  id                  INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  first_name          VARCHAR(20),
  second_name         VARCHAR(20),
  last_name           VARCHAR(20),
  date_of_birth       DATE,
  sex                 VARCHAR(7),
  nationality         VARCHAR(20),
  web_site            VARCHAR(200),
  e_mail              VARCHAR(50),
  current_job         VARCHAR(50),
  contact_address_id  INT,
  photo_link          VARCHAR(200),
  phone_number_id     INT,
    FOREIGN KEY (contact_address_id)
      REFERENCES contacts_vladimir_lakomy.contact_address(id)
      ON DELETE CASCADE,
    FOREIGN KEY (phone_number_id)
      REFERENCES contacts_vladimir_lakomy.phone_number(id)
);



