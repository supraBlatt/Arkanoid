# 323830448
# Yuval Ezra

compile: bin
	find src -name "*.java" > sources.txt
	javac -cp biuoop-1.4.jar:. -d bin @sources.txt
	rm sources.txt

bin:
	mkdir bin

run: compile
	java -cp biuoop-1.4.jar:bin:resources game.Ass6Game

jar: compile
	jar cfm ass6game.jar Manifest.mf -C bin . -C resources .
