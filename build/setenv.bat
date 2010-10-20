@echo off

set TRUNK_DIR=E:\BME\current\eclipse
set JBOSS_DIR=d:\Prog\jboss-5.1.0.GA
set JAVA_DIR=c:\Program Files\Java\jdk1.6.0_18
set ANT_DIR=d:\Prog\apache-ant


set PATH=%ANT_DIR%\bin;%PATH%
set PATH=%JAVA_DIR%\bin;%PATH%
set PATH=%HSQLDB_DIR%\bin;%PATH%
set JAVA_HOME=%JAVA_DIR%

set BUILD_DIR=%TRUNK_DIR%\ctvg-build

if "%1" == "" (
	set SERVER_NAME=sp
) else (
	set SERVER_NAME=%1
)
set SERVER_DIR=%JBOSS_DIR%\server\%SERVER_NAME%