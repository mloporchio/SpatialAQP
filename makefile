#
#		File:			makefile
#		Author:		Matteo Loporchio, 491283
#
#		This is a standard makefile to compile the entire SpatialAQP project.
#		Many thanks to https://www.cs.swarthmore.edu/~newhall/unixhelp/javamakefiles.html
#		for sharing their code.
#

JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Block.java \
	Blockchain.java \
	BlockHeader.java \
	Hash.java \
	Main.java \
	MRTree.java \
	Point.java \
	Rectangle.java \
	Utility.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
