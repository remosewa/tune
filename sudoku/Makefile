JAVAC=javac
sources =  ./Main.java entities/Board.java entities/Cell.java entities/Relationship.java
classes = $(sources:.java=.class)

all: myProgram

myProgram:
	javac $(sources)
	
clean :
	rm -f *.class

%.class : %.java
	$(JAVAC) $<
	
print-%  : ; @echo $* = $($*)

jar: myProgram
	jar cfm solver.jar META-INF/MANIFEST.MF $(classes)