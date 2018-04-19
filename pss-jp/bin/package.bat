@echo off
echo [INFO] Prepare to package the project skip test.

cd %~dp0
cd ..

rem set MAVEN_OPTS=%MAVEN_OPTS% -XX:MaxPermSize=128m
call mvn package -Dmaven.test.skip=true

cd bin
pause