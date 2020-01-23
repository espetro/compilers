ECHO OFF

:: Desactiva la salida por consola de los siguientes comandos
SET file=%1
SET input=%2
:: mkdir tmp

cat ..\in\fixed\jplcore.j %file% > tmp\jasminCode.j
java -jar jasmin.jar  tmp\jasminCode.j > tmp\jasminCode.bin
del /Q tmp\*
:: rm -f $1.*_
java JPL %input%
