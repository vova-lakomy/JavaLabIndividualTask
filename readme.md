#iTechArt Java Lab individual project

1. clone
2. run create_user.sql located at root
3. set directory for uploads in file-upload.properties located at resources
4. mvn package
5. run

Data base will be created and populated at every start. To turn 
this option off check the file 'sqlLoader.properties' located at 
resources, then you can run 'initDB.sql' and 'populateDB.sql' manually.

Tested on apache-tomcat-8.0.35, 8.5.5, 9.0.0.M10
