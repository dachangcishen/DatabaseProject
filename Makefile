all: main.class

main.class:
	javac *.java
	java -cp mysql-jdbc.jar:. CSCI3170Proj

run:
	java -cp mysql-connector-java-5.1.48.jar:. CSCI3170Proj

clean:
	rm *.class