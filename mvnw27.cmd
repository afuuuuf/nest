@echo off
setlocal

set "JAVA_HOME=C:\Program Files\OpenJDK\jdk-27.0.1.1"
set "PATH=%JAVA_HOME%\bin;%PATH%"

call "%~dp0mvnw.cmd" %*

endlocal
