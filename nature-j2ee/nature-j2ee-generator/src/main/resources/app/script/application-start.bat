@echo off
color 0E
setlocal
set curdir=%~dp0

echo test am i admin? > %SystemRoot%\System32\ThinkVenus.test
if not exist "%SystemRoot%\System32\ThinkVenus.test" (
    echo  Warning: please run as an administrator!
    pause
    exit
)
del %SystemRoot%\System32\ThinkVenus.test

rem Detects whether the java_home environment variable is defined
if not "%JAVA_HOME%" == "" goto foundJavaHome
echo WARNING: The operating system does not install the Java running environment, and begin to find java-home from installation package
for /f "delims=" %%i in ('dir /ad /b "%curdir%..\" ^| findstr "^jdk.*$"') do (
	set JAVA_HOME=%curdir%..\%%i
	goto foundJavaHome
)
echo WARNING: Can't find the java home.
pause
:foundJavaHome
echo USING JAVA_HOME: %JAVA_HOME%
set PATH=%JAVA_HOME%\bin;%PATH%
java -fullversion
for /f "delims=" %%i in ('dir /s/a-d /b "%curdir%..\lib\${artifactId}-application-*-RELEASE.jar" ^| findstr "${artifactId}-application-.*-RELEASE.jar$"') do (
	title %%~ni Command Line Console
	set JAR_PATH=%%i
	goto doAppRun
)
echo WARNING: Can't find the jar[${artifactId}-application-xxx-RELEASE.jar] that needs to be executed 
pause
:doAppRun
rem Configure the JVM startup memory  
set java_vm_args=-Xms256M -Xmx1024M
java %java_vm_args% -jar "%JAR_PATH%" --server.port=8080 --spring.profiles.active=prod