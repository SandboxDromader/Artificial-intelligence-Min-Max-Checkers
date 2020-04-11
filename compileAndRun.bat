@ECHO OFF
javac -d ./bin ./src/*.java
java -cp ./bin; BoardManager
pause
del /q bin