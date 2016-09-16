CREATE DATABASE contacts_vladimir_lakomy;
CREATE USER vova_lakomy@localhost IDENTIFIED BY '1234567';
GRANT CREATE, DROP, DELETE, INSERT, SELECT, UPDATE, REFERENCES
ON contacts_vladimir_lakomy.* to vova_lakomy@localhost IDENTIFIED BY '1234567';