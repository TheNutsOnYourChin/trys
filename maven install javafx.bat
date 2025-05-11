@echo off
call mvn install:install-file -DgroupId=javafx-swt -DartifactId=javafx.swt -Dversion=17.0.6 -Dpackaging=jar -Dfile=D:\Java\javafx-sdk-17.0.6\lib\javafx-swt.jar
call mvn install:install-file -DgroupId=javafx-swt -DartifactId=javafx.base -Dversion=17.0.6 -Dpackaging=jar -Dfile=D:\Java\javafx-sdk-17.0.6\lib\javafx.base.jar
call mvn install:install-file -DgroupId=javafx-swt -DartifactId=javafx.controls -Dversion=17.0.6 -Dpackaging=jar -Dfile=D:\Java\javafx-sdk-17.0.6\lib\javafx.controls.jar
call mvn install:install-file -DgroupId=javafx-swt -DartifactId=javafx.fxml -Dversion=17.0.6 -Dpackaging=jar -Dfile=D:\Java\javafx-sdk-17.0.6\lib\javafx.fxml.jar
call mvn install:install-file -DgroupId=javafx-swt -DartifactId=javafx.graphics -Dversion=17.0.6 -Dpackaging=jar -Dfile=D:\Java\javafx-sdk-17.0.6\lib\javafx.graphics.jar
call mvn install:install-file -DgroupId=javafx-swt -DartifactId=javafx.media -Dversion=17.0.6 -Dpackaging=jar -Dfile=D:\Java\javafx-sdk-17.0.6\lib\javafx.media.jar
call mvn install:install-file -DgroupId=javafx-swt -DartifactId=javafx.swing -Dversion=17.0.6 -Dpackaging=jar -Dfile=D:\Java\javafx-sdk-17.0.6\lib\javafx.swing.jar
call mvn install:install-file -DgroupId=javafx-swt -DartifactId=javafx.web -Dversion=17.0.6 -Dpackaging=jar -Dfile=D:\Java\javafx-sdk-17.0.6\lib\javafx.web.jar
pause
