JC = javac
JVM = java
JOPTS = -cp ./lib/junit-4.4.jar:./lib/hamcrest-all-1.3.jar
JEXOPTS = -cp ././.:./lib/junit-4.4.jar:./lib/hamcrest-all-1.3.jar
TEST = Domain.Test.TestDadesModificades_Algorisme

default:
	$(JC) $(JOPTS) */*.java */*/*.java

runtest:
	java $(JEXOPTS) org.junit.runner.JUnitCore $(TEST)

clean:
	$(RM) */*.class */*/*.class