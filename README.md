# TV2-Semesterprojekt
Second semester project

## Setup Project 
__Requirement:__

Intellij IDEA Ultimate 2021.1

PostgreSQL 9.4.1

__Recommended:__

Java provider adopt-openj9-15

__Setup:__

Open the project in Intellij

1. Click on Open and select the root project folder

Setup SDK

1. Click on "File" located at the left top in Intellij and select "Project Structure..."
2. Click on "Project" under "Project Settings" at the left
3. At the right under "Project SDK:" click on the drop-down and choose java version 15.

   NOTE: If Java version 15 does not exist, it can be installed by clicking on the drop-down menu under "Project SDK:"<br>
   and select "Add SDK" and then select "Download JDK...". A new window will appear where it will be possible<br>
   to install Java version 15.

4. Under "Project language level:" click on the drop-down and select "SDK default (15 - Text blocks)"

Setup Database

1. Install PostgresSQL

2. Set password for PostgresSQL
   
      **NOTE: It is imperative that you remember your password**

3. In IntelliJ, press the Database button in the upper right corner of the screen, next to Maven.

4. Click on the “Data Source Properties”. Then, add a “PostgresSQL” database to the project. Change the username to “Postgres”, and the password to the one you chose. Don’t change the database name.

5. A console window should appear in the open tabs. Run the following in the console:

drop database IF EXISTS tv2_semesterprojekt;
CREATE DATABASE tv2_semesterprojekt
WITH
OWNER = postgres
ENCODING = 'UTF8'
CONNECTION LIMIT = -1;

6.	Lastly, run both of the SQL files by right-clicking the database named "tv2_semesterprojekt", and choosing “Run SQL script” and picking “database.sql” and “dummy_data.sql” from the resources folder.

## Run source code

1. Find the Main.java located in the folder src/main/java/com/company
2. Right-click on the Main.java and select "Run 'Main.main()'"

## Build Java Archive (.jar)

1. Click at the "Maven" text located at the edge of the program to the right
2. Double-click on "TV2 semesterprojekt" to expand it
3. Double-click on "Lifecycle" to expand it
4. Double-click on "install"
5. When Maven is done the .jar file can be located in the "target" folder

## Run Java Archive (.jar)

1. Located the .jar in the "target" folder
1. Right-click on the .jar file and select "Run 'TV2-Semesterprojekt-...'"
