#
#		File:			makefile
#		Author:		Matteo Loporchio, 491283
#
#		This is a standard makefile to compile the entire SpatialAQP project.
#		Many thanks to
#		https://www.cs.swarthmore.edu/~newhall/unixhelp/javamakefiles.html
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
	Geometry.java \
	Hash.java \
	Main.java \
	MRTree.java \
	MRTreeNode.java \
	Point.java \
	Query.java \
	Rectangle.java \
	SkipList.java \
	SkipListEntry.java \
	TestChain.java \
	TestIndex.java \
	TestQuery.java \
	Utility.java \
	VContainer.java \
	VLeaf.java \
	VObject.java \
	VPruned.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
