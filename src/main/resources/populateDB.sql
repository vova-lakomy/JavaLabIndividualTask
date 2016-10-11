USE contacts_vladimir_lakomy;

INSERT INTO contacts_vladimir_lakomy.contact
(id, first_name, second_name, last_name, date_of_birth, sex, nationality, marital_status, web_site,
 e_mail, current_job, country, town, street, house_number, flat_number, zip_code)
VALUES
  (1,'Александр','Сергеевич', 'Иванов', '1990-02-27', 'MALE','Русский','SINGLE','alex.com',
     'alex.ivan@gmail.com', 'Google', 'Беларусь','Минск','Независимости',12,65,220000),

  (2,'Григорий','Федорович', 'Смирнов', '1985-09-26', 'MALE','Беларусь','MARRIED','grigor.com',
     'grig.fedor@gmail.com', 'Microsoft', 'Бнларусь','Гомель','Советская',34,2,246144),

  (3,'Елена','Петровна', 'Сидорова', '1998-07-19', 'FEMALE','Украина','SINGLE','sidorova.com',
     'helen.sid@gmail.com', 'Apple', 'Украина','Одесса','Филатова',32,21,131241),

  (4,'Сергей','Сергеевич', 'Карпов', '1990-01-11', 'MALE','Беларусь','DIVORCED','karpov.com',
     'serega.karp@gmail.com', 'IBM', 'Украина','Харьков','Революционная',56,256,199321),

  (5,'Владимир','Владимирович', 'Тупин', '1952-09-11', 'MALE','Россия','DIVORCED','toopin.com',
     'tupin.vv@gmail.com', 'BlackHouse', 'Россия', 'Moscow','Victory st.',111,8,124365),

  (6,'Julia','Igorevna', 'Cherepanova', '1971-12-01', 'FEMALE','Russian','MARRIED','cherepan.com',
     'jul.cherep@gmail.com', 'Google', 'Belarus','Mogilev','Frunze st.',14,57,236674),

  (7,'Виктор','Робертович', 'Иванов', '1962-07-21', 'MALE','Russian','MARRIED','ivan.com',
     'ivan.victor@gmail.com', 'Oracle', 'Россия','СПБ','Budapest st.',5,122,115321),

  (8,'Kate','Ivanovna', 'Sokolova', '1985-03-15', 'FEMALE','BELARUSSIAN','WIDOWED','sokol.com',
     'katjusha85@gmail.com', 'Mail.Ru', 'Poland','Warsaw','Lenin ave.',322,99,112000),

  (9,'Igor','Victorovich', 'Stepanov', '1980-04-01', 'MALE','Ukrainian','MARRIED','stepa.com',
     'stepasha2001@gmail.com', 'Google', 'Ukraine','Kiev','Suvorov st.',98,76,133251),

  (10,'Dmitry','Anatoljevich', 'Mefnedev', '1965-09-14', 'MALE','Russian','MARRIED','mefned.com',
      'mefned.dima@gmail.com', 'WhiteHouse','Russia','Rostov','Korolev st.',16,76,195123),

  (11,'Ivan','Ivanovich', 'Gvozdev', '1992-9-16', 'MALE','Belarussian','MARRIED','stepa.com',
      'i.gvozd@gmail.com', 'Google', 'Belarus','Brest','Suharevskaya st.',47,386,161823),

  (12,'Михаил','Александрович', 'Мищенко', '1973-2-12', 'MALE','Беларусь','MARRIED','misch4.com',
      'mish@gmail.com', 'Google', 'Belarus','Minsk','Sosnovaya st.',29,8,227557),

  (13,'Fedor','Pavlovich', 'Ploscenko', '1971-1-15', 'MALE','Belarussian','WIDOWED','plos.com',
      'fedos@gmail.com', 'Google', 'Belarus','Pinsk','Malaya st.',126,341,221697),

  (14,'Inna','Victorovna', 'Svetlova', '1961-5-19', 'FEMALE','Belarussian','DIVORCED','svet.com',
      'inno4ka@gmail.com', 'Google', 'Belarus','Minsk','Zaharova st.',107,308,217600),

  (15,'Алексей','Григорьевич', 'Светлов', '1989-3-5', 'MALE','Беларусь','MARRIED','alex.com',
      'alex12@gmail.com', 'Google', 'Беларусь','Минск','Захарова',119,182,221756),

  (16,'Milana','Alfonsovna', 'Prigozhaya', '1981-1-3', 'FEMALE','Belarussian','MARRIED','nice.com',
      'milashka25@gmail.com', 'Google', 'Belarus','Minsk','Suvorov st.',26,329,192043),

  (17,'Александр','Бриборьевич', 'Алкушенко', '1954-8-30', 'MALE','Belarussian','MARRIED','alka.com',
      'aleks.alku@gmail.com', 'BlackHouse', 'Belarus','Минск','Трола',72,42,202000),

  (18,'Irina','Iosifovna', 'Sushkina', '1982-3-11', 'FEMALE','Belarussian','SINGLE','qazs.com',
      'irochka.sushkina@gmail.com', 'Google', 'Belarus','Gomel','Suvorov st.',20,110,206493),

  (19,'Egor','Fedorovich', 'Vashkel', '1975-12-18', 'MALE','Belarussian','MARRIED','vash.com',
      'vash.egor@gmail.com', 'Google', 'Belarus','Vitebsk','Pushkina st.',32,310,184124),

  (20,'Tatyana','Victorovna', 'Stepanova', '1963-10-15', 'FEMALE','Belarussian','SINGLE','tan.com',
      'tata.stepanova@gmail.com', 'Google', 'Belarus','Minsk','Pobedy st.',133,112,168690),

  (21,'Artem','Vasilyevich', 'Nozhkin', '1986-5-11', 'MALE','Belarussian','DIVORCED','rpoke.com',
      'nozhArt@gmail.com', 'Google', 'Belarus','Orsha','Oktyabrya st.',24,187,188950),

  (22,'Mihail','Ivanovich', 'Grib', '1993-4-11', 'MALE','Belarussian','MARRIED','git.com',
      'mushroom@gmail.com', 'Google', 'Belarus','Polotsk','Molodyezhnaya st.',21,172,208579),

  (23,'Igor','Petrovich', 'Grib', '1983-12-8', 'MALE','Belarussian','MARRIED','brig.com',
      'igor.grib@gmail.com', 'Google', 'Belarus','Polotsk','Stepnaya st.',95,350,157532),

  (24,'Vladimir','Inanovich', 'Pleshko', '1961-10-27', 'MALE','Belarussian','WIDOWED','vova.com',
      'vova.pleshko@gmail.com', 'Google', 'Belarus','Senno','Sadovaya st.',121,387,192627),

  (25,'Matvey','Vladislavovich', 'Arkanov', '1983-5-27', 'MALE','Belarussian','SINGLE','motja.com',
      'arkasha@gmail.com', 'Google', 'Belarus','Grodno','Semashko st.',97,181,211421);



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
           (10, 379, 29, 3214566, 'MOBILE', 'comment', 10),
           (11, 790, 17, 1246531, 'HOME', 'this is home phone', 10),
           (12, 560, 43, 1321456, 'HOME', 'one one one one', 10),
           (13, 308, 12, 2194353, 'MOBILE', 'bla bla', 10),
           (14, 120, 98, 3154687, 'MOBILE', 'bla bal bla bla', 10);


# INSERT INTO contacts_vladimir_lakomy.contact_attachment (id,attachment_link,attachment_comment,date_of_upload,contact_id)
#     VALUES (1,'./uploads/file1.txt','text file1', '2016-09-15',1),
#            (2,'./uploads/file2.txt','text file2', '2016-09-16',1),
#            (3,'./uploads/file3.txt','text file3', '2016-09-15',1),
#            (4,'./uploads/file4.txt','text file4', '2016-09-12',2),
#            (5,'./uploads/file5.txt','text file5', '2016-09-12',3),
#            (6,'./uploads/file6.txt','text file6', '2016-09-14',4),
#            (7,'./uploads/file7.txt','text file7', '2016-09-14',2),
#            (8,'./uploads/file8.txt','text file8', '2016-09-13',2),
#            (9,'./uploads/file9.txt','text file9', '2016-09-13',4),
#            (10,'./uploads/file10.txt','text file10', '2016-09-12',3),
#            (11,'./uploads/file11.txt','text file11', '2016-09-12',5),
#            (12,'./uploads/file12.txt','text file12', '2016-09-11',5),
#            (13,'./uploads/file13.txt','text file13', '2016-09-13',7),
#            (14,'./uploads/file14.txt','text file14', '2016-09-15',7);

