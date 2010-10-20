@echo off

call setenv.bat

title Series Portal JBoss server

set LOCAL_IP=0.0.0.0

"%JBOSS_DIR%\bin\debug.bat" -c %SERVER_NAME% -b%LOCAL_IP%
