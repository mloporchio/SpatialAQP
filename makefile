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
	BlockchainRes.java \
	BlockRes.java \
	Geometry.java \
	Hash.java \
	MRTree.java \
	MRTreeNode.java \
	Pair.java \
	Point.java \
	Query.java \
	Rectangle.java \
	SkipList.java \
	SkipListEntry.java \
	TestIndex.java \
	TestLookup.java \
	TestQuery.java \
	TestSkip.java \
	Utility.java \
	VBlock.java \
	VContainer.java \
	VLeaf.java \
	VObject.java \
	VPruned.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
