@echo off&color 0E&Title ${artifactId}-application-console

rem Detects whether the java_home environment variable is defined 
if defined JAVA_HOME (
	if exist "%JAVA_HOME%" (
		echo Detection of a running Java environment 
		set PATH=%JAVA_HOME%\bin;%PATH%
		goto findJavaHome
	)
)
set curdir=%~dp0
cd ../
echo The operating system does not install the Java running environment, and begin to find java-home from installation package
goto getJdkHome

:getJdkHome
for /f "delims=" %%i in ('dir /ad /b "%cd%" ^| findstr "^jdk.*$"') do (
	set JAVA_HOME=%%~fi
	goto checkIsFindJavaHome
)

:checkIsFindJavaHome
if defined JAVA_HOME (
	if exist %JAVA_HOME% (
		echo Find JDK in the installation package directory: %JAVA_HOME%
		set PATH=%JAVA_HOME%\bin;%PATH%
		goto findJavaHome
	)
)
echo Can't find the JAVA_HOME in the installation package directory.
pause

:findJavaHome
echo %PATH%
java -version
for /f "delims=" %%i in ('dir /b /s "%curdir%../lib/${artifactId}*application*.jar" ^| findstr "${artifactId}\-application\-.*.jar$"') do (
	echo %%~fi , %%~ni , %%~zi
	set JAR_PATH=%%~fi
	goto appRun
)

:appRun
rem Configure the JVM startup memory  
set java_vm_args=-Xms256M -Xmx1024M
java %java_vm_args% -jar "%JAR_PATH%" --server.port=8080 --spring.profiles.active=prod