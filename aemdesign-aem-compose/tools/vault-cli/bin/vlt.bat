@REM
@REM   ADOBE CONFIDENTIAL
@REM   __________________
@REM
@REM    Copyright 2012 Adobe Systems Incorporated
@REM    All Rights Reserved.
@REM
@REM   NOTICE:  All information contained herein is, and remains
@REM   the property of Adobe Systems Incorporated and its suppliers,
@REM   if any.  The intellectual and technical concepts contained
@REM   herein are proprietary to Adobe Systems Incorporated and its
@REM   suppliers and are protected by trade secret or copyright law.
@REM   Dissemination of this information or reproduction of this material
@REM   is strictly forbidden unless prior written permission is obtained
@REM   from Adobe Systems Incorporated.

@REM ----------------------------------------------------------------------------
@REM Vault Start Up Batch script
@REM
@REM Required ENV vars:
@REM JAVA_HOME - location of a JDK home dir
@REM
@REM Optional ENV vars
@REM VLT_BATCH_ECHO - set to 'on' to enable the echoing of the batch commands
@REM VLT_BATCH_PAUSE - set to 'on' to wait for a key stroke before ending
@REM VLT_OPTS - parameters passed to the Java VM when running vlt
@REM     e.g. to debug vlt itself, use
@REM set VLT_OPTS=-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000
@REM ----------------------------------------------------------------------------

@REM Begin all REM lines with '@' in case VLT_BATCH_ECHO is 'on'
@echo off
@REM enable echoing my setting VLT_BATCH_ECHO to 'on'
@if "%VLT_BATCH_ECHO%" == "on"  echo %VLT_BATCH_ECHO%

@REM set %HOME% to equivalent of $HOME
if "%HOME%" == "" (set HOME=%HOMEDRIVE%%HOMEPATH%)

@REM Execute a user defined script before this one
if exist "%HOME%\vltrc_pre.bat" call "%HOME%\vltrc_pre.bat"

set ERROR_CODE=0

:init
@REM Decide how to startup depending on the version of windows

@REM -- Win98ME
if NOT "%OS%"=="Windows_NT" goto Win9xArg

@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" @setlocal

@REM -- 4NT shell
if "%eval[2+2]" == "4" goto 4NTArgs

@REM -- Regular WinNT shell
set CMD_LINE_ARGS=%*
goto WinNTGetScriptDir

@REM The 4NT Shell from jp software
:4NTArgs
set CMD_LINE_ARGS=%$
goto WinNTGetScriptDir

:Win9xArg
@REM Slurp the command line arguments.  This loop allows for an unlimited number
@REM of arguments (up to the command line limit, anyway).
set CMD_LINE_ARGS=
:Win9xApp
if %1a==a goto Win9xGetScriptDir
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto Win9xApp

:Win9xGetScriptDir
set SAVEDIR=%CD%
%0\
cd %0\..\.. 
set VLT_HOME=%CD%
cd %SAVEDIR%
set SAVE_DIR=
goto repoSetup

:WinNTGetScriptDir
set VLT_HOME=%~dp0\..

:repoSetup


if "%JAVACMD%"=="" set JAVACMD=java

if "%REPO%"=="" set REPO=%VLT_HOME%\lib

set CLASSPATH="%REPO%"\com.day.jcr.vault-2.4.40.jar;"%REPO%"\vault-vlt-2.4.40.jar;"%REPO%"\jackrabbit-jcr-commons-2.4.4-r1468174.jar;"%REPO%"\vault-diff-2.4.40.jar;"%REPO%"\commons-io-1.4.jar;"%REPO%"\vault-sync-2.4.40.jar;"%REPO%"\commons-jci-fam-1.0.jar;"%REPO%"\commons-logging-api-1.1.jar;"%REPO%"\org.apache.sling.jcr.api-2.0.6.jar;"%REPO%"\org.apache.sling.commons.osgi-2.0.6.jar;"%REPO%"\vault-davex-2.4.40.jar;"%REPO%"\jackrabbit-jcr-client-2.4.4-r1468174.jar;"%REPO%"\jackrabbit-spi-commons-2.4.4-r1468174.jar;"%REPO%"\jackrabbit-spi-2.4.4-r1468174.jar;"%REPO%"\commons-collections-3.2.1.jar;"%REPO%"\jackrabbit-jcr2spi-2.4.4-r1468174.jar;"%REPO%"\jackrabbit-api-2.4.4-r1468174.jar;"%REPO%"\jackrabbit-spi2dav-2.4.4-r1468174.jar;"%REPO%"\jackrabbit-webdav-2.4.4-r1468174.jar;"%REPO%"\commons-httpclient-3.0.jar;"%REPO%"\commons-codec-1.2.jar;"%REPO%"\jcl-over-slf4j-1.5.8.jar;"%REPO%"\commons-cli-2.0-mahout.jar;"%REPO%"\jline-0.9.94.jar;"%REPO%"\jcr-2.0.jar;"%REPO%"\slf4j-api-1.5.8.jar;"%REPO%"\slf4j-log4j12-1.5.8.jar;"%REPO%"\log4j-1.2.12.jar;"%REPO%"\vault-cli-2.4.40.jar
goto endInit

@REM Reaching here means variables are defined and arguments have been captured
:endInit

%JAVACMD% %VLT_OPTS% -Xms500m -Xmx500m -XX:PermSize=128m -XX:-UseGCOverheadLimit -classpath %CLASSPATH_PREFIX%;%CLASSPATH% -Dapp.name="vlt" -Dapp.repo="%REPO%" -Dapp.home="%VLT_HOME%" -Dvlt.home="%VLT_HOME%" com.day.jcr.vault.cli.VaultFsApp %CMD_LINE_ARGS%
if ERRORLEVEL 1 goto error
goto end

:error
if "%OS%"=="Windows_NT" @endlocal
set ERROR_CODE=%ERRORLEVEL%

:end
@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" goto endNT

@REM For old DOS remove the set variables from ENV - we assume they were not set
@REM before we started - at least we don't leave any baggage around
set CMD_LINE_ARGS=
goto postExec

:endNT
@REM If error code is set to 1 then the endlocal was done already in :error.
if %ERROR_CODE% EQU 0 @endlocal


:postExec
if exist "%HOME%\vltrc_post.bat" call "%HOME%\vltrc_post.bat"
@REM pause the batch file if VLT_BATCH_PAUSE is set to 'on'
if "%VLT_BATCH_PAUSE%" == "on" pause


if "%FORCE_EXIT_ON_ERROR%" == "on" (
  if %ERROR_CODE% NEQ 0 exit %ERROR_CODE%
)

exit /B %ERROR_CODE%
