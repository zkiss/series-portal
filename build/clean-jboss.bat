@echo off

call setenv %*

rd /S /Q %SERVER_DIR%\work
rd /S /Q %SERVER_DIR%\tmp
rd /S /Q %SERVER_DIR%\data
rd /S /Q %SERVER_DIR%\log
