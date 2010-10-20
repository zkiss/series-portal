@echo off
call setenv
title Deploy: %TRUNK_DIR%
color F4
pushd "%TRUNK_DIR%\build"
call ant deploy
popd
if "%ERRORLEVEL%" == "1" pause