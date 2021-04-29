# TV2-Semesterprojekt
Second semester project

## Setup Project 
__Requirement:__

Intellij IDEA Ultimate 2021.1

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
