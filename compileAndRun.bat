@ECHO OFF
javac -d ./bin ./src/*.java
java -cp ./bin; BoardDisplayManager
pause
del /q bin