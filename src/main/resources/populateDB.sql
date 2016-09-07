USE contacts_vladimir_lakomy;

INSERT INTO contacts_vladimir_lakomy.contact
(id, first_name, second_name, last_name, date_of_birth, sex, nationality, martial_status, web_site,
 e_mail, current_job, photo_link)
VALUES
  (1,'Alexandr','Sergeevich', 'Ivanov', '1990-02-27', 'MALE','Russian','SINGLE','alex.com',
     'alex.ivan@gmail.com', 'Google', './uploads/img/alex.jpg'),

  (2,'Grigory','Fedorovich', 'Smirnov', '1985-09-22', 'MALE','Belarus','MARRIED','grigor.com',
     'grig.fedor@gmail.com', 'Microsoft', './uploads/img/grigo.jpg'),

  (3,'Helen','Petrovna', 'Sidorova', '1998-07-19', 'FEMALE','Ukrainian','SINGLE','sidorova.com',
     'helen.sid@gmail.com', 'Apple', './uploads/img/hel.jpg'),

  (4,'Helen','Petrovna', 'Sidorova', '1998-07-19', 'FEMALE','Ukrainian','SINGLE','sidorova.com',
     'helen.sid@gmail.com', 'Apple', './uploads/img/hel.jpg'),

  (5,'Helen','Petrovna', 'Sidorova', '1998-07-19', 'FEMALE','Ukrainian','SINGLE','sidorova.com',
     'helen.sid@gmail.com', 'Apple', './uploads/img/hel.jpg'),

  (6,'Helen','Petrovna', 'Sidorova', '1998-07-19', 'FEMALE','Ukrainian','SINGLE','sidorova.com',
     'helen.sid@gmail.com', 'Apple', './uploads/img/hel.jpg'),

  (7,'Helen','Petrovna', 'Sidorova', '1998-07-19', 'FEMALE','Ukrainian','SINGLE','sidorova.com',
     'helen.sid@gmail.com', 'Apple', './uploads/img/hel.jpg'),

  (8,'Helen','Petrovna', 'Sidorova', '1998-07-19', 'FEMALE','Ukrainian','SINGLE','sidorova.com',
     'helen.sid@gmail.com', 'Apple', './uploads/img/hel.jpg'),

  (9,'Helen','Petrovna', 'Sidorova', '1998-07-19', 'FEMALE','Ukrainian','SINGLE','sidorova.com',
     'helen.sid@gmail.com', 'Apple', './uploads/img/hel.jpg'),

  (10,'Helen','Petrovna', 'Sidorova', '1998-07-19', 'FEMALE','Ukrainian','SINGLE','sidorova.com',
      'helen.sid@gmail.com', 'Apple', './uploads/img/hel.jpg');

INSERT INTO contacts_vladimir_lakomy.phone_number (id,country_code,operator_code,phone_number,phone_type, phone_comment, contact_id)
    VALUES (1, 375, 29, 7641640, 'MOBILE', 'comment1', 1),
           (2, 375, 29, 7047143, 'MOBILE', '', 2),
           (3, 371, 25, 7129811, 'MOBILE', 'blabla', 3),
           (4, 375, 25, 7135441, 'HOME', 'igcomment', 4),
           (5, 371, 29, 8451632, 'HOME', 'nm&&34', 5),
           (6, 375, 29, 3331245, 'MOBILE', 'comment2', 6),
           (7, 372, 23, 8574321, 'MOBILE', 'comment3', 7),
           (8, 375, 29, 9658123, 'HOME', 'comment4', 8),
           (9, 370, 32, 7456123, 'MOBILE', 'comment5', 9),
           (10, 379, 29, 3214566, 'MOBILE', 'comment6', 10);

INSERT INTO contacts_vladimir_lakomy.contact_address (id,country,town,street,house_number,flat_number,zip_code, contact_id)
    VALUES (1,'Belarus','Minsk','Independence ave.',12,65,220000, 1),
           (2,'Belarus','Gomel','Sovetskaya st.',34,2,246144, 2),
           (3,'Russia','Moscow','Victory st.',111,8,124365, 3),
           (4,'Russia','St. Petersburg','Budapest st.',5,122,115321, 4),
           (5,'Poland','Warsaw','Lenin ave.',322,99,112000, 5),
           (6,'Ukraine','Kiev','Suvorov st.',98,76,133251, 6),
           (7,'Belarus','Mogilev','Frunze st.',14,57,236674, 7),
           (8,'Russia','Rostov','Korolev st.',16,76,195123, 8),
           (9,'Ukraine','Odessa','Filatov st.',32,21,131241, 9),
           (10,'Ukraine','Kharkov','Revolution st.',56,256,199321, 10);

INSERT INTO contacts_vladimir_lakomy.contact_attachment (id,attachment_link,attachment_comment,date_of_upload,contact_id)
    VALUES (1,'./uploads/file1.txt','text file1', '2016-09-15',1),
           (2,'./uploads/file2.txt','text file2', '2016-09-16',1),
           (3,'./uploads/file3.txt','text file3', '2016-09-15',1),
           (4,'./uploads/file4.txt','text file4', '2016-09-12',2),
           (5,'./uploads/file5.txt','text file5', '2016-09-12',3),
           (6,'./uploads/file6.txt','text file6', '2016-09-14',4),
           (7,'./uploads/file7.txt','text file7', '2016-09-14',2),
           (8,'./uploads/file8.txt','text file8', '2016-09-13',2),
           (9,'./uploads/file9.txt','text file9', '2016-09-13',4),
           (10,'./uploads/file10.txt','text file10', '2016-09-12',3),
           (11,'./uploads/file11.txt','text file11', '2016-09-12',5),
           (12,'./uploads/file12.txt','text file12', '2016-09-11',5),
           (13,'./uploads/file13.txt','text file13', '2016-09-13',7),
           (14,'./uploads/file14.txt','text file14', '2016-09-15',7);

