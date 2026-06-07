@echo off
setlocal

set "BUILD_DIR=build\classes"

if not exist build mkdir build
if not exist "%BUILD_DIR%" mkdir "%BUILD_DIR%"

dir /s /b src\edu\*.java > build\sources.txt
javac -encoding UTF-8 -d "%BUILD_DIR%" @build\sources.txt
if errorlevel 1 (
    echo.
    echo Build failed. Please make sure JDK 11 or newer is installed.
    pause
    exit /b 1
)

java -cp "%BUILD_DIR%;src" edu.hitsz.application.Main
